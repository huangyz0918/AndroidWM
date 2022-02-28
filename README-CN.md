# AndroidWM 
[ ![Download](https://api.bintray.com/packages/huangyz0918/androidwm/androidwm/images/download.svg) ](https://bintray.com/huangyz0918/androidwm/androidwm/_latestVersion) [![Build Status](https://travis-ci.org/huangyz0918/AndroidWM.svg?branch=master)](https://travis-ci.org/huangyz0918/AndroidWM) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/7f8e55520309410a95f71b54cfe8c381)](https://app.codacy.com/app/huangyz0918/AndroidWM?utm_source=github.com&utm_medium=referral&utm_content=huangyz0918/AndroidWM&utm_campaign=Badge_Grade_Dashboard)
 ![progress](https://img.shields.io/badge/progress-developing-yellow.svg) [![wiki](https://img.shields.io/badge/wiki-chinese-blue.svg)](https://github.com/huangyz0918/AndroidWM/blob/master/wikis/WIKI-CN.md) 

一个轻量级的 Android 图片水印框架，支持隐形数字水印。 [English version](./README.md)

<!--- ![](https://i.loli.net/2018/09/01/5b8aa948a2020.png) -->
<!-- ![](https://github.com/huangyz0918/AndroidWM/blob/master/images/banner.svg) -->
![](https://github.com/huangyz0918/AndroidWM/blob/master/images/logo.svg)
<!--![](https://i.loli.net/2018/09/11/5b97dddb4e407.png) -->

# 下载与安装 

### Gradle
下载完整版本水印框架，支持隐形数字水印 (包大小: 1Mb):

```gradle
implementation 'com.huangyz0918:androidwm:0.2.3'
```

下载轻量级版本，只支持可见图像水印 (包大小: 28Kb):

```gradle
implementation 'com.huangyz0918:androidwm-light:0.1.2'
```

# 快速入门
### 新建一个水印图片
在下载并且配置好 androidwm 之后，你可以创建一个 `WatermarkImage` 或者是 `WatermarkText` 的实例，并且使用内置的诸多`Set`方法为创建一个水印做好准备。

```java
    WatermarkText watermarkText = new WatermarkText(editText)
            .setPositionX(0.5)
            .setPositionY(0.5)
            .setOrigin(new WatermarkPosition(0.5, 0.5))
            .setTextColor(Color.WHITE)
            .setTextFont(R.font.champagne)
            .setTextShadow(0.1f, 5, 5, Color.BLUE)
            .setTextAlpha(150)
            .setRotation(30)
            .setTextSize(20);
```

对于具体定制一个文字水印或者是图片水印， 我们在接下来的文档中会仔细介绍。

当你的水印（文字或图片水印）已经准备就绪的时候，你需要一个 `WatermarkBuilder`来把水印画到你希望的背景图片上。 你可以通过 `create` 方法获取一个 `WatermarkBuilder` 的实例，注意，在创建这个实例的时候你需要先传入一个 `Bitmap` 或者是一个 `Drawable` 的资源 id 来获取背景图。

```java
    WatermarkBuilder
            .create(context, backgroundBitmap)
            .loadWatermarkText(watermarkText) // use .loadWatermarkImage(watermarkImage) to load an image.
            .getWatermark()
            .setToImageView(imageView);
```

![](https://i.loli.net/2018/09/01/5b8aa948a8935.png)

### 选择绘制模式
你可以在  `WatermarkBuilder.setTileMode()` 中选择是否使用铺满整图模式，默认情况下我们只会添加一个水印。

```java
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkText(watermarkText)
            .setTileMode(true) // select different drawing mode.
            .getWatermark()
            .setToImageView(backgroundView);
```

咚! 带水印的图片已经绘制好啦：

![](https://i.loli.net/2018/09/02/5b8b6617aa33a.png)

### 获取输出图片
你可以在 `WatermarkBuilder` 中同时加载文字水印和图片水印。 如果你想在绘制完成之后获得带水印的结果图片，可以使用 `Watermark` 的 `.getOutputImage()` 方法：

```java
    Bitmap bitmap = WatermarkBuilder
            .create(this, backgroundBitmap)
            .getWatermark()
            .getOutputImage();
```

### 创建多个水印
你还可以一次性添加多个水印图片，通过创建一个`WatermarkText` 的列表 `List<>` 并且在水印构建器的方法 ` .loadWatermarkTexts(watermarkTexts)`中把列表传入进去（图片类型水印同理）：

```java
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkTexts(watermarkTexts)
            .loadWatermarkImages(watermarkImages)
            .getWatermark();
```

### 加载资源
你还可以从系统的控件和资源中装载图片或者是文字资源，从而创建一个水印对象：

```java
WatermarkText watermarkText = new WatermarkText(editText); // for a text from EditText.
WatermarkText watermarkText = new WatermarkText(textView); // for a text from TextView.
WatermarkImage watermarkImage = new WatermarkImage(imageView); // for an image from ImageView.
WatermarkImage watermarkImage = new WatermarkImage(this, R.drawable.image); // for an image from Resource.
```

`WatermarkBuilder`里面的背景图片同样可以从系统资源或者是 `ImageView` 中装载：

```java
    WatermarkBuilder
            .create(this, backgroundImageView) // .create(this, R.drawable.background)
            .getWatermark()
```

如果在水印构建器中你既没有加载文字水印也没有加载图片水印，那么处理过后的图片将保持原样，毕竟你啥也没干 :)

### 隐形水印 (测试版)

androidwm 支持两种模式的隐形水印：

- 空域 LSB 水印
- 频域叠加水印

你可以通过`WatermarkBuilder` 直接构造一个隐形水印，为了选择不同的隐形方式，可以使用布尔参数 `isLSB` 来区分它们 __(注：频域水印仍在开发中)__，而想要获取到构建成功的水印图片，你需要添加一个监听器：

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

同理，检测隐形水印可以使用类`WatermarkDetector`，通过一个`create`方法获取到实例，同时传进去一张加过水印的图片。`.create`方法的第一个布尔参数是水印的种类(`isLSB`)，这决定了可检测水印的种类。

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
LSB 隐形空域水印 Demo 动态图：

|  ![](https://i.loli.net/2018/09/06/5b90f8d80402b.gif)   | ![](https://i.loli.net/2018/09/06/5b90f8d7936f9.gif) | 
| :-------------: | :-------------: | 
|   隐形文字水印 (LSB)   |  隐形图像水印 (LSB)  | 

好啦！请尽情使用吧 :kissing_heart: 关于具体的用法和原理介绍，可以查看 [Wiki](https://github.com/huangyz0918/AndroidWM/blob/master/wikis/WIKI-CN.md)


# 开源许可
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

