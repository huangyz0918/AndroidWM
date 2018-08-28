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

/**
 * The main class for watermark processing library.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class WaterMark {
    private String inputText;
    private Bitmap inputBitmap;
    private Context context;

    WaterMark(@NonNull Context context,
              @NonNull Bitmap inputBitmap,
              @NonNull String inputText) {

        this.context = context;
        this.inputBitmap = inputBitmap;
        this.inputText = inputText;

    }
}