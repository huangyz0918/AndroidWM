/*
 *    Copyright 2018 huangyz0918
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.watermark.androidwm.bean;

/**
 * It's a class for saving the position of watermark.
 * Can be used for a single image/text or a set of
 * images/texts.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 * @since 29/08/2018
 */
public class WatermarkPosition {

    private double positionX = 0; //default X position
    private double positionY = 0; //default Y position
    private double rotation = 0; //default rotation

    public WatermarkPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public WatermarkPosition(double positionX, double positionY, double rotation) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.rotation = rotation;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
}
