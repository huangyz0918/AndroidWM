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
 * Util class for doing Fast Fourier Transform.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
@SuppressWarnings("PMD")
public class FFTUtils {

    /**
     * compute the FFT of x[], assuming its length is a power of 2.
     * The method is for calculating the FFT.
     *
     * @param x the {@link ComplexNumber} input.
     * @return The result {@link ComplexNumber}.
     */
    public static ComplexNumber[] fft(ComplexNumber[] x) {
        int N = x.length;

        // base case
        if (N == 1) {
            return new ComplexNumber[]{x[0]};
        }

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) {
            throw new RuntimeException("N is not a power of 2");
        }

        // fft of even terms
        ComplexNumber[] even = new ComplexNumber[N / 2];
        for (int k = 0; k < N / 2; k++) {
            even[k] = x[2 * k];
        }
        ComplexNumber[] q = fft(even);

        // fft of odd terms
//        ComplexNumber[] odd = even;  // reuse the array
        for (int k = 0; k < N / 2; k++) {
            even[k] = x[2 * k + 1];
        }
        ComplexNumber[] r = fft(even);

        // combine
        ComplexNumber[] y = new ComplexNumber[N];
        for (int k = 0; k < N / 2; k++) {
            double kth = -2 * k * Math.PI / N;
            ComplexNumber wk = new ComplexNumber(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].plus(wk.times(r[k]));
            y[k + N / 2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }

    /**
     * compute the inverse iFFT of x[], assuming its length is a power of 2
     * The method is for calculating the iFFT.
     *
     * @param x the {@link ComplexNumber} input.
     * @return The result {@link ComplexNumber}.
     */
    public static ComplexNumber[] ifft(ComplexNumber[] x) {
        int N = x.length;
        ComplexNumber[] y = new ComplexNumber[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = fft(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(1.0 / N);
        }

        return y;
    }


    /**
     * This method id for calculating the two-dimensional complex number.
     *
     * @param x input array of complex number.
     */
    public static ComplexNumber[][] fft2d(ComplexNumber[][] x) {
        int N = x.length;

        for (int i = 0; i < N; i++) {
            ComplexNumber[] temp = new ComplexNumber[N];
            for (int j = 0; j < N; j++) {
                temp[j] = x[i][j];
            }

            ComplexNumber[] resultTemp = fft(temp);
            for (int j = 0; j < N; j++) {
                x[i][j] = resultTemp[j];
            }

        }


        for (int i = 0; i < N; i++) {
            ComplexNumber[] temp = new ComplexNumber[N];
            for (int j = 0; j < N; j++) {
                temp[j] = x[j][i];
            }

            ComplexNumber[] resultTemp = fft(temp);
            for (int j = 0; j < N; j++) {
                x[j][i] = resultTemp[j];
            }

        }

        ComplexNumber[][] y = new ComplexNumber[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                y[i][j] = x[i][j];
            }
        }

        return y;
    }

    /**
     * compute the inverse iFFT of x[], assuming its length is a power of 2
     * The method is for calculating the iFFT.
     *
     * @param x the {@link ComplexNumber} input.
     * @return The result {@link ComplexNumber}.
     */
    public static ComplexNumber[][] ifft2d(ComplexNumber[][] x) {
        int N = x.length;
        for (int i = 0; i < N; i++) {
            ComplexNumber[] temp = new ComplexNumber[N];
            for (int j = 0; j < N; j++) {
                temp[j] = x[i][j].conjugate();
            }

            ComplexNumber[] resultTemp = fft(temp);
            for (int j = 0; j < N; j++) {
                x[i][j] = resultTemp[j];
            }

        }


        for (int i = 0; i < N; i++) {
            ComplexNumber[] temp = new ComplexNumber[N];
            for (int j = 0; j < N; j++) {
                temp[j] = x[j][i];
            }

            ComplexNumber[] resultTemp = fft(temp);
            for (int j = 0; j < N; j++) {
                x[j][i] = resultTemp[j].conjugate();
            }
        }

        ComplexNumber[][] y = new ComplexNumber[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                y[i][j] = x[i][j].times(1.0 / (N * N));
            }
        }

        return y;
    }

    /**
     * compute the circular convolution of x and y.
     *
     * @param x the input complex number.
     * @param y the input complex number.
     */
    public static ComplexNumber[] cConvolve(ComplexNumber[] x, ComplexNumber[] y) {

        // should probably pad x and y with 0s so that they have same length
        // and are powers of 2
        if (x.length != y.length) {
            throw new RuntimeException("Dimensions don't agree");
        }

        int N = x.length;

        // compute FFT of each sequence
        ComplexNumber[] a = fft(x);
        ComplexNumber[] b = fft(y);

        // point-wise multiply
        ComplexNumber[] c = new ComplexNumber[N];
        for (int i = 0; i < N; i++) {
            c[i] = a[i].times(b[i]);
        }

        // compute inverse FFT
        return ifft(c);
    }

    /**
     * compute the linear convolution of x and y
     *
     * @param x the input complex number.
     * @param y the input complex number.
     */
    public static ComplexNumber[] convolve(ComplexNumber[] x, ComplexNumber[] y) {
        ComplexNumber ZERO = new ComplexNumber(0, 0);

        ComplexNumber[] a = new ComplexNumber[2 * x.length];
        for (int i = 0; i < x.length; i++) {
            a[i] = x[i];
        }
        for (int i = x.length; i < 2 * x.length; i++) {
            a[i] = ZERO;
        }

        ComplexNumber[] b = new ComplexNumber[2 * y.length];
        for (int i = 0; i < y.length; i++) {
            b[i] = y[i];
        }
        for (int i = y.length; i < 2 * y.length; i++) {
            b[i] = ZERO;
        }

        return cConvolve(a, b);
    }
}

