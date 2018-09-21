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
import android.graphics.Color;
import android.os.AsyncTask;

import com.watermark.androidwm.bean.DetectionParams;
import com.watermark.androidwm.bean.DetectionReturnValue;
import com.watermark.androidwm.listener.DetectFinishListener;
import com.watermark.androidwm.utils.BitmapUtils;

import static com.watermark.androidwm.utils.Constant.ERROR_BITMAP_NULL;
import static com.watermark.androidwm.utils.Constant.ERROR_DETECT_FAILED;
import static com.watermark.androidwm.utils.Constant.ERROR_NO_WATERMARK_FOUND;
import static com.watermark.androidwm.utils.Constant.LSB_IMG_PREFIX_FLAG;
import static com.watermark.androidwm.utils.Constant.LSB_IMG_SUFFIX_FLAG;
import static com.watermark.androidwm.utils.Constant.LSB_TEXT_PREFIX_FLAG;
import static com.watermark.androidwm.utils.Constant.LSB_TEXT_SUFFIX_FLAG;
import static com.watermark.androidwm.utils.StringUtils.binaryToString;

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

        int[] pixels = new int[markedBitmap.getWidth() * markedBitmap.getHeight()];
        markedBitmap.getPixels(pixels, 0, markedBitmap.getWidth(), 0, 0,
                markedBitmap.getWidth(), markedBitmap.getHeight());

        int[] colorArray = new int[4 * pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            colorArray[4 * i] = Color.alpha(pixels[i]);
            colorArray[4 * i + 1] = Color.red(pixels[i]);
            colorArray[4 * i + 2] = Color.green(pixels[i]);
            colorArray[4 * i + 3] = Color.blue(pixels[i]);
        }

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
            resultValue.setWatermarkBitmap(BitmapUtils.StringToBitmap(resultString));
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

    /**
     * Replace the wrong rgb number in a form of binary,
     * the only case is 0 - 1 = 9, so, we need to replace
     * all nines to zero.
     */
    private void replaceNinesJ(int[] inputArray) {
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i] == 9) {
                inputArray[i] = 0;
            }
        }
    }

    /**
     * Int array to string.
     */
    private String intArrayToStringJ(int[] inputArray) {
        StringBuilder binary = new StringBuilder();
        for (int num : inputArray) {
            binary.append(num);
        }
        return binary.toString();
    }


    /**
     * Get text between two strings. Passed limiting strings are not
     * included into result.
     *
     * @param text Text to search in.
     */
    private String getBetweenStrings(String text, boolean isText, DetectFinishListener listener) {
        String result = null;
        if (isText) {
            try {
                result = text.substring(text.indexOf(LSB_TEXT_PREFIX_FLAG) + LSB_TEXT_SUFFIX_FLAG.length(),
                        text.length());
                result = result.substring(0, result.indexOf(LSB_TEXT_SUFFIX_FLAG));
            } catch (StringIndexOutOfBoundsException e) {
                listener.onFailure(ERROR_NO_WATERMARK_FOUND);
            }
        } else {
            try {
                result = text.substring(text.indexOf(LSB_IMG_PREFIX_FLAG) + LSB_IMG_SUFFIX_FLAG.length(),
                        text.length());
                result = result.substring(0, result.indexOf(LSB_IMG_SUFFIX_FLAG));
            } catch (StringIndexOutOfBoundsException e) {
                listener.onFailure(ERROR_NO_WATERMARK_FOUND);
            }
        }

        return result;
    }
}
