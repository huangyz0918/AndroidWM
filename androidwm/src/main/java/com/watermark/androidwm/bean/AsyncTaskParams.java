package com.watermark.androidwm.bean;

import android.graphics.Bitmap;

/**
 * This is a simple class that can help we put multiple primitive
 * parameters into the task.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class AsyncTaskParams {
    private Bitmap backgroundImg;
    private String watermarkText;
    private Bitmap watermarkImg;

    public AsyncTaskParams(Bitmap backgroundImg, String watermarkText, Bitmap watermarkImg) {
        this.backgroundImg = backgroundImg;
        this.watermarkText = watermarkText;
        this.watermarkImg = watermarkImg;
    }

    public AsyncTaskParams(Bitmap backgroundImg, Bitmap watermarkImg) {
        this.backgroundImg = backgroundImg;
        this.watermarkImg = watermarkImg;
    }

    public AsyncTaskParams(Bitmap backgroundImg, String watermarkText) {
        this.backgroundImg = backgroundImg;
        this.watermarkText = watermarkText;
    }

    /**
     * Getters and Setters for {@link AsyncTaskParams}.
     */
    public Bitmap getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(Bitmap backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getWatermarkText() {
        return watermarkText;
    }

    public void setWatermarkText(String watermarkText) {
        this.watermarkText = watermarkText;
    }

    public Bitmap getWatermarkImg() {
        return watermarkImg;
    }

    public void setWatermarkImg(Bitmap watermarkImg) {
        this.watermarkImg = watermarkImg;
    }
}