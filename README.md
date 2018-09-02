# AndroidWM
[ ![Download](https://api.bintray.com/packages/huangyz0918/androidwm/androidwm/images/download.svg) ](https://bintray.com/huangyz0918/androidwm/androidwm/_latestVersion) [![Build Status](https://travis-ci.org/huangyz0918/AndroidWM.svg?branch=master)](https://travis-ci.org/huangyz0918/AndroidWM) [![contributions](https://img.shields.io/badge/contributions-welcome-green.svg)](https://github.com/GalaxyRockets/AndroidWM)

A lightweight android image watermark library that supports encrypted watermarks. [中文版本](./README-CN.md)

![](https://i.loli.net/2018/09/01/5b8aa948a2020.png)

## Download Library (The beta)

To use this library by gradle:

```groovy
implementation 'com.huangyz0918:androidwm:v0.1.5'
```

For maven:

```maven
<dependency>
  <groupId>com.huangyz0918</groupId>
  <artifactId>androidwm</artifactId>
  <version>v0.1.5</version>
  <type>pom</type>
</dependency>
```

For lvy:

```lvy
<dependency org='com.huangyz0918' name='androidwm' rev='v0.1.5'>
  <artifact name='androidwm' ext='pom' ></artifact>
</dependency>
```

## Quick Start
After downloading the library and adding it into your project, You can create a `WatermarkImage` or `WatermarkText` and do some pre-settings with their instance.

```java
    WatermarkText watermarkText = new WatermarkText(editText)
            .setPositionX(0.5)
            .setPositionY(0.5)
            .setTextColor(Color.WHITE)
            .setTextFont(R.font.champagne)
            .setTextShadow(0.1f, 5, 5, Color.BLUE)
            .setTextAlpha(150)
            .setRotation(30)
            .setTextSize(20);
```

There are many attributes that can help you to make a customization with a text watermark or an image watermark. You can get more information from the specific documentation section that follows.

After the preparation is complete, you need a `WatermarkBuilder` to create a watermark image. You can get an instance from the `create` method of `WatermarkBuilder`, and, you need to put a `Bitmap` or an int `Drawable` as the background image first.

```java
    WatermarkBuilder
            .create(context, backgroundBitmap)
            .loadWatermarkText(watermarkText)
            .loadWatermarkImage(watermarkImage)
            .getWatermark()
            .setToImageView(imageView);
```

You can create both text watermark and image watermark, and load them into your `WatermarkBuilder`. If you want to get the result bitmap, we also have a `.getOutputImage()` method for you after getting the watermark:

```java
    Bitmap bitmap = WatermarkBuilder
            .create(this, backgroundBitmap)
            .getWatermark()
            .getOutputImage();
```

And if you want to add many watermarks at the same time, you can use a `List<>` to hold your watermarks. You can add the `List<>` into the background image by ` .loadWatermarkTexts(watermarkTexts)`, the same as watermark images:

```java
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkTexts(watermarkTexts)
            .loadWatermarkImages(watermarkImages)
            .getWatermark();
```

Enjoy yourself!

## Detailed Usages

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
| setTextFont  | font of the `WatermarkText` | default  |
| setTextShadow  |  shadow of the `WatermarkText` | (0, 0, 0)  |

The basic methods of `WatermarkImage` are the same as `WatermarkText`, but for a image watermark, there is no background and background color. If you want to load a watermark image or a watermark from a view or resources, you can use those methods like this:

```java
WatermarkText watermarkText = new WatermarkText(editText); // for a text from EditText.
WatermarkText watermarkText = new WatermarkText(textView); // for a text from TextView.
WatermarkImage watermarkImage = new WatermarkImage(imageView); // for an image from ImageView.
WatermarkImage watermarkImage = new WatermarkImage(this, R.drawable.image); // for an image from Resource.
```

The  `WatermarkBuilder` can be create from resources too. Also, the background image can be added via `ImageView`:

```java
    WatermarkBuilder
            .create(this, backgroundImageView)
            .getWatermark()
```

If you didn't load a watermark ,the default value is as the same as background, nothing will be changed.

