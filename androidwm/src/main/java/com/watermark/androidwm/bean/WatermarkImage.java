package com.watermark.androidwm.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

/**
 * It's a wrapper of the watermark image.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 * @since 29/08/2018
 */
public class WatermarkImage {
    private Bitmap image;
    private boolean isEncrypted = false;
    private boolean isVisible = true;
    private int alpha = 50;
    private double size = 0.2;
    // set the default values for the position.
    private WatermarkPosition position = new WatermarkPosition(0, 0, 0);

    /**
     * Constructors for WatermarkImage
     */
    public WatermarkImage(Bitmap image) {
        this.image = image;
    }

    public WatermarkImage(Bitmap image, WatermarkPosition position) {
        this.image = image;
        this.position = position;
    }

    public WatermarkImage(ImageView imageView) {
        watermarkFromImageView(imageView);
    }

    /**
     * Getters for those attrs.
     */
    public Bitmap getImage() {
        return image;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public int getAlpha() {
        return alpha;
    }

    public WatermarkPosition getPosition() {
        return position;
    }

    public double getSize() {
        return size;
    }

    /**
     * Setters for those attrs.
     */
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

    public WatermarkImage setRotation(double rotation) {
        this.position.setRotation(rotation);
        return this;
    }

    /**
     * @param imageAlpha can be set to 0-255.
     */
    public WatermarkImage setImageAlpha(int imageAlpha) {
        this.alpha = imageAlpha;
        return this;
    }

    /**
     * @param size can be set to 0-1 as the proportion of
     *             background image.
     */
    public WatermarkImage setSize(double size) {
        this.size = size;
        return this;
    }

    /**
     * @param isVisible whether to set this watermark invisible.
     */
    public WatermarkImage setWatermarkVisibility(boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    /**
     * @param isEncrypted whether to encrypted this watermark.
     */
    public WatermarkImage setWatermarkEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
        return this;
    }

    /**
     * load a bitmap as watermark image from a ImageView.
     *
     * @param imageView the ImageView we need to use.
     */
    private void watermarkFromImageView(ImageView imageView) {
        imageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        this.image = drawable.getBitmap();
    }

}
