# AndroidWM 
 [ ![Download](https://api.bintray.com/packages/huangyz0918/androidwm/androidwm/images/download.svg) ](https://bintray.com/huangyz0918/androidwm/androidwm/_latestVersion) [![Build Status](https://travis-ci.org/huangyz0918/AndroidWM.svg?branch=master)](https://travis-ci.org/huangyz0918/AndroidWM) [![contributions](https://img.shields.io/badge/contributions-welcome-green.svg)](https://github.com/GalaxyRockets/AndroidWM)

一个轻量级的 Android 图片水印框架，支持隐形水印和加密水印。 [English version](./README.md)

![](https://i.loli.net/2018/09/01/5b8aa948a8935.png)

## 下载与安装 (测试版本)

使用 gradle 下载框架:

```groovy
implementation 'com.huangyz0918:androidwm:v0.1.5'
```

使用 maven 下载框架:

```maven
<dependency>
  <groupId>com.huangyz0918</groupId>
  <artifactId>androidwm</artifactId>
  <version>v0.1.5</version>
  <type>pom</type>
</dependency>
```

使用 lvy 下载框架:

```lvy
<dependency org='com.huangyz0918' name='androidwm' rev='v0.1.5'>
  <artifact name='androidwm' ext='pom' ></artifact>
</dependency>
```

## 快速入门

在下载并且配置好 androidwm 之后，你可以创建一个 `WatermarkImage` 或者是 `WatermarkText` 的实例，并且使用内置的诸多`Set`方法为创建一个水印做好准备。

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

对于具体定制一个文字水印或者是图片水印， 我们在接下来的文档中会仔细介绍。

当你的水印（文字或图片水印）已经准备就绪的时候，你需要一个 `WatermarkBuilder`来把水印画到你希望的背景图片上。 你可以通过 `create` 方法获取一个 `WatermarkBuilder` 的实例，注意，在创建这个实例的时候你需要先传入一个 `Bitmap` 或者是一个 `Drawable` 的资源 id。

```java
    WatermarkBuilder
            .create(context, backgroundBitmap)
            .loadWatermarkText(watermarkText)
            .loadWatermarkImage(watermarkImage)
            .getWatermark()
            .setToImageView(imageView);
```

你可以通过`loadxxx()`方法将你定制好的水印加载到 `WatermarkBuilder`里。 如果你想获得最终绘制的图片，你可以在`.getWatermark()`之后调用 `.getOutputImage()`方法，它将返回一个 `Bitmap`：

```java
    Bitmap bitmap = WatermarkBuilder
            .create(this, backgroundBitmap)
            .getWatermark()
            .getOutputImage();
```

如果你想在一个`WatermarkBuilder` 里面同时创建多个水印，你可以使用一个链表 `List<>` 来放置你想绘制的水印对象， 并且使用方法： ` .loadWatermarkTexts(watermarkTexts)`加载文字水印到背景图片中，图片水印同理：

```java
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkTexts(watermarkTexts)
            .loadWatermarkImages(watermarkImages)
            .getWatermark();
```

好啦，到这里你已经掌握了 androidwm 最基础的用法，enjoy yourself!

## 使用说明

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
WatermarkImage watermarkImage = new WatermarkImage(this, R.drawable.image); // for an image from Resource.
```
类 `WatermarkBuilder` 初始化背景图片的时候也可以从系统的资源中添加（如：R.drawable.background） 。如果你想要从一个 `ImageView` 加载图片作为底图，也是可以的：

```java
    WatermarkBuilder
            .create(this, backgroundImageView)
            .getWatermark()
```

如果在水印构建器中你既没有加载文字水印也没有加载图片水印，那么处理过后的图片将保持原样，毕竟你啥也没干 :)。

