package com.watermark.androidwm.bean;


/**
 * It's a wrapper of the watermark text.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 * @since 29/08/2018
 */
public class WatermarkText {

    private String text;
    private WatermarkPosition position;

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

    public WatermarkText setPositionRotation(double rotation) {
        this.position.setPositionX(rotation);
        return this;
    }

}
