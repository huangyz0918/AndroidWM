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

import com.watermark.androidwm.listener.BuildFinishListener;
import com.watermark.androidwm.bean.AsyncTaskParams;
import com.watermark.androidwm.utils.BitmapUtils;

import static com.watermark.androidwm.utils.Constant.LSB_IMG_PREFIX_FLAG;
import static com.watermark.androidwm.utils.Constant.LSB_IMG_SUFFIX_FLAG;
import static com.watermark.androidwm.utils.Constant.LSB_TEXT_PREFIX_FLAG;
import static com.watermark.androidwm.utils.Constant.LSB_TEXT_SUFFIX_FLAG;

/**
 * This is a background task for adding the specific invisible text
 * into the background image. We don't need to read every pixel's
 * RGB value, we just read the length values that can put our encrypted
 * text in.
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
        Bitmap watermarkBitmap = params[0].getWatermarkImg();
        int[] backgroundColorArray;

        if (backgroundBitmap == null) {
            listener.onFailure("No background image! please load an image in your WatermarkBuilder!");
            return null;
        }

        // resize the watermark bitmap.
        // convert the watermark bitmap into a String.
        if (watermarkBitmap != null) {
            if (params[0].getMaxImageSize() > 0) {
                watermarkBitmap = BitmapUtils.resizeBitmap(watermarkBitmap, params[0].getMaxImageSize());
            }
            watermarkString = BitmapUtils.BitmapToString(watermarkBitmap);
        }

        if (watermarkString == null) {
            listener.onFailure("No input text or image! please load an " +
                    "image or a text in your WatermarkBuilder!");
            return null;
        }

        // resize the background bitmap and create the empty output bitmap.
        if (params[0].getMaxImageSize() > 0) {
            backgroundBitmap = BitmapUtils.resizeBitmap(backgroundBitmap, params[0].getMaxImageSize());
        }

        Bitmap outputBitmap = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(),
                backgroundBitmap.getConfig());

        // convert the background bitmap into pixel array.
        int[] backgroundPixels = new int[backgroundBitmap.getWidth() * backgroundBitmap.getHeight()];
        backgroundBitmap.getPixels(backgroundPixels, 0, backgroundBitmap.getWidth(), 0, 0,
                backgroundBitmap.getWidth(), backgroundBitmap.getHeight());

        backgroundColorArray = new int[4 * backgroundPixels.length];

        for (int i = 0; i < backgroundPixels.length; i++) {
            backgroundColorArray[4 * i] = Color.alpha(backgroundPixels[i]);
            backgroundColorArray[4 * i + 1] = Color.red(backgroundPixels[i]);
            backgroundColorArray[4 * i + 2] = Color.green(backgroundPixels[i]);
            backgroundColorArray[4 * i + 3] = Color.blue(backgroundPixels[i]);
        }

        // convert the Sting into a binary string, and, replace the single digit number.
        // using the rebuilt pixels to create a new watermarked image.
        String watermarkBinary = stringToBinary(watermarkString);

        if (watermarkBitmap != null) {
            watermarkBinary = LSB_IMG_PREFIX_FLAG + watermarkBinary + LSB_IMG_SUFFIX_FLAG;
        } else {
            watermarkBinary = LSB_TEXT_PREFIX_FLAG + watermarkBinary + LSB_TEXT_SUFFIX_FLAG;
        }

        int[] watermarkColorArray = stringToIntArray(watermarkBinary);
        if (watermarkColorArray.length > backgroundColorArray.length) {
            listener.onFailure("The Pixels in background are too small to put the watermark in, " +
                    "the data has been lost! Please make sure the maxImageSize is bigger enough!");
        } else {
            int chunkSize = watermarkColorArray.length;
            int numOfChunks = (int) Math.ceil((double) backgroundColorArray.length / chunkSize);
            for (int i = 0; i < numOfChunks - 1; i++) {
                int start = i * chunkSize;
                for (int j = 0; j < chunkSize; j++) {
                    backgroundColorArray[start + j] = replaceSingleDigit(backgroundColorArray[start + j]
                            , watermarkColorArray[j]);
                }
            }

            for (int i = 0; i < backgroundPixels.length; i++) {
                int color = Color.argb(
                        backgroundColorArray[4 * i],
                        backgroundColorArray[4 * i + 1],
                        backgroundColorArray[4 * i + 2],
                        backgroundColorArray[4 * i + 3]
                );
                backgroundPixels[i] = color;
            }

            outputBitmap.setPixels(backgroundPixels, 0, backgroundBitmap.getWidth(), 0, 0,
                    backgroundBitmap.getWidth(), backgroundBitmap.getHeight());

            return outputBitmap;

        }
        return null;
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
    private String stringToBinary(String inputText) {
        byte[] bytes = inputText.getBytes();
        StringBuilder binary = new StringBuilder();
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
     * String to integer array.
     */
    private int[] stringToIntArray(String inputString) {
        char[] strArray = inputString.toCharArray();
        int[] num = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            num[i] = strArray[i] - '0';
        }
        return num;
    }

    /**
     * get the single digit number and set it to the target one.
     */
    private int replaceSingleDigit(int target, int singleDigit) {
        return (target / 10) * 10 + singleDigit;
    }

    /**
     * to split an array into chunks of equal size. The last chunk
     * may be smaller than the rest.
     */
    static int[][] chunkArray(int[] array, int chunkSize) {
        int numOfChunks = (int) Math.ceil((double) array.length / chunkSize);
        int[][] output = new int[numOfChunks][];

        for (int i = 0; i < numOfChunks; ++i) {
            int start = i * chunkSize;
            int length = Math.min(array.length - start, chunkSize);

            int[] temp = new int[length];
            System.arraycopy(array, start, temp, 0, length);
            output[i] = temp;
        }

        return output;
    }

}
