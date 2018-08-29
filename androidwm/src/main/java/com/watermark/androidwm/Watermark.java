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

import com.watermark.androidwm.bean.WatermarkImage;
import com.watermark.androidwm.bean.WatermarkPosition;
import com.watermark.androidwm.bean.WatermarkText;

import java.util.List;

/**
 * The main class for watermark processing library.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class Watermark {
    private WatermarkText inputText;
    private WatermarkImage watermarkImg;
    private Bitmap backgroundImg;
    private Context context;
    private List<WatermarkImage> wmBitmapList;
    private List<WatermarkText> wmTextList;

    Watermark(@NonNull Context context,
              @NonNull Bitmap backgroundImg,
              @Nullable WatermarkImage watermarkImg,
              @Nullable List<WatermarkImage> wmBitmapList,
              @Nullable WatermarkText inputText,
              @Nullable List<WatermarkText> wmTextList) {

        this.context = context;
        this.watermarkImg = watermarkImg;
        this.wmBitmapList = wmBitmapList;
        this.backgroundImg = backgroundImg;
        this.wmTextList = wmTextList;
        this.inputText = inputText;
    }

    public Context getContext() {
        return context;
    }

    public WatermarkText getInputText() {
        return inputText;
    }

    public Bitmap getBackgroundImg() {
        return backgroundImg;
    }

    public WatermarkImage getWatermarkImg() {
        return watermarkImg;
    }

    public List<WatermarkImage> getWmBitmapList() {
        return wmBitmapList;
    }

    public List<WatermarkText> getWmTextList() {
        return wmTextList;
    }

    /**
     * set the position of a single image.
     *
     * @param position the position of image watermark.
     */
    public void setImagePosition(@NonNull WatermarkPosition position) {

    }

    /**
     * set the position of a single text.
     *
     * @param position the position of text watermark.
     */
    public void setTextPosition(@NonNull WatermarkPosition position) {

    }
}
