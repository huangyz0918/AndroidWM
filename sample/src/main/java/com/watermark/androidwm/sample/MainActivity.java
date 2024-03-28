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
package com.watermark.androidwm.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.watermark.androidwm.WatermarkDetector;
import com.watermark.androidwm.bean.WatermarkPosition;
import com.watermark.androidwm.task.DetectionReturnValue;
import com.watermark.androidwm.listener.BuildFinishListener;
import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.bean.WatermarkImage;
import com.watermark.androidwm.bean.WatermarkText;
import com.watermark.androidwm.listener.DetectFinishListener;

import timber.log.Timber;
//import com.watermark.androidwm.utils.BitmapUtils;

/**
 * This is the sample for library: androidwm.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 * @since 29/08/2018
 */
public class MainActivity extends AppCompatActivity {

    private RadioButton mode_single;
    private RadioButton mode_tile;
    private RadioButton mode_invisible;
    private Button btnAddText;
    private Button btnAddImg;
    private Button btnDetectImage;
    private Button btnDetectText;
    private Button btnClear;

    private ImageView backgroundView;
    private ImageView watermarkView;
    private Bitmap watermarkBitmap;

    private EditText editText;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }

    private void initViews() {
        mode_single = findViewById(R.id.mode_single);
        mode_tile = findViewById(R.id.mode_tile);
        mode_invisible = findViewById(R.id.mode_invisible);
        btnAddImg = findViewById(R.id.btn_add_image);
        btnAddText = findViewById(R.id.btn_add_text);
        btnDetectImage = findViewById(R.id.btn_detect_image);
        btnDetectText = findViewById(R.id.btn_detect_text);
        btnClear = findViewById(R.id.btn_clear_watermark);

        editText = findViewById(R.id.editText);
        backgroundView = findViewById(R.id.imageView);
        watermarkView = findViewById(R.id.imageView_watermark);

        progressBar = findViewById(R.id.progressBar);

        watermarkBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test_watermark);

        watermarkView.setVisibility(View.GONE);
    }

    private void initEvents() {
        // The sample method of adding a text watermark.
        btnAddText.setOnClickListener((View v) -> {
            String markText = editText.getText().toString();
            if(markText.trim().isEmpty()){
                Toast.makeText(this, "Please Input Text First", Toast.LENGTH_SHORT).show();
                return;
            }
            if(mode_invisible.isChecked()){
                createInvisibleTextMark();
                return;
            }
            WatermarkText watermarkText = new WatermarkText(markText)
                    .setPosition(new WatermarkPosition(0.5, 0.5))
                    .setOrigin(new WatermarkPosition(0.5, 0.5))
                    .setTextSize(40)
                    .setTextAlpha(255)
                    .setTextColor(Color.WHITE)
                    .setTextFont(R.font.champagne)
                    .setTextShadow(0.1f, 5, 5, Color.BLUE);

            WatermarkBuilder.create(this, backgroundView)
                    .setTileMode(mode_tile.isChecked())
                    .loadWatermarkText(watermarkText)
                    .getWatermark()
                    .setToImageView(backgroundView);
        });

        // The sample method of adding an image watermark.
        btnAddImg.setOnClickListener((View v) -> {
            if(mode_invisible.isChecked()){
                createInvisibleImgMark();
                return;
            }
            // Math.random()
            WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap)
                    .setImageAlpha(80)
                    .setPositionX(Math.random())
                    .setPositionY(Math.random())
                    // .setPosition(new WatermarkPosition(0.5, 0.5))
                    .setOrigin(new WatermarkPosition(0.5, 0.5))
                    .setRotation(15)
                    .setSize(0.1);

            WatermarkBuilder
                    .create(this, backgroundView)
                    .loadWatermarkImage(watermarkImage)
                    .setTileMode(mode_tile.isChecked())
                    .getWatermark()
                    .setToImageView(backgroundView);

        });

        // detect the text watermark.
        btnDetectText.setOnClickListener((View v) -> {
            progressBar.setVisibility(View.VISIBLE);
            WatermarkDetector.create(backgroundView, true)
                    .detect(new DetectFinishListener() {
                        @Override
                        public void onSuccess(DetectionReturnValue returnValue) {
                            progressBar.setVisibility(View.GONE);
                            if (returnValue != null) {
                                Toast.makeText(MainActivity.this, "Successfully detected invisible text: "
                                        + returnValue.getWatermarkString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(String message) {
                            progressBar.setVisibility(View.GONE);
                            Timber.e(message);
                        }
                    });
        });

        // detect the image watermark.
        btnDetectImage.setOnClickListener((View v) -> {
            progressBar.setVisibility(View.VISIBLE);
            WatermarkDetector.create(backgroundView, true)
                    .detect(new DetectFinishListener() {
                        @Override
                        public void onSuccess(DetectionReturnValue returnValue) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this,
                                    "Successfully detected invisible watermark!", Toast.LENGTH_SHORT).show();
                            if (returnValue != null) {
                                watermarkView.setVisibility(View.VISIBLE);
                                watermarkView.setImageBitmap(returnValue.getWatermarkBitmap());
                            }
                        }

                        @Override
                        public void onFailure(String message) {
                            progressBar.setVisibility(View.GONE);
                            Timber.e(message);
                        }
                    });
        });

        // reload the background.
        btnClear.setOnClickListener((View v) -> {
            Glide.with(this).load(R.drawable.test2)
                    .into(backgroundView);
            watermarkView.setVisibility(View.GONE);
        });

    }

    private void createInvisibleImgMark(){
        progressBar.setVisibility(View.VISIBLE);
        WatermarkBuilder
                .create(this, backgroundView)
                .loadWatermarkImage(watermarkBitmap)
                .setInvisibleWMListener(true, new BuildFinishListener<Bitmap>() {
                    @Override
                    public void onSuccess(Bitmap object) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,
                                "Successfully create invisible watermark!", Toast.LENGTH_SHORT).show();
                        if (object != null) {
                            backgroundView.setImageBitmap(object);
                            // Save to local needs permission.
//                                BitmapUtils.saveAsPNG(object, "sdcard/DCIM/", true);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        progressBar.setVisibility(View.GONE);
                        Timber.e(message);
                    }
                });
    }

    private void createInvisibleTextMark(){
        progressBar.setVisibility(View.VISIBLE);
        WatermarkText watermarkText = new WatermarkText(editText.getText().toString());
        WatermarkBuilder
                .create(this, backgroundView)
                .loadWatermarkText(watermarkText)
                .setInvisibleWMListener(true, new BuildFinishListener<Bitmap>() {
                    @Override
                    public void onSuccess(Bitmap object) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,
                                "Successfully create invisible watermark!", Toast.LENGTH_SHORT).show();
                        if (object != null) {
                            backgroundView.setImageBitmap(object);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        progressBar.setVisibility(View.GONE);
                        Timber.e(message);
                    }
                });
    }
}
