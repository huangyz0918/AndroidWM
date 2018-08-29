# AndroidWM
[![platform](https://img.shields.io/badge/platform-android-blue.svg)](https://github.com/GalaxyRockets/AndroidWM) [![principal](https://img.shields.io/badge/principal-huangyz0918-yellow.svg)](https://github.com/huangyz0918) [![build Status](https://travis-ci.org/GalaxyRockets/AndroidWM.svg?branch=master)](https://travis-ci.org/GalaxyRockets/AndroidWM) [![contributions](https://img.shields.io/badge/contributions-welcome-green.svg)](https://github.com/GalaxyRockets/AndroidWM)

A lightweight android image watermark library that supports encrypted watermarks.

## Download Library

## Quick Start
After you have downloaded the library and added it into your project, you can get a watermark instance by `WatermarkBuilder`.
You can add a watermark image or watermark text into your background by adding `loadWatermarkImage(Bitmap)` or `loadWatermarkText(String)`:

```java
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkImage(watermarkBitmap)
            .loadWatermarkText(watermarkText)
            .getWatermark();
```

There are packaged `WatermarkImage` and `WatermarkText` classes for you, you can create a more flexible and more customized watermark by instantiating them and put them in the ImageView quickly:

```java
    WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap)
            .setPositionRotation(45)
            .setPositionX(10.5)
            .setPositionY(20.1)
            .setPosition(new WatermarkPosition(10.5, 20.1, 45)); // you can also create a WatermarkPosition instance for position setting.
            
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkImage(watermarkImage)
            .getWatermark()
            .setToImageView(imageView);
```
