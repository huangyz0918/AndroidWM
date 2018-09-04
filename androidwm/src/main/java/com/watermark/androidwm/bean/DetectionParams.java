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
package com.watermark.androidwm.bean;

import android.graphics.Bitmap;

/**
 * This is a simple class that can help we put multiple primitive
 * parameters into the task.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class DetectionParams {

    private Bitmap watermarkBitmap;
    private boolean isTextWatermark;

    public DetectionParams(Bitmap watermarkBitmap, boolean isText) {
        this.watermarkBitmap = watermarkBitmap;
        this.isTextWatermark = isText;
    }


    /**
     * Getters and Setters for {@link DetectionParams}.
     */
    public Bitmap getWatermarkBitmap() {
        return watermarkBitmap;
    }

    public void setWatermarkBitmap(Bitmap watermarkBitmap) {
        this.watermarkBitmap = watermarkBitmap;
    }

    public boolean isTextWatermark() {
        return isTextWatermark;
    }

    public void setTextWatermark(boolean textWatermark) {
        isTextWatermark = textWatermark;
    }
}
