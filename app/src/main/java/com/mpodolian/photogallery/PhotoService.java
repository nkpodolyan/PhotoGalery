package com.mpodolian.photogallery;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoService {

    String FEATURE_POPULAR = "popular";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({FEATURE_POPULAR})
    @interface Feature {
    }

    @GET("v1/photos")
    Call<PhotosResponse> retrievePhotos(@Query("feature") @Feature String feature,
                                        @Query("consumer_key") String apiKey);

    @GET("v1/photos")
    Call<PhotosResponse> retrievePhotos(@Query("feature") @Feature String feature,
                                        @Query("consumer_key") String apiKey,
                                        @Query("page") int page);

}
