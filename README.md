# AndroidWM
 [ ![Download](https://api.bintray.com/packages/galaxyrockets/AndroidWM/androidwm/images/download.svg) ](https://bintray.com/galaxyrockets/AndroidWM/androidwm/_latestVersion) [![build Status](https://travis-ci.org/GalaxyRockets/AndroidWM.svg?branch=master)](https://travis-ci.org/GalaxyRockets/AndroidWM) [![principal](https://img.shields.io/badge/principal-huangyz0918-yellow.svg)](https://github.com/huangyz0918) [![contributions](https://img.shields.io/badge/contributions-welcome-green.svg)](https://github.com/GalaxyRockets/AndroidWM)

A lightweight android image watermark library that supports encrypted watermarks. [中文版本](./README-CN.md)

## Download Library (The beta)

To use this library by gradle:

```java
implementation 'com.watermark:androidwm:0.1.4'
```

For maven:

```java
<dependency>
  <groupId>com.watermark</groupId>
  <artifactId>androidwm</artifactId>
  <version>0.1.4</version>
  <type>pom</type>
</dependency>
```

For lvy:

```java
<dependency org='com.watermark' name='androidwm' rev='0.1.4'>
  <artifact name='androidwm' ext='pom' />
</dependency>
```

## Quick Start
After downloading the library and adding it into your project, you can get a watermark instance by `WatermarkBuilder`.
You can add a watermark image or watermark text into your background by adding `loadWatermarkImage(Bitmap)` or `loadWatermarkText(String)` like this:

```java
    WatermarkBuilder
            .create(context, backgroundBitmap)
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
            .create(context, backgroundBitmap)
            .loadWatermarkImage(watermarkImage)
            .getWatermark()
            .setToImageView(imageView);
```

Here is a table of attributes in `WatermarkText` that you can custom:


|   __Method__  | __Description__ | __Default value__ |
| ------------- | ------------- | ------------- |
| setPosition  | `WatermarkPosition` for the  `WatermarkText` | _null_ |
| setPositionX  |  the x-axis coordinates of the `WatermarkText`  | _0_  |
| setPositionY  |  the y-axis coordinates of the `WatermarkText`  | _0_ |
| setRotation  |  the rotation of the `WatermarkText`, from 0 to 360 | _0_  |
| setTextColor   |  the text color of the `WatermarkText` | _`Color.BLACK`_  |
| setTextStyle    |  the text style of the `WatermarkText` | _`Paint.Style.FILL`_  |
| setBackgroundColor   |  the background color of the `WatermarkText` | _null_  |
| setTextAlpha   |  the text alpha of the `WatermarkText`, from 0 to 255 | _50_  |
| setTextSize  |  the text size of the `WatermarkText` | _20_   |
| setWatermarkVisibility  |  the visibility of the `WatermarkText` | _true_   |
| setWatermarkEncrypted  |  whether to encrypted the `WatermarkText` | _false_   |

The basic methods of `WatermarkImage` are the same as `WatermarkText`, but for a image watermark, there is no background and background color. If you want to load a watermark image or a watermark from a view, you can use those methods like this:

```java
WatermarkText watermarkText = new WatermarkText(editText); // for a text from EditText.
WatermarkText watermarkText = new WatermarkText(textView); // for a text from TextView.
WatermarkImage watermarkImage = new WatermarkImage(imageView); // for a image from imageView.
```
Also, the background image can be added via `ImageView`:

```java
    WatermarkBuilder
            .create(this, backgroundImageView)
            .getWatermark()

```

And if you want to add many watermark at the same time, you can use a `List<>` to hold your watermark texts. You can add the `List<>` into the background image by ` .loadWatermarkTexts(watermarkTexts)`, the same as watermark images:

```java
    WatermarkBuilder
            .create(this, backgroundImageView)
            .loadWatermarkTexts(watermarkTexts)
            .loadWatermarkImages(watermarkImages)
            .getWatermark()
```

If you want to get the result bitmap, we also have a `.getOutputImage()` method for you:

```java
    WatermarkBuilder
            .create(this, backgroundImageView)
            .getWatermark()
            .getOutputImage();

```

If you didn't load a watermark ,the default value is as the same as background, nothing will be changed.

