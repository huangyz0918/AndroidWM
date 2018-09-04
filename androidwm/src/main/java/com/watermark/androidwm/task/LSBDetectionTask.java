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
import com.watermark.androidwm.listener.DetectFinishListener;

/**
 * This is a task for watermark image detection.
 * In LSB mode, all the task will return a bitmap;
 * TODO: remove the annotation of SuppressWarnings after this class finished.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
@SuppressWarnings("PMD")
public class LSBDetectionTask extends AsyncTask<DetectionParams, Void, Bitmap> {

    private DetectFinishListener listener;

    public LSBDetectionTask(DetectFinishListener listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(DetectionParams... detectionParams) {
        boolean isText = detectionParams[0].isTextWatermark();
        Bitmap markedBitmap = detectionParams[0].getWatermarkBitmap();
        if (isText) {

        } else {

        }
        return markedBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            listener.onSuccess(bitmap);
        } else {
            listener.onFailure("Cannot decode the watermark!");
        }
        super.onPostExecute(bitmap);
    }

    /**
     * Converting a binary string to a ASCII string.
     */
    private String binaryToString(String inputText) {
        StringBuilder sb = new StringBuilder();
        char[] chars = inputText.replaceAll("\\s", "").toCharArray();
        int[] mapping = {1, 2, 4, 8, 16, 32, 64, 128};

        for (int j = 0; j < chars.length; j += 8) {
            int idx = 0;
            int sum = 0;
            for (int i = 7; i >= 0; i--) {
                if (chars[i + j] == '1') {
                    sum += mapping[idx];
                }
                idx++;
            }
            sb.append(Character.toChars(sum));
        }
        return sb.toString();
    }
}
