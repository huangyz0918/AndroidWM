# AndroidWM 
[ ![Download](https://api.bintray.com/packages/huangyz0918/androidwm/androidwm/images/download.svg) ](https://bintray.com/huangyz0918/androidwm/androidwm/_latestVersion) [![Build Status](https://travis-ci.org/huangyz0918/AndroidWM.svg?branch=master)](https://travis-ci.org/huangyz0918/AndroidWM) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/7f8e55520309410a95f71b54cfe8c381)](https://app.codacy.com/app/huangyz0918/AndroidWM?utm_source=github.com&utm_medium=referral&utm_content=huangyz0918/AndroidWM&utm_campaign=Badge_Grade_Dashboard)[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fhuangyz0918%2FAndroidWM.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fhuangyz0918%2FAndroidWM?ref=badge_shield)  [![wiki](https://img.shields.io/badge/wiki-english-blue.svg)](https://github.com/huangyz0918/AndroidWM/blob/master/wikis/WIKI.md) ![downloads](https://img.shields.io/bintray/dt/huangyz0918/androidwm/androidwm-light)

A lightweight android image watermark library that supports encrypted watermarks. [中文版本](./README-CN.md)

<!--- ![](https://i.loli.net/2018/09/01/5b8aa948a2020.png) -->
<!-- ![](https://github.com/huangyz0918/AndroidWM/blob/master/images/banner.svg) -->
![](https://github.com/huangyz0918/AndroidWM/blob/master/images/logo.svg)
<!--![](https://i.loli.net/2018/09/11/5b97dddb4e407.png) -->

# Download Library 

### Gradle:
For __androidWM__ supports the invisible digital watermarks (package size: 1Mb):

```gradle
implementation 'com.huangyz0918:androidwm:0.2.3'
```

For __androidWM-light__ only supports the visible watermarks (package size: 28Kb):

```gradle
implementation 'com.huangyz0918:androidwm-light:0.1.2'
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

In this library, we also support the invisible watermark and the detection of them. We can use two ways to build an invisible watermark: the LSB (spatial domain) and the wavelet transform (Frequency domain). All you need to do is to use a boolean (isLSB) to distinguish them. __(PS. the watermark in frequency domain is under developing)__

You can create a new invisible watermark by the `WatermarkBuilder`'s `.setInvisibleWMListener`:

```java
     WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkImage(watermarkBitmap)
            .setInvisibleWMListener(true, new BuildFinishListener<Bitmap>() {
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
The first paramter of `setInvisibleWMListener` is `isLSB`, if false, the invisible algorithm will change to the frequency domain. 

To detect the invisible watermark, you can use `WatermarkDetector`, you need to put a boolean parameter in `.create` method, since we have two kinds of invisible watermarks, if `isLSB` is true, the detector can detect LSB watermarks, if not, the detector can detect the watermarks in the frequency domain.

```java
     WatermarkDetector
            .create(inputBitmap, true)
            .detect(false, new DetectFinishListener() {
                @Override
                public void onSuccess(DetectionReturnValue returnValue) {
                       Bitmap watermarkImage = returnValue.getWatermarkBitmap();
                       String watermarkString = returnValue.getWatermarkString();
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

For more information, please checkout [Wiki](https://github.com/huangyz0918/AndroidWM/blob/master/wikis/WIKI.md), enjoy yourself! :kissing_heart:


# License
```
   Copyright 2018 Yizheng Huang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

```
