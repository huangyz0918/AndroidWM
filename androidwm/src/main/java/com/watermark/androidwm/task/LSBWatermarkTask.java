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
import android.util.Log;

import com.watermark.androidwm.BuildFinishListener;
import com.watermark.androidwm.bean.AsyncTaskParams;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a background task for adding the specific invisible text
 * into the background image. We don't need to read every pixel's
 * RGB value, we just read the length values that can put our encrypted
 * text in.
 * <p>
 * TODO: build another AsyncTask that used for reverting the text.
 * TODO: build RGBConvertTask that can read the pixel in the background image.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class LSBWatermarkTask extends AsyncTask<AsyncTaskParams, Void, Bitmap> {

    private BuildFinishListener<Bitmap> listener;

    public LSBWatermarkTask(BuildFinishListener<Bitmap> callback) {
        this.listener = callback;
    }

    @Override
    protected Bitmap doInBackground(AsyncTaskParams... params) {
        Bitmap backgroundBitmap = params[0].getBackgroundImg();
        String watermarkString = params[0].getWatermarkText();

        int length = watermarkString.getBytes().length;
        Log.d("====>", "doInBackground: text length: " + length);
        int pixelNumber = length * 8 / 3;
        int colorR, colorG, colorB;
        List<Integer> colorList = new ArrayList<>();
        for (int i = 0; i < pixelNumber; i++) {
            int color = backgroundBitmap.getPixel(0, i);
            colorR = Color.red(color);
            colorG = Color.green(color);
            colorB = Color.blue(color);
            colorList.add(colorR);
            colorList.add(colorG);
            colorList.add(colorB);
        }

        Log.d("====>", "doInBackground: color list length: " + colorList.size());

        // TODO: change to resultBitmap
        return backgroundBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap resultBitmap) {
        if (listener != null) {
            if (resultBitmap != null) {
                listener.onSuccess(resultBitmap);
            } else {
                listener.onFailure("created watermark failed!");
            }
        }
        super.onPostExecute(resultBitmap);
    }

    /**
     * Converting a {@link String} text into a binary text.
     */
    public String stringToBinary(String inputText) {
        byte[] bytes = inputText.getBytes();
        StringBuilder binary = new StringBuilder();
        // append 125 zeros as a flag
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }

        return binary.toString();
    }

    /**
     * Converting a binary string to a ASCII string.
     * TODO: This method should be placed in another AsyncTask
     */
    public String binaryToString(String inputText) {
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
