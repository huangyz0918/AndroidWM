package com.watermark.androidwm.utils;

/**
 * Complex number.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class ComplexNumber {
    private double re, im;

    public ComplexNumber() {
    }

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public ComplexNumber(ComplexNumber source) {
        if (source != null) {
            re = source.re;
            im = source.im;
        }
    }

    public double getRe() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public double getIm() {
        return im;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public ComplexNumber getConjugate() {
        return new ComplexNumber(re, im * -1);
    }

    public ComplexNumber add(ComplexNumber op) {
        return new ComplexNumber(re + op.getRe(), im + op.getIm());
    }

    public ComplexNumber sub(ComplexNumber op) {
        return new ComplexNumber(re - op.getRe(), im - op.getIm());
    }

    public ComplexNumber mul(ComplexNumber op) {
        return new ComplexNumber(re * op.getRe() - im * op.getIm(), re * op.getIm() + im * op.getRe());
    }

    public ComplexNumber div(ComplexNumber op) {
        ComplexNumber result = new ComplexNumber(this);
        result = result.mul(op.getConjugate());
        double opNormSq = op.getRe() * op.getRe() + op.getIm() * op.getIm();
        result.setRe(result.getRe() / opNormSq);
        result.setIm(result.getIm() / opNormSq);
        return result;
    }

    public static ComplexNumber fromPolar(double magnitude, double angle) {
        ComplexNumber result = new ComplexNumber();
        result.setRe(magnitude * Math.cos(angle));
        result.setIm(magnitude * Math.sin(angle));
        return result;
    }

    public double getMagnitude() {
        return Math.sqrt(re * re + im * im);
    }

    public double getArgument() {
        return Math.atan2(im, re);
    }

    @Override
    public String toString() {
        return re + "+i." + im + " (" + getMagnitude() + ".exp(i " + (getArgument() / Math.PI) + " PI)";
    }

}