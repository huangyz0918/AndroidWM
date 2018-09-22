/*
 *    Copyright 2018 huangyz0918
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.watermark.androidwm.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.watermark.androidwm.bean.DetectionParams;
import com.watermark.androidwm.bean.DetectionReturnValue;
import com.watermark.androidwm.listener.DetectFinishListener;
import com.watermark.androidwm.utils.BitmapUtils;

import static com.watermark.androidwm.utils.BitmapUtils.bitmap2ARGBArray;
import static com.watermark.androidwm.utils.BitmapUtils.getBitmapPixels;
import static com.watermark.androidwm.utils.Constant.ERROR_BITMAP_NULL;
import static com.watermark.androidwm.utils.Constant.ERROR_DETECT_FAILED;
import static com.watermark.androidwm.utils.StringUtils.binaryToString;
import static com.watermark.androidwm.utils.StringUtils.getBetweenStrings;
import static com.watermark.androidwm.utils.StringUtils.intArrayToStringJ;
import static com.watermark.androidwm.utils.StringUtils.replaceNinesJ;

/**
 * This is a task for watermark image detection.
 * In LSB mode, all the task will return a bitmap;
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class LSBDetectionTask extends AsyncTask<DetectionParams, Void, DetectionReturnValue> {

    private DetectFinishListener listener;

    public LSBDetectionTask(DetectFinishListener listener) {
        this.listener = listener;
    }

    @Override
    protected DetectionReturnValue doInBackground(DetectionParams... detectionParams) {
        boolean isText = detectionParams[0].isTextWatermark();
        Bitmap markedBitmap = detectionParams[0].getWatermarkBitmap();
        DetectionReturnValue resultValue = new DetectionReturnValue();

        if (markedBitmap == null) {
            listener.onFailure(ERROR_BITMAP_NULL);
            return null;
        }

        int[] pixels = getBitmapPixels(markedBitmap);
        int[] colorArray = bitmap2ARGBArray(pixels);

        for (int i = 0; i < colorArray.length; i++) {
            colorArray[i] = colorArray[i] % 10;
        }

        replaceNinesJ(colorArray);
        String binaryString = intArrayToStringJ(colorArray);
        String resultString;
        if (isText) {
            resultString = getBetweenStrings(binaryString, true, listener);
            resultString = binaryToString(resultString);
            resultValue.setWatermarkString(resultString);
        } else {
            binaryString = getBetweenStrings(binaryString, false, listener);
            resultString = binaryToString(binaryString);
            resultValue.setWatermarkBitmap(BitmapUtils.stringToBitmap(resultString));
        }

        return resultValue;
    }

    @Override
    protected void onPostExecute(DetectionReturnValue detectionReturnValue) {
        if (detectionReturnValue != null) {
            if (detectionReturnValue.getWatermarkBitmap() != null) {
                listener.onImage(detectionReturnValue.getWatermarkBitmap());
            } else if (detectionReturnValue.getWatermarkString() != null
                    && !detectionReturnValue.getWatermarkString().equals("")) {
                listener.onText(detectionReturnValue.getWatermarkString());
            } else {
                listener.onFailure(ERROR_DETECT_FAILED);
            }
        } else {
            listener.onFailure(ERROR_DETECT_FAILED);
        }
        super.onPostExecute(detectionReturnValue);
    }

}
