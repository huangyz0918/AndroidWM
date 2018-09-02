# AndroidWM 
 [ ![Download](https://api.bintray.com/packages/huangyz0918/androidwm/androidwm/images/download.svg) ](https://bintray.com/huangyz0918/androidwm/androidwm/_latestVersion) [![Build Status](https://travis-ci.org/huangyz0918/AndroidWM.svg?branch=master)](https://travis-ci.org/huangyz0918/AndroidWM) [![contributions](https://img.shields.io/badge/contributions-welcome-green.svg)](https://github.com/GalaxyRockets/AndroidWM)

一个轻量级的 Android 图片水印框架，支持隐形水印和加密水印。 [English version](./README.md)

![](https://i.loli.net/2018/09/01/5b8aa948a8935.png)

## 下载与安装 (测试版本)

使用 gradle 下载框架:

```gradle
implementation 'com.huangyz0918:androidwm:v0.1.5'
```

使用 maven 下载框架:

```xml
<dependency>
  <groupId>com.huangyz0918</groupId>
  <artifactId>androidwm</artifactId>
  <version>v0.1.5</version>
  <type>pom</type>
</dependency>
```

使用 lvy 下载框架:

```xml
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

当你的水印（文字或图片水印）已经准备就绪的时候，你需要一个 `WatermarkBuilder`来把水印画到你希望的背景图片上。 你可以通过 `create` 方法获取一个 `WatermarkBuilder` 的实例，注意，在创建这个实例的时候你需要先传入一个 `Bitmap` 或者是一个 `Drawable` 的资源 id 来获取背景图。

```java
    WatermarkBuilder
            .create(context, backgroundBitmap)
            .loadWatermarkText(watermarkText) // use .loadWatermarkImage(watermarkImage) to load an image.
            .getWatermark()
            .setToImageView(imageView);
```

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


你可以在 `WatermarkBuilder` 中同时加载文字水印和图片水印。 如果你想在绘制完成之后获得带水印的结果图片，可以使用 `Watermark` 的 `.getOutputImage()` 方法：

```java
    Bitmap bitmap = WatermarkBuilder
            .create(this, backgroundBitmap)
            .getWatermark()
            .getOutputImage();
```

你还可以一次性添加多个水印图片，通过创建一个`WatermarkText` 的列表 `List<>` 并且在水印构建器的方法 ` .loadWatermarkTexts(watermarkTexts)`中把列表传入进去（图片类型水印同理）：

```java
    WatermarkBuilder
            .create(this, backgroundBitmap)
            .loadWatermarkTexts(watermarkTexts)
            .loadWatermarkImages(watermarkImages)
            .getWatermark();
```

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


好啦！请尽情使用吧 :kissing_heart:


## 使用说明

对于 `WatermarkText` 和 `WatermarkImage` 的定制化，我们提供了一些常用的方法:


|   __方法名称__  | __备注__ | __默认值__ |
| ------------- | ------------- | ------------- |
| setPosition  | 水印的位置类 `WatermarkPosition` | _null_ |
| setPositionX  |  水印的横轴坐标，从背景图片左上角为(0,0)  | _0_  |
| setPositionY  |  水印的纵轴坐标，从背景图片左上角为(0,0)  | _0_ |
| setRotation  |  水印的旋转角度| _0_  |
| setTextColor  (`WatermarkText`)  |  `WatermarkText` 的文字颜色 | _`Color.BLACK`_  |
| setTextStyle  (`WatermarkText`)  |  `WatermarkText` 的文字样式| _`Paint.Style.FILL`_  |
| setBackgroundColor  (`WatermarkText`) |  `WatermarkText` 的背景颜色 | _null_  |
| setTextAlpha  (`WatermarkText`) | `WatermarkText` 文字的透明度， 从 0 到 255 | _50_  |
| setImageAlpha  (`WatermarkImage`) | `WatermarkImage` 图片的透明度， 从 0 到 255 | _50_  |
| setTextSize (`WatermarkText`) | `WatermarkText` 字体的大小，单位与系统 layout 相同 | _20_   |
| setSize  (`WatermarkImage`)|  `WatermarkImage` 水印图片的大小，从 0 到 1 (背景图片大小的比例) | _0.2_   |
| setWatermarkVisibility (`WatermarkText`) | `WatermarkText` 是否是可见的文字水印 | _true_   |
| setWatermarkEncrypted  (`WatermarkText`)|  `WatermarkText` 是否是加密的文字水印 | _false_   |
| setTextFont (`WatermarkText`) | `WatermarkText` 的字体| _default_  |
| setTextShadow  (`WatermarkText`)| `WatermarkText` 字体的阴影与圆角 | _(0, 0, 0)_  |
| setImageDrawable  (`WatermarkImage`)| `WatermarkImage`的图片资源 | _null_ |

`WatermarkImage` 的一些基本属性和`WatermarkText` 的相同。


