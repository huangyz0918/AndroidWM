# AndroidWM 
 [ ![Download](https://api.bintray.com/packages/galaxyrockets/AndroidWM/androidwm/images/download.svg) ](https://bintray.com/galaxyrockets/AndroidWM/androidwm/_latestVersion) [![build Status](https://travis-ci.org/GalaxyRockets/AndroidWM.svg?branch=master)](https://travis-ci.org/GalaxyRockets/AndroidWM) [![principal](https://img.shields.io/badge/principal-huangyz0918-yellow.svg)](https://github.com/huangyz0918) [![contributions](https://img.shields.io/badge/contributions-welcome-green.svg)](https://github.com/GalaxyRockets/AndroidWM)

一个轻量级的 Android 图片水印框架，支持隐形水印和加密水印。 [English version](./README.md)

## 下载与安装 (测试版本)

使用 gradle 下载框架:

```java
implementation 'com.watermark:androidwm:0.1.3'
```

使用 maven 下载框架:

```java
<dependency>
  <groupId>com.watermark</groupId>
  <artifactId>androidwm</artifactId>
  <version>0.1.3</version>
  <type>pom</type>
</dependency>
```

使用 lvy 下载框架:

```java
<dependency org='com.watermark' name='androidwm' rev='0.1.3'>
  <artifact name='androidwm' ext='pom' />
</dependency>
```

## 快速入门
在下载并且在您的项目内配置好 androidwm 之后, 您可以使用 `WatermarkBuilder` 快速地获取到一个水印的实例 (`Watermark`).
同时，您可以使用 `loadWatermarkImage(Bitmap)` 或者 `loadWatermarkText(String)` 方法为背景图片添加一个图片水印或者是文字水印:

```java
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkImage(watermarkBitmap)
            .loadWatermarkText(watermarkText)
            .getWatermark();
```

androidwm 里面有封装好的 `WatermarkImage` 和 `WatermarkText` 类，它们可以帮助您灵活定制一个水印。在构建完成水印之后，您可以选择把新建的带水印图片放置到 ImageView 中:

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
