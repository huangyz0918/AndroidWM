package com.watermark.androidwm;


import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * The unit tests for the LSB invisible watermark detection methods.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class LSBDetectionTest {

    @Test
    public void arrayToStringTest() {
        int[] inputArray = {
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0
        };
        int minSize = 8;

        int multiple = inputArray.length / minSize;
        int mod = inputArray.length % minSize;

        StringBuilder[] builders = new StringBuilder[multiple + 1];
        builders[builders.length - 1] = new StringBuilder();

        int[] arrayWithout = Arrays.copyOfRange(inputArray, multiple * minSize, inputArray.length);
        int[] expectArray = {7, 8, 9, 0};

        assertEquals(Arrays.toString(expectArray), Arrays.toString(arrayWithout));
        assertEquals(mod, arrayWithout.length);
    }

}
