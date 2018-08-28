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
package com.watermark.androidwm.utils;


import java.io.UnsupportedEncodingException;

/**
 * Base64 is a group of similar binary-to-text encoding
 * schemes that represent binary data in an ASCII string
 * format by translating it into a radix-64 representation.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 * @since 28/08/2018
 */
public class Base64Utils {
    private static final char[] BASE64ENCODECHARS = new char[]
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                    '6', '7', '8', '9', '+', '/'};
    private static final byte[] BASE64DECODECHARS = new byte[]
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53,
                    54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                    12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29,
                    30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
                    -1, -1, -1};

    /**
     * Encryption method.
     *
     * @param data
     */
    public static String encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1;
        int b2;
        int b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(BASE64ENCODECHARS[b1 >>> 2]);
                sb.append(BASE64ENCODECHARS[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(BASE64ENCODECHARS[b1 >>> 2]);
                sb.append(BASE64ENCODECHARS[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(BASE64ENCODECHARS[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(BASE64ENCODECHARS[b1 >>> 2]);
            sb.append(BASE64ENCODECHARS[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(BASE64ENCODECHARS[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(BASE64ENCODECHARS[b3 & 0x3f]);
        }
        return sb.toString();
    }

    /**
     * Decryption method.
     *
     * @param str
     */
    public static byte[] decode(String str) {
        try {
            return decodePrivate(str);
        } catch (UnsupportedEncodingException e) {
            // Log.e(Constants.TAG, "decode: ", e);
        }
        return new byte[]
                {};
    }

    private static byte[] decodePrivate(String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        byte[] data;
        data = str.getBytes("US-ASCII");
        int len = data.length;
        int i = 0;
        int b1;
        int b2;
        int b3;
        int b4;

        while (i < len) {

            do {
                b1 = BASE64DECODECHARS[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) {
                break;
            }

            do {
                b2 = BASE64DECODECHARS[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) {
                break;
            }
            sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            do {
                b3 = data[i++];
                if (b3 == 61) {
                    return sb.toString().getBytes("iso8859-1");
                }
                b3 = BASE64DECODECHARS[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) {
                break;
            }
            sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

            do {
                b4 = data[i++];
                if (b4 == 61) {
                    return sb.toString().getBytes("iso8859-1");
                }
                b4 = BASE64DECODECHARS[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) {
                break;
            }
            sb.append((char) (((b3 & 0x03) << 6) | b4));
        }
        return sb.toString().getBytes("iso8859-1");
    }

}