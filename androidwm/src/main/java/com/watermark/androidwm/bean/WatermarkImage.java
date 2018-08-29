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
    // set the default values for the position.
    private WatermarkPosition position = new WatermarkPosition(0, 0, 0);

    public WatermarkImage(Bitmap image) {
        this.image = image;
    }

    public WatermarkImage(Bitmap image, WatermarkPosition position) {
        this.image = image;
        this.position = position;
    }

    public Bitmap getImage() {
        return image;
    }

    public WatermarkPosition getPosition() {
        return position;
    }

    public WatermarkImage setPosition(WatermarkPosition position) {
        this.position = position;
        return this;
    }

    public WatermarkImage setPositionX(double x) {
        this.position.setPositionX(x);
        return this;
    }

    public WatermarkImage setPositionY(double y) {
        this.position.setPositionY(y);
        return this;
    }

    public WatermarkImage setPositionRotation(double rotation) {
        this.position.setPositionX(rotation);
        return this;
    }

}
