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
package com.watermark.androidwm.sample;

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
import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.bean.WatermarkImage;
import com.watermark.androidwm.bean.WatermarkText;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the sample for library: androidwm.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 * @since 29/08/2018
 */
public class MainActivity extends AppCompatActivity {

    private Button btnAddText;
    private Button btnAddImg;
    private Button btnAddAll;
    private Button btnAddList;
    private Button btnClear;

    private ImageView backgroundView;
    private Bitmap watermarkBitmap;
//    private Bitmap watermarkBackground;

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
        btnAddAll = findViewById(R.id.btn_add_all);
        btnAddList = findViewById(R.id.btn_add_list);
        btnClear = findViewById(R.id.btn_clear_watermark);

        editText = findViewById(R.id.editText);
        backgroundView = findViewById(R.id.imageView);

        Glide.with(this).load(R.drawable.test)
                .into(backgroundView);

//        watermarkBackground = BitmapFactory.decodeResource(getResources(), R.drawable.test);

        watermarkBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test_watermark);
    }

    private void initEvents() {
        // The sample method of adding a text watermark.
        btnAddText.setOnClickListener((View v) -> {
            WatermarkText watermarkText = new WatermarkText(editText)
                    .setPositionX(Math.random())
                    .setPositionY(Math.random())
                    .setTextColor(Color.BLACK)
                    .setTextAlpha(150)
                    .setRotation(30)
                    .setTextSize(20);

            WatermarkBuilder
                    .create(this, backgroundView)
                    .loadWatermarkText(watermarkText)
                    .setTileMode(true)
                    .getWatermark()
                    .setToImageView(backgroundView);
        });

        // The sample method of adding a image watermark.
        btnAddImg.setOnClickListener((View v) -> {

            // Math.random()
            WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap)
                    .setImageAlpha(100)
                    .setPositionX(Math.random())
                    .setPositionY(Math.random())
                    .setRotation(15)
                    .setSize(0.2);

            WatermarkBuilder
                    .create(this, backgroundView)
                    .loadWatermarkImage(watermarkImage)
                    .setTileMode(true)
                    .getWatermark()
                    .setToImageView(backgroundView);
        });

        // The sample method of adding both image and text watermark.
        btnAddAll.setOnClickListener((View v) -> {
            WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap)
                    .setImageAlpha(150)
                    .setPositionX(Math.random())
                    .setPositionY(Math.random())
                    .setSize(0.1);

            WatermarkText watermarkText = new WatermarkText("FFF")
                    .setPositionX(Math.random())
                    .setPositionY(Math.random())
                    .setTextColor(Color.WHITE)
                    .setTextAlpha(150)
                    .setBackgroundColor(Color.GREEN)
                    .setTextSize(50);


            Bitmap outputBitmap = WatermarkBuilder
                    .create(this, backgroundView)
                    .loadWatermarkImage(watermarkImage)
                    .loadWatermarkText(watermarkText)
                    .getWatermark().getOutputImage();

            backgroundView.setImageBitmap(outputBitmap);
        });

        // The sample method of adding a list of watermarks.
        btnAddList.setOnClickListener((View v) -> {
            List<WatermarkText> watermarkTexts = new ArrayList<>();
            List<WatermarkImage> watermarkImages = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                WatermarkText watermarkText = new WatermarkText("FBI Warning: " + i)
                        .setPositionX(Math.random())
                        .setPositionY(Math.random())
                        .setTextColor(Color.WHITE)
                        .setTextAlpha(150)
                        .setBackgroundColor(Color.RED)
                        .setTextSize(20);

                WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap)
                        .setImageAlpha(150)
                        .setPositionX(Math.random())
                        .setPositionY(Math.random())
                        .setSize(0.1);

                watermarkTexts.add(watermarkText);
                watermarkImages.add(watermarkImage);
            }

            WatermarkBuilder
                    .create(this, backgroundView)
                    .loadWatermarkTexts(watermarkTexts)
                    .loadWatermarkImages(watermarkImages)
                    .getWatermark()
                    .setToImageView(backgroundView);
        });

        btnClear.setOnClickListener((View v) -> {
            Glide.with(this).load(R.drawable.test)
                    .into(backgroundView);
        });

    }
}
