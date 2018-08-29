package com.watermark.androidwm.bean;


/**
 * It's a wrapper of the watermark text.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 * @since 29/08/2018
 */
public class WatermarkText {

    private String text;
    private boolean isEncrypted = false;
    private boolean isVisible = true;
    private float alpha = 1;
    // set the default values for the position.
    private WatermarkPosition position = new WatermarkPosition(0, 0, 0);

    public WatermarkText(String text) {
        this.text = text;
    }

    public WatermarkText(String text, WatermarkPosition position) {
        this.text = text;
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public float getAlpha() {
        return alpha;
    }

    public WatermarkPosition getPosition() {
        return position;
    }

    public WatermarkText setPosition(WatermarkPosition position) {
        this.position = position;
        return this;
    }

    public WatermarkText setPositionX(double x) {
        this.position.setPositionX(x);
        return this;
    }

    public WatermarkText setPositionY(double y) {
        this.position.setPositionY(y);
        return this;
    }

    public WatermarkText setRotation(double rotation) {
        this.position.setRotation(rotation);
        return this;
    }

    public WatermarkText setTextAlpha(float textAlpha) {
        this.alpha = textAlpha;
        return this;
    }

    public WatermarkText setWatermarkVisibility(boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public WatermarkText setWatermarkEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
        return this;
    }

}
