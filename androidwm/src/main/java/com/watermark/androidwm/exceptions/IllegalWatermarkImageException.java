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
package com.watermark.androidwm.exceptions;


/**
 * An exception for using illegal image for image bitmap.
 * For some picture that cannot be measured normally ,the
 * {@link android.graphics.Bitmap} will throw {@link IllegalArgumentException}
 * This is a kind of {@link IllegalWatermarkImageException}.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
public class IllegalWatermarkImageException extends Exception {

    public IllegalWatermarkImageException() {

    }

    public IllegalWatermarkImageException(String message) {
        super(message);
    }

    public IllegalWatermarkImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalWatermarkImageException(Throwable cause) {
        super(cause);
    }

}
