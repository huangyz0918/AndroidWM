package com.watermark.androidwm;

import com.watermark.androidwm.utils.FastDctFft;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * The unit tests for the FD invisible watermark detection methods.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class DCTTest {

    @Test
    public void testFftDct() {
        double[] test = {255.0, 254.0, 243.0, 253.0, 255.0, 255.0, 246.0, 255.0, 255.0, 255.0};
        double[] temp = {255.0, 254.0, 243.0, 253.0, 255.0, 255.0, 246.0, 255.0, 255.0, 255.0};
        FastDctFft.transform(test);
        FastDctFft.inverseTransform(test);

        double scale = (double) test.length / 2;
        for (int i = 0; i < test.length; i++) {
            test[i] = (int) Math.round(test[i] / scale);
        }

        assertEquals(temp[0] - test[0], 0, 0);
        assertEquals(temp[1] - test[1], 0, 0);
        assertEquals(temp[2] - test[2], 0, 0);
        assertEquals(temp[3] - test[3], 0, 0);
    }

}
