package com.watermark.androidwm.bean;


import android.graphics.Color;
import android.graphics.Paint;
import android.widget.EditText;
import android.widget.TextView;

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
    private int alpha = 50;
    private double size = 20;
    private int color = Color.BLACK;
    private Paint.Style style = Paint.Style.FILL;
    // set the default values for the position.
    private WatermarkPosition position = new WatermarkPosition(0, 0, 0);

    /**
     * Constructors for WatermarkText
     */
    public WatermarkText(String text) {
        this.text = text;
    }

    public WatermarkText(String text, WatermarkPosition position) {
        this.text = text;
        this.position = position;
    }

    /**
     * Getters for those attrs.
     */
    public String getText() {
        return text;
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

    public int getColor() {
        return color;
    }

    public Paint.Style getStyle() {
        return style;
    }

    /**
     * Setters for those attrs.
     */
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

    public WatermarkText setColor(int color) {
        this.color = color;
        return this;
    }

    public WatermarkText setStyle(Paint.Style style) {
        this.style = style;
        return this;
    }

    /**
     * @param textAlpha can be set to 0-255.
     */
    public WatermarkText setTextAlpha(int textAlpha) {
        this.alpha = textAlpha;
        return this;
    }

    /**
     * @param size can be set to normal text size.
     */
    public WatermarkText setSize(double size) {
        this.size = size;
        return this;
    }

    /**
     * @param isVisible whether to set this watermark invisible.
     */
    public WatermarkText setWatermarkVisibility(boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    /**
     * @param isEncrypted whether to encrypted this watermark.
     */
    public WatermarkText setWatermarkEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
        return this;
    }

    /**
     * load a string text as watermark text from a {@link TextView}.
     *
     * @param textView the {@link TextView} we need to use.
     */
    public void textFromTextView(TextView textView) {
        this.text = textView.getText().toString();
    }

    /**
     * load a string text as watermark text from a {@link EditText}.
     *
     * @param editText the {@link EditText} we need to use.
     */
    public void textFromEditText(EditText editText) {
        this.text = editText.getText().toString();
    }

}
