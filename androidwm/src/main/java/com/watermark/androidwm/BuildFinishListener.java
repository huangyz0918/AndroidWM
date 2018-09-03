package com.watermark.androidwm;


/**
 * This method is for listening if the operation of
 * creating invisible watermark is finished.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public interface BuildFinishListener<T> {

    void onSuccess(T object);

    void onFailure(String message);
}
