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
package com.watermark.androidwm;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public class WaterMarkBuilder {
    private Bitmap inputImage;
    private String inputWaterMarkText;
    private Context context;

    /**
     * Sets the {@link Context} application context
     * as the args which ready for adding a watermark.
     *
     * @param context The context to use.
     * @return This builder.
     */
    public WaterMarkBuilder loadContext(@NonNull Context context) {
        this.context = context;
        return this;
    }

    /**
     * Sets the {@link Bitmap} inputImage as the
     * args which ready for adding a watermark.
     *
     * @param inputImage The image bitmap to use.
     * @return This builder.
     */
    public WaterMarkBuilder loadImage(@NonNull Bitmap inputImage) {
        this.inputImage = inputImage;
        return this;
    }

    /**
     * Sets the {@link String} as the args
     * which ready for adding to a watermark.
     *
     * @param inputText The text to add.
     * @return This builder.
     */
    public WaterMarkBuilder loadWaterMarkText(@NonNull String inputText) {
        this.inputWaterMarkText = inputText;
        return this;
    }

    /**
     * let the watermark builder to build a new watermark object
     *
     * @return a new watermark object
     */
    public WaterMark getWaterMark() {
        return new WaterMark(context, inputImage, inputWaterMarkText);
    }
}
