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

    WatermarkText(String text, WatermarkPosition position) {
        this.text = text;
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public WatermarkPosition getPosition() {
        return position;
    }

    public void setPosition(WatermarkPosition position) {
        this.position = position;
    }

    /**
     * convert the String into watermark text.
     *
     * @param text input String
     * @return WatermarkText
     */
    public static WatermarkText string2WMimage(String text,
                                               WatermarkPosition position) {
        return new WatermarkText(text, position);
    }
}
