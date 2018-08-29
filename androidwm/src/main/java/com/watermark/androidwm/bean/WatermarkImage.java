package com.watermark.androidwm.bean;

import android.graphics.Bitmap;

/**
 * It's a wrapper of the watermark image.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 * @since 29/08/2018
 */
public class WatermarkImage {
    private Bitmap image;
    private WatermarkPosition position;

    WatermarkImage(Bitmap image, WatermarkPosition position) {
        this.image = image;
        this.position = position;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public WatermarkPosition getPosition() {
        return position;
    }

    public void setPosition(WatermarkPosition position) {
        this.position = position;
    }

    /**
     * convert the bitmap into watermark image.
     *
     * @param bitmap input bitmap
     * @return WatermarkImage
     */
    public static WatermarkImage bitmap2WMimage(Bitmap bitmap,
                                                WatermarkPosition position) {
        return new WatermarkImage(bitmap, position);
    }
}
