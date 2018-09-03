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
package com.watermark.androidwm.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.Display;
import android.view.WindowManager;

import com.watermark.androidwm.bean.WatermarkImage;
import com.watermark.androidwm.bean.WatermarkText;

/**
 * Util class for operations with {@link Bitmap}.
 *
 * @author huangyz0918
 */
public class BitmapUtils {

    /**
     * Get screen width in pixel.
     *
     * @return the pixel in screen, if we cannot get the
     * {@link WindowManager}, return 0.
     */
    private static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return 0;
    }

    /**
     * build a bitmap from a text.
     *
     * @return {@link Bitmap} the bitmap return.
     */
    public static Bitmap textAsBitmap(Context context, WatermarkText watermarkText, Bitmap backgroundImg) {
        TextPaint watermarkPaint = new TextPaint();
        watermarkPaint.setColor(watermarkText.getTextColor());
        watermarkPaint.setStyle(watermarkText.getTextStyle());

        if (watermarkText.getTextAlpha() >= 0 && watermarkText.getTextAlpha() <= 255) {
            watermarkPaint.setAlpha(watermarkText.getTextAlpha());
        }

        watermarkPaint.setTextSize((float) watermarkText.getTextSize() *
                context.getResources().getDisplayMetrics().density
                * (backgroundImg.getWidth() / getScreenWidth(context)));

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
                watermarkPaint, mTextMaxWidth, android.text.Layout.Alignment.ALIGN_NORMAL, 2.0f,
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
        float scaleWidth = (backgroundImg.getWidth() * size) / bitmapWidth;
        float scaleHeight = (float) (watermarkImg.getHeight() / watermarkImg.getWidth()) * scaleWidth;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(watermarkImg, 0, 0,
                bitmapWidth, bitmapHeight, matrix, true);
    }

    /**
     * Compute a color's brightness value.
     *
     * @param rgb the color's RGB values as an integer (0xRRGGBB)
     */
    static float getBrightness(int rgb) {
        int red = (rgb >> 16) & 0xff;
        int green = (rgb >> 8) & 0xff;
        int blue = (rgb) & 0xff;
        return (float) ((.2126 * red + .7152 * green + .0722 * blue) / 255);
    }

    /**
     * Converting the color form HSV to RGB.
     */
    public static int hsvToRgb(float hue, float saturation, float value) {

        int h = (int) (hue * 6);
        float f = hue * 6 - h;
        float p = value * (1 - saturation);
        float q = value * (1 - f * saturation);
        float t = value * (1 - (1 - f) * saturation);

        switch (h) {
            case 0:
                return rgbToInteger(value, t, p);
            case 1:
                return rgbToInteger(q, value, p);
            case 2:
                return rgbToInteger(p, value, t);
            case 3:
                return rgbToInteger(p, q, value);
            case 4:
                return rgbToInteger(t, p, value);
            case 5:
                return rgbToInteger(value, p, q);
            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }
    }

    /**
     * Converting the RGB value to a String.
     */
    private static int rgbToInteger(float r, float g, float b) {
        int R = Math.round(255 * r);
        int G = Math.round(255 * g);
        int B = Math.round(255 * b);

        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;

        return 0xFF000000 | R | G | B;
    }
}
