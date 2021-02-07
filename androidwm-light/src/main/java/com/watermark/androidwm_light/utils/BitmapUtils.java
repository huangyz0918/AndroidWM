/*
 *    Copyright 2018 Yizheng Huang
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
package com.watermark.androidwm_light.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.TypedValue;

import com.watermark.androidwm_light.bean.WatermarkImage;
import com.watermark.androidwm_light.bean.WatermarkText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import timber.log.Timber;

/**
 * Util class for operations with {@link Bitmap}.
 *
 * @author huangyz0918
 */
public class BitmapUtils {

    /**
     * build a bitmap from a text.
     *
     * @return {@link Bitmap} the bitmap return.
     */
    public static Bitmap textAsBitmap(Context context, WatermarkText watermarkText) {
        TextPaint watermarkPaint = new TextPaint();
        watermarkPaint.setColor(watermarkText.getTextColor());
        watermarkPaint.setStyle(watermarkText.getTextStyle());

        if (watermarkText.getTextAlpha() >= 0 && watermarkText.getTextAlpha() <= 255) {
            watermarkPaint.setAlpha(watermarkText.getTextAlpha());
        }

        float value = (float) watermarkText.getTextSize();
        int pixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, context.getResources().getDisplayMetrics());
        watermarkPaint.setTextSize(pixel);

        if (watermarkText.getTextShadowBlurRadius() != 0
                || watermarkText.getTextShadowXOffset() != 0
                || watermarkText.getTextShadowYOffset() != 0) {
            watermarkPaint.setShadowLayer(watermarkText.getTextShadowBlurRadius(),
                    watermarkText.getTextShadowXOffset(),
                    watermarkText.getTextShadowYOffset(),
                    watermarkText.getTextShadowColor());
        }

        if (watermarkText.getTextFont() != 0) {
            Typeface typeface = ResourcesCompat.getFont(context, watermarkText.getTextFont());
            watermarkPaint.setTypeface(typeface);
        }

        watermarkPaint.setAntiAlias(true);
        watermarkPaint.setTextAlign(Paint.Align.LEFT);
        watermarkPaint.setStrokeWidth(5);

        float baseline = (int) (-watermarkPaint.ascent() + 1f);
        Rect bounds = new Rect();
        watermarkPaint.getTextBounds(watermarkText.getText(),
                0, watermarkText.getText().length(), bounds);

        int boundWidth = bounds.width() + 20;
        int mTextMaxWidth = (int) watermarkPaint.measureText(watermarkText.getText());
        if (boundWidth > mTextMaxWidth) {
            boundWidth = mTextMaxWidth;
        }
        StaticLayout staticLayout = new StaticLayout(watermarkText.getText(),
                0, watermarkText.getText().length(),
                watermarkPaint, mTextMaxWidth, android.text.Layout.Alignment.ALIGN_NORMAL, 1.0f,
                2.0f, false);

        int lineCount = staticLayout.getLineCount();
        int height = (int) (baseline + watermarkPaint.descent() + 3) * lineCount;
        Bitmap image = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        if (boundWidth > 0 && height > 0) {
            image = Bitmap.createBitmap(boundWidth, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(image);
        canvas.drawColor(watermarkText.getBackgroundColor());
        staticLayout.draw(canvas);
        return image;
    }

    /**
     * this method is for image resizing, we should get
     * the size from the input {@link WatermarkImage}
     * objects, and, set the size from 0 to 1 ,which means:
     * size = watermarkImageWidth / backgroundImageWidth
     *
     * @return {@link Bitmap} the new bitmap.
     */
    public static Bitmap resizeBitmap(Bitmap watermarkImg, float size, Bitmap backgroundImg) {
        int bitmapWidth = watermarkImg.getWidth();
        int bitmapHeight = watermarkImg.getHeight();
        float scale = (backgroundImg.getWidth() * size) / bitmapWidth;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(watermarkImg, 0, 0,
                bitmapWidth, bitmapHeight, matrix, true);
    }

    /**
     * Saving a bitmap instance into local PNG.
     */
    public static void saveAsPNG(Bitmap inputBitmap, String filePath, boolean withTime) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Timber.e("SD card is not available/writable right now.");
        }

        @SuppressLint("SimpleDateFormat") String timeStamp =
                new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US).format(Calendar.getInstance().getTime());

        FileOutputStream out = null;
        try {
            if (withTime) {
                out = new FileOutputStream(filePath + timeStamp + ".png");
            } else {
                out = new FileOutputStream(filePath + "watermarked" + ".png");
            }
            inputBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            Timber.e(e.toString());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Timber.e(e.toString());
            }
        }
    }

}
