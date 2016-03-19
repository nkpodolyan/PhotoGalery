package com.mpodolian.photogallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mpodolian.photogallery.datamodels.PhotoInfo;

public class PhotoViewActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String PHOTO_INFO_KEY = "photo_info";

    public static void start(Activity context, @NonNull PhotoInfo photoInfo) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(PHOTO_INFO_KEY, photoInfo);
        context.startActivity(intent);
    }

    private PhotoInfo photoInfo;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoInfo = getIntent().getParcelableExtra(PHOTO_INFO_KEY);
        setContentView(R.layout.activity_photo_view);
        Glide.with(this).load(photoInfo.getImageUrl()).into((ImageView) findViewById(R.id.photo));
        findViewById(R.id.share_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/html");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, photoInfo.getImageUrl());
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }
}
