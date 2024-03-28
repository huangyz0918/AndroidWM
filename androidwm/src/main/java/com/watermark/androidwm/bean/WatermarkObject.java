package com.watermark.androidwm.bean;

public abstract class WatermarkObject<T> {
    protected int alpha = 50;
    // set the default values for the position.
    protected WatermarkPosition position = new WatermarkPosition(0, 0, 0);
    protected WatermarkPosition origin = new WatermarkPosition(0, 0, 0);

    public WatermarkPosition getPosition() {
        return position;
    }

    public T setPosition(WatermarkPosition position) {
        this.position = position;
        return (T)this;
    }

    public WatermarkPosition getOrigin() {
        return origin;
    }

    public T setOrigin(WatermarkPosition origin) {
        this.origin = origin;
        return (T)this;
    }


    public T setPositionX(double x) {
        this.position.setPositionX(x);
        return (T)this;
    }

    public T setPositionY(double y) {
        this.position.setPositionY(y);
        return (T)this;
    }

    public T setRotation(double rotation) {
        this.position.setRotation(rotation);
        return (T)this;
    }

    public T setOriginX(double x) {
        this.origin.setPositionX(x);
        return (T)this;
    }

    public T setOriginY(double y) {
        this.origin.setPositionY(y);
        return (T)this;
    }
}
