# AndroidWM 
 [ ![Download](https://api.bintray.com/packages/galaxyrockets/AndroidWM/androidwm/images/download.svg) ](https://bintray.com/galaxyrockets/AndroidWM/androidwm/_latestVersion) [![build Status](https://travis-ci.org/GalaxyRockets/AndroidWM.svg?branch=master)](https://travis-ci.org/GalaxyRockets/AndroidWM) [![principal](https://img.shields.io/badge/principal-huangyz0918-yellow.svg)](https://github.com/huangyz0918) [![contributions](https://img.shields.io/badge/contributions-welcome-green.svg)](https://github.com/GalaxyRockets/AndroidWM)

一个轻量级的 Android 图片水印框架，支持隐形水印和加密水印。 [English version](./README.md)

![](https://i.loli.net/2018/09/01/5b8a5ab312d67.png)

## 下载与安装 (测试版本)

使用 gradle 下载框架:

```java
implementation 'com.watermark:androidwm:0.1.4'
```

使用 maven 下载框架:

```java
<dependency>
  <groupId>com.watermark</groupId>
  <artifactId>androidwm</artifactId>
  <version>0.1.4</version>
  <type>pom</type>
</dependency>
```

使用 lvy 下载框架:

```java
<dependency org='com.watermark' name='androidwm' rev='0.1.4'>
  <artifact name='androidwm' ext='pom' />
</dependency>
```

## 快速入门
在下载并且在您的项目内配置好 androidwm 之后, 您可以使用 `WatermarkBuilder` 快速地获取到一个水印的实例 (`Watermark`).
同时，您可以使用 `loadWatermarkImage(Bitmap)` 或者 `loadWatermarkText(String)` 方法为背景图片添加一个图片水印或者是文字水印:

```java
    WatermarkBuilder
            .create(context, backgroundBitmap)
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
            .create(context, backgroundBitmap)
            .loadWatermarkImage(watermarkImage)
            .getWatermark()
            .setToImageView(imageView);
```

对于 `WatermarkText` 的定制化，我们提供了一些常用的方法:


|   __方法名称__  | __备注__ | __默认值__ |
| ------------- | ------------- | ------------- |
| setPosition  |    `WatermarkText` 的 `WatermarkPosition` | _null_ |
| setPositionX  |  `WatermarkText` 绘制的横轴坐标  | _0_  |
| setPositionY  |  `WatermarkText` 绘制的纵轴坐标 | _0_ |
| setRotation  |  `WatermarkText` 的旋转角度, 从 0 到 360 | _0_  |
| setTextColor   |   `WatermarkText` 的字体颜色 | _`Color.BLACK`_  |
| setTextStyle    |   `WatermarkText` 的字体风格 | _`Paint.Style.FILL`_  |
| setBackgroundColor   |  `WatermarkText` 水印文字的背景颜色 | _null_  |
| setTextAlpha   |   `WatermarkText` 的透明度, 从 0 到 255 | _50_  |
| setTextSize  |  `WatermarkText` 的字体大小 | _20_   |
| setWatermarkVisibility  |  `WatermarkText` 水印是否是可见水印 | _true_   |
| setWatermarkEncrypted  | `WatermarkText` 是否对水印进行加密| _false_   |

`WatermarkImage` 的一些基本属性和`WatermarkText` 的相同, 但是对于图片水印来说, 没有文字样式和背景（所以也就不存在什么背景颜色）. 如果你要从一个视图中加载字符串作为水印文字, 你可以使用下面的方法:

```java
WatermarkText watermarkText = new WatermarkText(editText); // EditText.
WatermarkText watermarkText = new WatermarkText(textView); // TextView.
WatermarkImage watermarkImage = new WatermarkImage(imageView); // ImageView.
```
同理，你也可以从 `ImageView` 中加载图片作为水印图片:

```java
    WatermarkBuilder
            .create(this, backgroundImageView)
            .getWatermark()

```

如果你想要一次性添加多个不同的文字水印，你可以使用一个线性表 `List<>` 来放置你的水印， 你可以把放有你水印的 `List<>` 通过方法： ` .loadWatermarkTexts(watermarkTexts)` 加载到水印构建器中，添加图片类型的水印同理：

```java
    WatermarkBuilder
            .create(this, backgroundImageView)
            .loadWatermarkTexts(watermarkTexts)
            .loadWatermarkImages(watermarkImages)
            .getWatermark()
```

如果你想要获得处理后的图片，你可以使用方法 `.getOutputImage()` ，就像这样：

```java
    Bitmap bitmap = WatermarkBuilder
            .create(this, backgroundImageView)
            .getWatermark()
            .getOutputImage();

```

如果在水印构建器中你既没有加载文字水印也没有加载图片水印，那么处理过后的图片将保持原样，毕竟你啥也没干 :)。

