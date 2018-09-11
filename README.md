# AndroidWM
[ ![Download](https://api.bintray.com/packages/huangyz0918/androidwm/androidwm/images/download.svg) ](https://bintray.com/huangyz0918/androidwm/androidwm/_latestVersion) [![Build Status](https://travis-ci.org/huangyz0918/AndroidWM.svg?branch=master)](https://travis-ci.org/huangyz0918/AndroidWM) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/7f8e55520309410a95f71b54cfe8c381)](https://app.codacy.com/app/huangyz0918/AndroidWM?utm_source=github.com&utm_medium=referral&utm_content=huangyz0918/AndroidWM&utm_campaign=Badge_Grade_Dashboard) ![progress](https://img.shields.io/badge/progress-developing-yellow.svg)

A lightweight android image watermark library that supports encrypted watermarks. [中文版本](./README-CN.md)

<!--- ![](https://i.loli.net/2018/09/01/5b8aa948a2020.png) -->

![](https://i.loli.net/2018/09/11/5b97dddb4e407.png)

# Download Library 

### Gradle:

```gradle
implementation 'com.huangyz0918:androidwm:0.1.9'
```
### Maven:

```xml
<dependency>
  <groupId>com.huangyz0918</groupId>
  <artifactId>androidwm</artifactId>
  <version>0.1.9</version>
  <type>pom</type>
</dependency>
```

### Lvy:

```xml
<dependency org='com.huangyz0918' name='androidwm' rev='0.1.9'>
  <artifact name='androidwm' ext='pom' ></artifact>
</dependency>
```

# Quick Start
### Build a Watermark
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

![](https://i.loli.net/2018/09/01/5b8aa948a2020.png)

### Select the Drawing Mode
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

### Get the Output
You can create both text watermark and image watermark, and load them into your `WatermarkBuilder`. If you want to get the result bitmap, we also have a `.getOutputImage()` method for you after getting the watermark:

```java
    Bitmap bitmap = WatermarkBuilder
            .create(this, backgroundBitmap)
            .getWatermark()
            .getOutputImage();
```

### Build Multiple Watermarks
And if you want to add many watermarks at the same time, you can use a `List<>` to hold your watermarks. You can add the `List<>` into the background image by ` .loadWatermarkTexts(watermarkTexts)`, the same as watermark images:

```java
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkTexts(watermarkTexts)
            .loadWatermarkImages(watermarkImages)
            .getWatermark();
```

### Ways of Loading Resources
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

### Invisible Watermarks (beta)

In this library, we also support the invisible watermark and the detection of them. We can use two ways to build a invisible watermark: the LSB (spatial domain) and the wavelet transform (Frequency domain). All you need to do is to use a boolean (isLSB) to distinguish them. __(PS. the watermark in frequency domain is under developing)__

You can create a new invisible watermark by the `WatermarkBuilder`'s `.setInvisibleWMListener`:

```java
     WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkImage(watermarkBitmap)
            .setInvisibleWMListener(true, 512, new BuildFinishListener<Bitmap>() {
                @Override
                public void onSuccess(Bitmap object) {
                    if (object != null) {
                       // do something...
                    }
                }

                @Override
                public void onFailure(String message) {
                   // do something...
                }
            });
```
The first paramter of `setInvisibleWMListener` is `isLSB`, if false, the invisible algorithm will change to the frequency domain. The second parameter is an int which is the max image size, if you are using a really big image, the progress may be slow, so you can use this paramter to resize the input image, but remember: the size must be enough for the watermark to put in, or it will throw an exception.

To detect the invisible watermark, you can use `WatermarkDetector`, the first paramter is the kind of watermark, if you want to detect the image watermark, the paramter is false, if it's a text, then true. The input bitmap is a image with invisible watermarks.

```java
     WatermarkDetector
            .create(inputBitmap, true)
            .detect(false, new DetectFinishListener() {
                @Override
                public void onImage(Bitmap watermark) {
                    if (watermark != null) {
                         // do something...
                    }
                }

                @Override
                public void onText(String watermark) {
                    if (watermark != null) {
                        // do something...
                    }
                }

                @Override
                public void onFailure(String message) {
                       // do something...
                }
            });
```

Here are the Demos for Least Significant Bits (LSB) invisible watermark:

|  ![](https://i.loli.net/2018/09/06/5b90f8d80402b.gif)   | ![](https://i.loli.net/2018/09/06/5b90f8d7936f9.gif) | 
| :-------------: | :-------------: | 
|    Invisible Text (LSB)   |   Invisible Image (LSB)    | 


Enjoy yourself! :kissing_heart:

# Detailed Usages

## Position

We use the class `WatermarkPosition` to control the position of a watermark. 

```java 
   WatermarkPosition watermarkPosition = new WatermarkPosition(double position_x, double position_y, double rotation);
   WatermarkPosition watermarkPosition = new WatermarkPosition(double position_x, double position_y);
```

We can set the abscissa and ordinate of the watermark in the constructor, or you can optionally add the rotation angle. The coordinate system starts from the upper left corner of the background image, and the upper left corner is the origin.

The `WatermarkPosition` also supports change the position dynamically, androidwm offers several methods for you to modify the positions.

```java
     watermarkPosition
              .setPositionX(x)
              .setPositionY(y)
              .setRotation(rotation);
```
In full coverage mode (Tile mode), the positional parameters of the watermark image will be invalid.

|  ![](https://i.loli.net/2018/09/05/5b8f4a970a83e.png)   | ![](https://i.loli.net/2018/09/05/5b8f4a9706788.png) | 
| :-------------: | :-------------: | 
|   x = y = 0, rotation = 15 | x = y = 0.5, rotation = -15  | 

Both the abscissa and the ordinate are a number from 0 to 1, representing a fixed proportion of the background image.


## Color of Text Watermark 

You can set the text color and background color in `WatermarkText`:

```java
    WatermarkText watermarkText = new WatermarkText(editText)
            .setPositionX(0.5)
            .setPositionY(0.5)
            .setTextSize(30)
            .setTextAlpha(200)
            .setTextColor(Color.GREEN)
            .setBackgroundColor(Color.WHITE); // if not, the background color will be transparent
```

|  ![](https://i.loli.net/2018/09/05/5b8f4ce0cf6ce.png)   | ![](https://i.loli.net/2018/09/05/5b8f4ce11a28c.png) | 
| :-------------: | :-------------: | 
|   color = green, background color = white | color = green, background color = default  | 

## Text Shadow and Font
You can set the text font by loading a local resource, besides, you can also set the shadow by `setTextShadow`.

```java
    WatermarkText watermarkText = new WatermarkText(editText)
            .setPositionX(0.5)
            .setPositionY(0.5)
            .setTextSize(40)
            .setTextAlpha(200)
            .setTextColor(Color.GREEN)
            .setTextFont(R.font.champagne)
            .setTextShadow(0.1f, 5, 5, Color.BLUE);
```

|  ![](https://i.loli.net/2018/09/05/5b8f5c48e2631.png)   | ![](https://i.loli.net/2018/09/05/5b8f5c48e081c.png) | 
| :-------------: | :-------------: | 
|   font = champagne | shadow = (0.1f, 5, 5, BLUE)  | 

the four parameters of text shadow is: `(blur radius, x offset, y offset, color)`.

## Text Size and Image Size

The text font size unit and the image size unit are different:
- Text size is as the same unit as the android layout. (will auto adjust with the screen density and background pixels)
- The image size is from 0 to 1, which is the width of the background image percentage.

|  ![](https://i.loli.net/2018/09/05/5b8f5eb1a7fb0.png)   | ![](https://i.loli.net/2018/09/05/5b8f5eb24d0fd.png) | 
| :-------------: | :-------------: | 
|   image size = 0.3 | text size = 40  | 

## Table of Methods

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
| setTextFont (`WatermarkText`) | typeface of the `WatermarkText` | _default_  |
| setTextShadow  (`WatermarkText`)|  shadow of the `WatermarkText` | _(0, 0, 0)_  |
| setImageDrawable  (`WatermarkImage`)|  image drawable of the `WatermarkImage` | _null_

The basic methods of `WatermarkImage` are the same as `WatermarkText`.



