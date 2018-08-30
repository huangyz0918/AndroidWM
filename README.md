# AndroidWM
 [![Download](https://api.bintray.com/packages/galaxyrockets/AndroidWM/androidwm/images/download.svg?version=v0.1.2)](https://bintray.com/galaxyrockets/AndroidWM/androidwm/v0.1.2/link) [![build Status](https://travis-ci.org/GalaxyRockets/AndroidWM.svg?branch=master)](https://travis-ci.org/GalaxyRockets/AndroidWM) [![principal](https://img.shields.io/badge/principal-huangyz0918-yellow.svg)](https://github.com/huangyz0918) [![contributions](https://img.shields.io/badge/contributions-welcome-green.svg)](https://github.com/GalaxyRockets/AndroidWM)

A lightweight android image watermark library that supports encrypted watermarks.

## Download Library (The beta)

To use this library by gradle:

```java
implementation 'com.watermark:androidwm:0.1.2'
```

For maven:

```java
<dependency>
  <groupId>com.watermark</groupId>
  <artifactId>androidwm</artifactId>
  <version>0.1.2</version>
  <type>pom</type>
</dependency>
```

For lvy:

```java
<dependency org='com.watermark' name='androidwm' rev='0.1.2'>
  <artifact name='androidwm' ext='pom' />
</dependency>
```

## Quick Start
After downloading the library and adding it into your project, you can get a watermark instance by `WatermarkBuilder`.
You can add a watermark image or watermark text into your background by adding `loadWatermarkImage(Bitmap)` or `loadWatermarkText(String)` like this:

```java
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkImage(watermarkBitmap)
            .loadWatermarkText(watermarkText)
            .getWatermark();
```

There are packaged `WatermarkImage` and `WatermarkText` classes for you to create a more flexible and more customized watermark, you can also instantiate them and put them in the ImageView quickly:

```java
    WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap)
            .setRotation(45)
            .setPositionX(10.5)
            .setPositionY(20.1);
            
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkImage(watermarkImage)
            .getWatermark()
            .setToImageView(imageView);
```
