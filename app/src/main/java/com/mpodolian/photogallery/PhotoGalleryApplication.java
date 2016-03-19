package com.mpodolian.photogallery;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoGalleryApplication extends Application {

    private PhotoManager photoManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instantiatePhotoManager();
    }

    public PhotoManager providePhotoManager() {
        return photoManager;
    }

    private void instantiatePhotoManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.500px.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PhotoService photoService = retrofit.create(PhotoService.class);
        photoManager = new PhotoManager(photoService);
    }
}
