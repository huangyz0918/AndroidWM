# AndroidWM 
 [ ![Download](https://api.bintray.com/packages/huangyz0918/androidwm/androidwm/images/download.svg) ](https://bintray.com/huangyz0918/androidwm/androidwm/_latestVersion) [![Build Status](https://travis-ci.org/huangyz0918/AndroidWM.svg?branch=master)](https://travis-ci.org/huangyz0918/AndroidWM) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/7f8e55520309410a95f71b54cfe8c381)](https://app.codacy.com/app/huangyz0918/AndroidWM?utm_source=github.com&utm_medium=referral&utm_content=huangyz0918/AndroidWM&utm_campaign=Badge_Grade_Dashboard)
 ![progress](https://img.shields.io/badge/progress-developing-yellow.svg)

 
一个轻量级的 Android 图片水印框架，支持隐形水印和加密水印。 [English version](./README.md)

![](https://i.loli.net/2018/09/11/5b97dddb4e407.png)

# 下载与安装 

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

# 快速入门
### 新建一个水印图片
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

你可以通过`WatermarkBuilder` 直接构造一个隐形水印，为了选择不同的隐形方式，可以使用布尔参数 `isLSB` 来区分它们 __(注：频域水印扔在开发中)__，而想要获取到构建成功的水印图片，你需要添加一个监听器：

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
`setInvisibleWMListener` 方法的第二个参数是一个整数，表示输入图片最大尺寸，有的时候，你输入的可能是一个巨大的图片，为了使计算算法更加快速，你可以选择在构建图片之前是否对图片进行缩放，如果你让这个参数为空，那么图片将以原图形式进行添加水印操作。无论如何，注意一定要保持背景图片的大小足以放得下水印图片中的信息，否则会抛出异常。

同理，检测隐形水印可以使用类`WatermarkDetector`，通过一个`create`方法获取到实例，同时传进去一张加过水印的图片，第一个布尔参数代表着水印的种类，true 代表着检测文字水印，反之则检测图形水印。

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
LSB 隐形空域水印 Demo 动态图：

|  ![](https://i.loli.net/2018/09/06/5b90f8d80402b.gif)   | ![](https://i.loli.net/2018/09/06/5b90f8d7936f9.gif) | 
| :-------------: | :-------------: | 
|   隐形文字水印 (LSB)   |  隐形图像水印 (LSB)  | 

好啦！请尽情使用吧 :kissing_heart:


# 使用说明

## 水印位置 
我们使用 `WatermarkPosition` 这个类的对象来控制具体水印出现的位置。

```java 
   WatermarkPosition watermarkPosition = new WatermarkPosition(double position_x, double position_y, double rotation);
   WatermarkPosition watermarkPosition = new WatermarkPosition(double position_x, double position_y);
```

在函数构造器中，我们可以设定水印图片的横纵坐标，如果你想在构造器中初始化一个水印旋转角度也是可以的， 水印的坐标系以背景图片的左上角为原点，横轴向右，纵轴向下。

`WatermarkPosition` 同时也支持动态调整水印的位置，这样你就不需要一次又一次地初始化新的位置对象了， androidwm 提供了一些方法：

```java
     watermarkPosition
              .setPositionX(x)
              .setPositionY(y)
              .setRotation(rotation);
```
在全覆盖水印模式(Tile mode)下，关于水印位置的参数将会失效。

|  ![](https://i.loli.net/2018/09/05/5b8f4a970a83e.png)   | ![](https://i.loli.net/2018/09/05/5b8f4a9706788.png) | 
| :-------------: | :-------------: | 
|   x = y = 0, rotation = 15 | x = y = 0.5, rotation = -15  | 

横纵坐标都是一个从 0 到 1 的浮点数，代表着和背景图片的相对比例。


## 字体水印的颜色

你可以在 `WatermarkText` 中设置字体水印的颜色或者是其背景颜色:

```java
    WatermarkText watermarkText = new WatermarkText(editText)
            .setPositionX(0.5)
            .setPositionY(0.5)
            .setTextSize(30)
            .setTextAlpha(200)
            .setTextColor(Color.GREEN)
            .setBackgroundColor(Color.WHITE); // 默认背景颜色是透明的
```

|  ![](https://i.loli.net/2018/09/05/5b8f4ce0cf6ce.png)   | ![](https://i.loli.net/2018/09/05/5b8f4ce11a28c.png) | 
| :-------------: | :-------------: | 
|   color = green, background color = white | color = green, background color = default  | 

## 字体颜色的阴影和字体
你可以从软件资源中加载一种字体，也可以通过方法 `setTextShadow` 设置字体的阴影。

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

阴影的四个参数分别为： `(blur radius, x offset, y offset, color)`.

## 字体大小和图片大小

水印字体和水印图片大小的单位是不同的：
- 字体大小和系统布局中字体大小是类似的，取决于屏幕的分辨率和背景图片的像素，您可能需要动态调整。
- 图片大小是一个从 0 到 1 的浮点数，是水印图片的宽度占背景图片宽度的比例。

|  ![](https://i.loli.net/2018/09/05/5b8f5eb1a7fb0.png)   | ![](https://i.loli.net/2018/09/05/5b8f5eb24d0fd.png) | 
| :-------------: | :-------------: | 
|   image size = 0.3 | text size = 40  | 


## 方法列表
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
| setTextFont (`WatermarkText`) | `WatermarkText` 的字体| _default_  |
| setTextShadow  (`WatermarkText`)| `WatermarkText` 字体的阴影与圆角 | _(0, 0, 0)_  |
| setImageDrawable  (`WatermarkImage`)| `WatermarkImage`的图片资源 | _null_ |

`WatermarkImage` 的一些基本属性和`WatermarkText` 的相同。


