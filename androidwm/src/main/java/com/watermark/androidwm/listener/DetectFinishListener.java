package com.watermark.androidwm.listener;

import android.graphics.Bitmap;

/**
 * This interface is for listening if the task of
 * detecting invisible watermark is finished.
 * <p>
 * TODO: change the return value of onSuccess() to Generic.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public interface DetectFinishListener {

    void onImage(Bitmap watermark);

    void onText(String watermark);

    void onFailure(String message);
}
