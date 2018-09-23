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

import org.jtransforms.fft.DoubleFFT_1D;

import static com.watermark.androidwm.utils.BitmapUtils.bitmap2ARGBArray;
import static com.watermark.androidwm.utils.BitmapUtils.getBitmapPixels;
import static com.watermark.androidwm.utils.Constant.ERROR_CREATE_FAILED;
import static com.watermark.androidwm.utils.Constant.ERROR_NO_WATERMARKS;
import static com.watermark.androidwm.utils.Constant.ERROR_PIXELS_NOT_ENOUGH;
import static com.watermark.androidwm.utils.Constant.FD_IMG_PREFIX_FLAG;
import static com.watermark.androidwm.utils.Constant.FD_IMG_SUFFIX_FLAG;
import static com.watermark.androidwm.utils.Constant.FD_TEXT_PREFIX_FLAG;
import static com.watermark.androidwm.utils.Constant.FD_TEXT_SUFFIX_FLAG;
import static com.watermark.androidwm.utils.StringUtils.copyFromIntArray;
import static com.watermark.androidwm.utils.StringUtils.replaceSingleDigit;
import static com.watermark.androidwm.utils.StringUtils.stringToBinary;
import static com.watermark.androidwm.utils.StringUtils.stringToIntArray;

/**
 * This is a tack that use Fast Fourier Transform for an image, to
 * build the image and text watermark into a frequency domain.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class FDWatermarkTask extends AsyncTask<AsyncTaskParams, Void, Bitmap> {

    private BuildFinishListener<Bitmap> listener;

    public FDWatermarkTask(BuildFinishListener<Bitmap> callback) {
        this.listener = callback;
    }

    @Override
    protected Bitmap doInBackground(AsyncTaskParams... params) {
        Bitmap backgroundBitmap = params[0].getBackgroundImg();
        String watermarkString = params[0].getWatermarkText();
        Bitmap watermarkBitmap = params[0].getWatermarkImg();

        // checkout if the kind of input watermark is bitmap or a string text.
        // add convert them into an ascii string.
        if (watermarkBitmap != null) {
            watermarkString = BitmapUtils.bitmapToString(watermarkBitmap);
        }

        if (watermarkString == null) {
            listener.onFailure(ERROR_NO_WATERMARKS);
            return null;
        }

        String watermarkBinary = stringToBinary(watermarkString);
        if (watermarkBitmap != null) {
            watermarkBinary = FD_IMG_PREFIX_FLAG + watermarkBinary + FD_IMG_SUFFIX_FLAG;
        } else {
            watermarkBinary = FD_TEXT_PREFIX_FLAG + watermarkBinary + FD_TEXT_SUFFIX_FLAG;
        }

        int[] watermarkColorArray = stringToIntArray(watermarkBinary);

        Bitmap outputBitmap = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(),
                backgroundBitmap.getConfig());

        // convert the background bitmap into pixel array.
        int[] backgroundPixels = getBitmapPixels(backgroundBitmap);
        int[] backgroundColorArray = bitmap2ARGBArray(backgroundPixels);

        // TODO: the two arrays make the maxsize smaller than 1024.
        double[] backgroundColorArrayD = copyFromIntArray(backgroundColorArray);
        DoubleFFT_1D backgroundFFT = new DoubleFFT_1D(backgroundColorArrayD.length);
        backgroundFFT.realForward(backgroundColorArrayD);

        if (watermarkColorArray.length > backgroundColorArrayD.length) {
            listener.onFailure(ERROR_PIXELS_NOT_ENOUGH);
        } else {

            for (int i = 0; i < watermarkColorArray.length; i++) {
                backgroundColorArrayD[i] = replaceSingleDigit(backgroundColorArrayD[i]
                        , watermarkColorArray[i]);
            }

            backgroundFFT.realInverse(backgroundColorArrayD, 0, true);

            for (int i = 0; i < backgroundPixels.length; i++) {
                int color = Color.argb(
                        (int) backgroundColorArrayD[4 * i],
                        (int) backgroundColorArrayD[4 * i + 1],
                        (int) backgroundColorArrayD[4 * i + 2],
                        (int) backgroundColorArrayD[4 * i + 3]
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
    protected void onPostExecute(Bitmap bitmap) {
        if (listener != null) {
            if (bitmap != null) {
                listener.onSuccess(bitmap);
            } else {
                listener.onFailure(ERROR_CREATE_FAILED);
            }
        }
        super.onPostExecute(bitmap);
    }

}