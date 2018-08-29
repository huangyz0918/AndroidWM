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

import com.watermark.androidwm.bean.WatermarkImage;
import com.watermark.androidwm.bean.WatermarkPosition;
import com.watermark.androidwm.bean.WatermarkText;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class for setting default structural classes for watermark to use.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public final class WatermarkBuilder {
    private Context context;
    private Bitmap backgroundImg;
    private WatermarkImage watermarkImage;
    private WatermarkText watermarkText;
    private List<WatermarkText> watermarkTexts = new ArrayList<>();
    private List<WatermarkImage> watermarkBitmaps = new ArrayList<>();

    private WatermarkBuilder(@NonNull Context context, @NonNull Bitmap backgroundImg) {
        this.context = context;
        this.backgroundImg = backgroundImg;
    }

    /**
     * to get an instance form class.
     *
     * @return instance of WatermarkBuilder
     */
    @SuppressWarnings("PMD")
    public static WatermarkBuilder create(Context context, Bitmap backgroundImg) {
        return new WatermarkBuilder(context, backgroundImg);
    }

    /**
     * Sets the {@link String} as the args
     * which ready for adding to a watermark.
     * Using the default position.
     *
     * @param inputText The text to add.
     * @return This builder.
     */
    public WatermarkBuilder loadWatermarkText(@NonNull String inputText) {
        watermarkText = new WatermarkText(inputText);
        return this;
    }

    /**
     * Sets the {@link String} as the args
     * which ready for adding to a watermark.
     * Using the new position.
     *
     * @param inputText The text to add.
     * @param position  The position in the background image.
     * @return This builder.
     */
    public WatermarkBuilder loadWatermarkText(@NonNull String inputText,
                                              @NonNull WatermarkPosition position) {
        watermarkText = new WatermarkText(inputText, position);
        return this;
    }

    /**
     * Sets the {@link String} as the args
     * which ready for adding to a watermark.
     *
     * @param watermarkString The watermark text object.
     * @return This builder.
     */
    public WatermarkBuilder loadWatermarkText(@NonNull WatermarkText watermarkString) {
        watermarkText = watermarkString;
        return this;
    }

    /**
     * Sets the {@link String} as the args
     * which ready for adding to a watermark.
     * And, this is a set of Strings.
     *
     * @param watermarkTexts The texts to add.
     * @return This builder.
     */
    public WatermarkBuilder loadWatermarkTexts(@NonNull List<WatermarkText> watermarkTexts) {
        this.watermarkTexts = watermarkTexts;
        return this;
    }

    /**
     * Sets the {@link Bitmap} as the args
     * which ready for adding to a background.
     * Using the default position.
     *
     * @param wmImg The image to add.
     * @return This builder.
     */
    public WatermarkBuilder loadWatermarkImage(@NonNull Bitmap wmImg) {
        watermarkImage = new WatermarkImage(wmImg);
        return this;
    }

    /**
     * Sets the {@link Bitmap} as the args
     * which ready for adding to a background.
     * Using the new position.
     *
     * @param position The position in the background image.
     * @param wmImg    The bitmap to add into.
     * @return This builder.
     */
    public WatermarkBuilder loadWatermarkImage(@NonNull Bitmap wmImg,
                                               @NonNull WatermarkPosition position) {
        watermarkImage = new WatermarkImage(wmImg, position);
        return this;
    }

    /**
     * Sets the {@link Bitmap} as the args
     * which ready for adding to a background.
     *
     * @param watermarkImg The watermark image object.
     * @return This builder.
     */
    public WatermarkBuilder loadWatermarkImage(@NonNull WatermarkImage watermarkImg) {
        watermarkImage = watermarkImg;
        return this;
    }

    /**
     * Sets the {@link Bitmap} as the args
     * which ready for adding to a background.
     * And, this is a set of bitmaps.
     *
     * @param bitmapList The bitmaps to add.
     * @return This builder.
     */
    public WatermarkBuilder loadWatermarkImages(@NonNull List<WatermarkImage> bitmapList) {
        this.watermarkBitmaps = bitmapList;
        return this;
    }

    /**
     * let the watermark builder to build a new watermark object
     *
     * @return a new watermark object
     */
    public Watermark getWatermark() {
        return new Watermark(
                context,
                backgroundImg,
                watermarkImage,
                watermarkBitmaps,
                watermarkText,
                watermarkTexts
        );
    }
}
