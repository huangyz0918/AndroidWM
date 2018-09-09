package com.watermark.androidwm.sample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Leaks to detect the memory leaks.
 */
public class Leaks extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
