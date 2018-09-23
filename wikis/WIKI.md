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
