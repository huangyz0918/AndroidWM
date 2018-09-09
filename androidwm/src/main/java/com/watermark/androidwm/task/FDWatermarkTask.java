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
import android.util.Log;

import com.watermark.androidwm.listener.BuildFinishListener;
import com.watermark.androidwm.bean.AsyncTaskParams;

/**
 * This is a tack that use Fast Fourier Transform for an image, to
 * build the image and text watermark into a frequency domain.
 * <p>
 * TODO: build the class for FFTUtils and verify the methods and classes.
 * TODO: build another AsyncTask that can detect the watermark in the picture.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class FDWatermarkTask extends AsyncTask<AsyncTaskParams, Void, Bitmap> {

    static {
        System.loadLibrary("native-lib");
    }

    private BuildFinishListener<Bitmap> listener;

    public FDWatermarkTask(BuildFinishListener<Bitmap> callback) {
        this.listener = callback;
    }

    @Override
    protected Bitmap doInBackground(AsyncTaskParams... params) {
//        Bitmap backgroundImg = params[0].getBackgroundImg();
//        Bitmap watermarkImg = params[0].getWatermarkImg();

        Log.d("===>", "string from naive: " + stringFromJNI());

        //TODO: change the return value.
        return params[0].getWatermarkImg();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (listener != null) {
            if (bitmap != null) {
                listener.onSuccess(bitmap);
            } else {
                listener.onFailure("created watermark failed!");
            }
        }
        super.onPostExecute(bitmap);
    }

    public native String stringFromJNI();

}