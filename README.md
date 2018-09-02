# AndroidWM
[ ![Download](https://api.bintray.com/packages/huangyz0918/androidwm/androidwm/images/download.svg) ](https://bintray.com/huangyz0918/androidwm/androidwm/_latestVersion) [![Build Status](https://travis-ci.org/huangyz0918/AndroidWM.svg?branch=master)](https://travis-ci.org/huangyz0918/AndroidWM) [![contributions](https://img.shields.io/badge/contributions-welcome-green.svg)](https://github.com/GalaxyRockets/AndroidWM)

A lightweight android image watermark library that supports encrypted watermarks. [中文版本](./README-CN.md)

![](https://i.loli.net/2018/09/01/5b8aa948a2020.png)

## Download Library (The beta)

To use this library by gradle:

```gradle
implementation 'com.huangyz0918:androidwm:v0.1.5'
```

For maven:

```xml
<dependency>
  <groupId>com.huangyz0918</groupId>
  <artifactId>androidwm</artifactId>
  <version>v0.1.5</version>
  <type>pom</type>
</dependency>
```

For lvy:

```xml
<dependency org='com.huangyz0918' name='androidwm' rev='v0.1.5'>
  <artifact name='androidwm' ext='pom' ></artifact>
</dependency>
```

## Quick Start
After downloading the library and adding it into your project, You can create a `WatermarkImage` or `WatermarkText` and do some pre-settings with their instance.

```java
    WatermarkText watermarkText = new WatermarkText(inputText)
            .setPositionX(0.5)
            .setPositionY(0.5)
            .setTextColor(Color.WHITE)
            .setTextFont(R.font.champagne)
            .setTextShadow(0.1f, 5, 5, Color.BLUE)
            .setTextAlpha(150)
            .setRotation(30)
            .setTextSize(20);
```

There are many attributes that can help you to make a customization with a text watermark or an image watermark. You can get more information from the documentation section that follows.

After the preparation is complete, you need a `WatermarkBuilder` to create a watermark image. You can get an instance from the `create` method of `WatermarkBuilder`, and, you need to put a `Bitmap` or an int `Drawable` as the background image first.

```java
    WatermarkBuilder
            .create(context, backgroundBitmap)
            .loadWatermarkText(watermarkText) // use .loadWatermarkImage(watermarkImage) to load an image.
            .getWatermark()
            .setToImageView(imageView);
```

You can select normal mode (default) or tile mode in `WatermarkBuilder.setTileMode()`:

```java
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkText(watermarkText)
            .setTileMode(true) // select different drawing mode.
            .getWatermark()
            .setToImageView(backgroundView);
```

Boom! the watermark has been drawed now:

![](https://i.loli.net/2018/09/02/5b8b6617a50c5.png)


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

If you want to load a watermark image or a watermark text from a view or resources, you can use those methods:

```java
WatermarkText watermarkText = new WatermarkText(editText); // for a text from EditText.
WatermarkText watermarkText = new WatermarkText(textView); // for a text from TextView.
WatermarkImage watermarkImage = new WatermarkImage(imageView); // for an image from ImageView.
WatermarkImage watermarkImage = new WatermarkImage(this, R.drawable.image); // for an image from Resource.
```

The background loaded in `WatermarkBuilder` can be created from resources or `ImageView` too: 

```java
    WatermarkBuilder
            .create(this, backgroundImageView) // .create(this, R.drawable.background)
            .getWatermark()
```

If you didn't load a watermark ,the default value is as the same as background, nothing will be changed.


Enjoy yourself! :kissing_heart:

## Detailed Usages

Here is a table of attributes in `WatermarkText` and `WatermarkImage` that you can custom with:

|   __Method__  | __Description__ | __Default value__ |
| ------------- | ------------- | ------------- |
| setPosition  | `WatermarkPosition` for the watermark | _null_ |
| setPositionX  |  the x-axis coordinates of the watermark  | _0_  |
| setPositionY  |  the y-axis coordinates of the watermark  | _0_ |
| setRotation  |  the rotation of the watermark| _0_  |
| setTextColor  (`WatermarkText`)  |  the text color of the `WatermarkText` | _`Color.BLACK`_  |
| setTextStyle  (`WatermarkText`)  |  the text style of the `WatermarkText` | _`Paint.Style.FILL`_  |
| setBackgroundColor  (`WatermarkText`) |  the background color of the `WatermarkText` | _null_  |
| setTextAlpha  (`WatermarkText`) |  the text alpha of the `WatermarkText`, from 0 to 255 | _50_  |
| setImageAlpha  (`WatermarkImage`) |  the text alpha of the `WatermarkImage`, from 0 to 255 | _50_  |
| setTextSize (`WatermarkText`) |  the text size of the `WatermarkText`, consistent with the size unit used by the layout | _20_   |
| setSize  (`WatermarkImage`)|  the image size of the `WatermarkImage`, from 0 to 1 (the proportion of background size) | _0.2_   |
| setWatermarkVisibility (`WatermarkText`) |  the visibility of the `WatermarkText` | _true_   |
| setWatermarkEncrypted  (`WatermarkText`)|  whether to encrypted the `WatermarkText` | _false_   |
| setTextFont (`WatermarkText`) | typeface of the `WatermarkText` | _default_  |
| setTextShadow  (`WatermarkText`)|  shadow of the `WatermarkText` | _(0, 0, 0)_  |
| setImageDrawable  (`WatermarkImage`)|  image drawable of the `WatermarkImage` | _null_

The basic methods of `WatermarkImage` are the same as `WatermarkText`.
