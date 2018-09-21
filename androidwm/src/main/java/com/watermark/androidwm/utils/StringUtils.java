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
 * Util class for operations with {@link String}.
 *
 * @author huangyz0918
 */
public class StringUtils {

    static {
        System.loadLibrary("Watermark");
    }

    /**
     * Converting a {@link String} text into a binary text.
     * <p>
     * This is the native version.
     */
    public static native String stringToBinary(String inputText);

    /**
     * String to integer array.
     * <p>
     * This is the native version.
     */
    public static native int[] stringToIntArray(String inputString);

    /**
     * Converting a binary string to a ASCII string.
     */
    public static native String binaryToString(String inputText);

    /**
     * Replace the wrong rgb number in a form of binary,
     * the only case is 0 - 1 = 9, so, we need to replace
     * all nines to zero.
     */
    public static native void replaceNines(int[] inputArray);

    /**
     * Int array to string.
     */
    public static native String intArrayToString(int[] inputArray);

    /**
     * native method for calculating the Convolution 1D.
     */
    public static native double[] calConv1D(double[] inputArray1, double[] inputArray2);

    /**
     * get the single digit number and set it to the target one.
     */
    public static int replaceSingleDigit(int target, int singleDigit) {
        return (target / 10) * 10 + singleDigit;
    }

}
