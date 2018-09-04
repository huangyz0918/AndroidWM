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
 * In FD mode, all the task will return a bitmap;
 * TODO: remove the annotation of SuppressWarnings after this class finished.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
@SuppressWarnings("PMD")
public class FDDetectionTask extends AsyncTask<DetectionParams, Void, Bitmap> {

    private DetectFinishListener listener;

    public FDDetectionTask(DetectFinishListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    @Override
    protected Bitmap doInBackground(DetectionParams... detectionParams) {
        return null;
    }
}