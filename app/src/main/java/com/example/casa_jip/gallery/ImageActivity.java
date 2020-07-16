package com.example.casa_jip.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.casa_jip.R;

public class ImageActivity extends AppCompatActivity {

    private ImageView ImageViewThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageViewThumbnail = findViewById(R.id.image_detail_thumbnail);

        //Receiving Data From Adapter
        Intent intent = getIntent();
        String ImageUrl = intent.getExtras().getString("Url");

        //Setting the values in the layout
        Glide.with(ImageActivity.this.getBaseContext()).load(ImageUrl).into(ImageViewThumbnail);
    }
}