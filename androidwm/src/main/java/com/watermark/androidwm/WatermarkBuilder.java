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
import android.support.annotation.Nullable;

/**
 * A builder class for setting default structural classes for watermark to use.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class WatermarkBuilder {
    private Bitmap backgroundImg;
    private String inputWatermarkText;
    private Context context;
    private Bitmap watermarkImg;

    /**
     * to get an instance form class.
     *
     * @return instance of WatermarkBuilder
     */
    public static WatermarkBuilder getInstance() {
        return new WatermarkBuilder();
    }

    /**
     * Sets the {@link Context} application context
     * as the args which ready for adding a watermark.
     *
     * @param context The context to use.
     * @return This builder.
     */
    public WatermarkBuilder loadContext(@NonNull Context context) {
        this.context = context;
        return this;
    }

    /**
     * Check if {@code context == null}, then return false.
     *
     * @return whether context is bull.
     */
    public boolean isNullContext() {
        return (context == null);
    }

    /**
     * Sets the {@link Bitmap} inputImage as the
     * args which ready for adding a watermark.
     *
     * @param inputImage The image bitmap to use.
     * @return This builder.
     */
    public WatermarkBuilder loadImage(@NonNull Bitmap inputImage) {
        this.backgroundImg = inputImage;
        return this;
    }

    /**
     * Sets the {@link String} as the args
     * which ready for adding to a watermark.
     *
     * @param inputText The text to add.
     * @return This builder.
     */
    public WatermarkBuilder loadWatermarkText(@Nullable String inputText) {
        this.inputWatermarkText = inputText;
        return this;
    }

    /**
     * Sets the {@link Bitmap} as the args
     * which ready for adding to a background.
     *
     * @param wmImg The text to add.
     * @return This builder.
     */
    public WatermarkBuilder loadWatermarkImage(@Nullable Bitmap wmImg) {
        this.watermarkImg = wmImg;
        return this;
    }

    /**
     * let the watermark builder to build a new watermark object
     *
     * @return a new watermark object
     */
    public Watermark getWatermark() {
        return new Watermark(context,
                backgroundImg,
                watermarkImg,
                inputWatermarkText);
    }
}
