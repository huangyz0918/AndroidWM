/*
 *    Copyright 2018 Yizheng Huang
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
package com.watermark.androidwm_light.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.watermark.androidwm_light.WatermarkBuilder;
import com.watermark.androidwm_light.bean.WatermarkImage;
import com.watermark.androidwm_light.bean.WatermarkText;

/**
 * This is the sample for library: androidwm.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 * @since 29/08/2018
 */
public class MainActivity extends AppCompatActivity {

    private Button btnAddText;
    private Button btnAddImg;
    private Button btnClear;
    private Button btnSingleText;
    private Button btnSingleImg;

    private ImageView backgroundView;
    private Bitmap watermarkBitmap;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }

    private void initViews() {
        btnAddImg = findViewById(R.id.btn_add_image);
        btnAddText = findViewById(R.id.btn_add_text);
        btnClear = findViewById(R.id.btn_clear_watermark);
        btnSingleImg = findViewById(R.id.btn_single_image);
        btnSingleText = findViewById(R.id.btn_single_text);

        editText = findViewById(R.id.editText);
        backgroundView = findViewById(R.id.imageView);

        watermarkBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test_watermark);

    }

    private void initEvents() {
        // The sample method of adding a text watermark.
        btnAddText.setOnClickListener((View v) -> {
            WatermarkText watermarkText = new WatermarkText(editText.getText().toString())
                    .setPositionX(0.5)
                    .setPositionY(0.5)
                    .setTextAlpha(255)
                    .setTextColor(Color.WHITE)
                    .setTextFont(R.font.champagne)
                    .setTextShadow(0.1f, 5, 5, Color.BLUE);

            WatermarkBuilder.create(this, backgroundView)
                    .setTileMode(true)
                    .loadWatermarkText(watermarkText)
                    .getWatermark()
                    .setToImageView(backgroundView);
        });

        btnSingleText.setOnClickListener((View v) -> {
            WatermarkText watermarkText = new WatermarkText(editText.getText().toString())
                    .setPositionX(Math.random())
                    .setPositionY(Math.random())
                    .setTextAlpha(255)
                    .setTextColor(Color.WHITE)
                    .setTextFont(R.font.champagne)
                    .setTextShadow(0.1f, 5, 5, Color.BLUE);

            WatermarkBuilder.create(this, backgroundView)
                    .loadWatermarkText(watermarkText)
                    .getWatermark()
                    .setToImageView(backgroundView);
        });

        // The sample method of adding an image watermark.
        btnAddImg.setOnClickListener((View v) -> {
            // Math.random()
            WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap)
                    .setImageAlpha(80)
                    .setRotation(15)
                    .setSize(0.2);

            WatermarkBuilder
                    .create(this, backgroundView)
                    .loadWatermarkImage(watermarkImage)
                    .setTileMode(true)
                    .getWatermark()
                    .setToImageView(backgroundView);

        });

        btnSingleImg.setOnClickListener((View v) -> {
            WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap)
                    .setImageAlpha(80)
                    .setPositionX(Math.random())
                    .setPositionY(Math.random())
                    .setRotation(15)
                    .setSize(0.2);

            WatermarkBuilder
                    .create(this, backgroundView)
                    .loadWatermarkImage(watermarkImage)
                    .getWatermark()
                    .setToImageView(backgroundView);
        });

        // reload the background.
        btnClear.setOnClickListener((View v) -> {
            Glide.with(this).load(R.drawable.test)
                    .into(backgroundView);
        });

    }
}
