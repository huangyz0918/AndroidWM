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
package com.watermark.androidwm.task;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;

import com.watermark.androidwm.bean.DetectionParams;
import com.watermark.androidwm.bean.DetectionReturnValue;
import com.watermark.androidwm.listener.DetectFinishListener;
import com.watermark.androidwm.utils.BitmapUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.watermark.androidwm.utils.Constant.LSB_PREFIX_FLAG;
import static com.watermark.androidwm.utils.Constant.LSB_SUFFIX_FLAG;

/**
 * This is a task for watermark image detection.
 * In LSB mode, all the task will return a bitmap;
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class LSBDetectionTask extends AsyncTask<DetectionParams, Void, DetectionReturnValue> {

    private DetectFinishListener listener;

    public LSBDetectionTask(DetectFinishListener listener) {
        this.listener = listener;
    }

    @Override
    protected DetectionReturnValue doInBackground(DetectionParams... detectionParams) {
        boolean isText = detectionParams[0].isTextWatermark();
        Bitmap markedBitmap = detectionParams[0].getWatermarkBitmap();
        DetectionReturnValue resultValue = new DetectionReturnValue();

        if (markedBitmap == null) {
            listener.onFailure("Cannot detect the watermark! markedBitmap is null object!");
            return null;
        }

        int[] Pixels = new int[markedBitmap.getWidth() * markedBitmap.getHeight()];
        markedBitmap.getPixels(Pixels, 0, markedBitmap.getWidth(), 0, 0,
                markedBitmap.getWidth(), markedBitmap.getHeight());

        int[] colorArray = new int[4 * Pixels.length];

        for (int i = 0; i < Pixels.length; i++) {
            colorArray[4 * i] = Color.alpha(Pixels[i]);
            colorArray[4 * i + 1] = Color.red(Pixels[i]);
            colorArray[4 * i + 2] = Color.green(Pixels[i]);
            colorArray[4 * i + 3] = Color.blue(Pixels[i]);
        }

        int[] outputBinary = new int[colorArray.length];

        for (int i = 0; i < outputBinary.length; i++) {
            outputBinary[i] = colorArray[i] % 10;
        }

        String binaryString = Arrays.toString(outputBinary).replaceAll("\\[|\\]|,|\\s", "");
        binaryString = getSubUtil(binaryString);

        if (binaryString == null) {
            listener.onFailure("Cannot detect the watermark! binaryString gets a null object!");
            return null;
        }

        // To make sure there has no redundancy.
        binaryString = binaryString.replaceAll("9", "0");
//        long startTime = System.currentTimeMillis();
        String resultString = binaryToString(binaryString);
//        long endTime = System.currentTimeMillis();
//        Log.d("--->", "time: " + (endTime - startTime));
        if (isText) {
            resultValue.setWatermarkString(resultString);
        } else {
            resultValue.setWatermarkBitmap(BitmapUtils.StringToBitmap(resultString));
        }

        return resultValue;
    }

    @Override
    protected void onPostExecute(DetectionReturnValue detectionReturnValue) {
        if (detectionReturnValue != null) {
            if (detectionReturnValue.getWatermarkBitmap() != null) {
                listener.onImage(detectionReturnValue.getWatermarkBitmap());
            }

            if (detectionReturnValue.getWatermarkString() != null) {
                listener.onText(detectionReturnValue.getWatermarkString());
            }
        } else {
            listener.onFailure("Failed to detect the watermark!");
        }
        super.onPostExecute(detectionReturnValue);
    }

    /**
     * Converting a binary string to a ASCII string.
     * <p>
     * TODO: this method needs performance optimization.
     */
    private String binaryToString(String inputText) {
        StringBuilder sb = new StringBuilder();
        char[] chars = inputText.replaceAll("\\s", "").toCharArray();
        int[] mapping = {1, 2, 4, 8, 16, 32, 64, 128};

        for (int j = 0; j < chars.length; j += 8) {
            int idx = 0;
            int sum = 0;
            for (int i = 7; i >= 0; i--) {
                if (chars[i + j] == '1') {
                    sum += mapping[idx];
                }
                idx++;
            }
            sb.append(Character.toChars(sum));
        }
        return sb.toString();
    }

    /**
     * Grab strings between two specific strings.
     */
    private String getSubUtil(String inputString) {
        String regexString = Pattern.quote(LSB_PREFIX_FLAG) + "(.*?)" + Pattern.quote(LSB_SUFFIX_FLAG);
        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            return matcher.group(1);
        }
        return null;

    }
}
