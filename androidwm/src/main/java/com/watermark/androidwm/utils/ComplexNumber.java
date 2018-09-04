/*
 *    Copyright 2018 huangyz0918
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.watermark.androidwm.utils;

/**
 * Complex number class.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class ComplexNumber {

    private final double re;   // the real part
    private final double im;   // the imaginary part

    // create a new object with the given real and imaginary parts
    public ComplexNumber(double real, double imag) {
        re = real;
        im = imag;
    }

    public static ComplexNumber getComplexNumber(int rgb) {
        return new ComplexNumber(getBrightness(rgb), 0.);
    }


    public ComplexNumber(ComplexNumber a) {
        re = a.re();
        im = a.im();
    }

    // return a string representation of the invoking Complex object
    public String toString() {
        if (im == 0) {
            return String.valueOf(re);
        }
        if (re == 0) {
            return im + "i";
        }
        if (im < 0) {
            return re + " - " + (-im) + "i";
        }
        return re + " + " + im + "i";
    }

    // return abs/modulus/magnitude and angle/phase/argument
    public double abs() {
        return Math.hypot(re, im);
    }  // Math.sqrt(re*re + im*im)

    public double phase() {
        return Math.atan2(im, re);
    }  // between -pi and pi

    // return a new Complex object whose value is (this + b)
    public ComplexNumber plus(ComplexNumber b) {
        ComplexNumber a = this;             // invoking object
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new ComplexNumber(real, imag);
    }

    // return a new Complex object whose value is (this - b)
    public ComplexNumber minus(ComplexNumber b) {
        ComplexNumber a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new ComplexNumber(real, imag);
    }

    // return a new Complex object whose value is (this * b)
    public ComplexNumber times(ComplexNumber b) {
        ComplexNumber a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new ComplexNumber(real, imag);
    }

    // scalar multiplication
    // return a new object whose value is (this * alpha)
    public ComplexNumber times(double alpha) {
        return new ComplexNumber(alpha * re, alpha * im);
    }

    // return a new Complex object whose value is the conjugate of this
    public ComplexNumber conjugate() {
        return new ComplexNumber(re, -im);
    }

    // return a new Complex object whose value is the reciprocal of this
    public ComplexNumber reciprocal() {
        double scale = re * re + im * im;
        return new ComplexNumber(re / scale, -im / scale);
    }

    // return the real or imaginary part
    public double re() {
        return re;
    }

    public double im() {
        return im;
    }

    // return a / b
    public ComplexNumber divides(ComplexNumber b) {
        ComplexNumber a = this;
        return a.times(b.reciprocal());
    }

    // return a new Complex object whose value is the complex exponential of this
    public ComplexNumber exp() {
        return new ComplexNumber(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    // return a new Complex object whose value is the complex sine of this
    public ComplexNumber sin() {
        return new ComplexNumber(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex cosine of this
    public ComplexNumber cos() {
        return new ComplexNumber(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    // return a new Complex object whose value is the complex tangent of this
    public ComplexNumber tan() {
        return sin().divides(cos());
    }

    // a static version of plus
    public static ComplexNumber plus(ComplexNumber a, ComplexNumber b) {
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new ComplexNumber(real, imag);
    }

    /**
     * Compute a color's brightness value.
     *
     * @param rgb the color's RGB values as an integer (0xRRGGBB)
     */
    private static float getBrightness(int rgb) {
        int red = (rgb >> 16) & 0xff;
        int green = (rgb >> 8) & 0xff;
        int blue = (rgb) & 0xff;
        return (float) ((.2126 * red + .7152 * green + .0722 * blue) / 255);
    }

}
