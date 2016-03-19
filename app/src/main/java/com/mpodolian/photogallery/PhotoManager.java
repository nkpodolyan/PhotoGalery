package com.mpodolian.photogallery;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.mpodolian.photogallery.datamodels.PhotoInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoManager implements Callback<PhotosResponse> {

    public interface PhotoManagerListener {

        void onStateChanged(PhotoManager photoManager);
    }

    public final static int LOAD_IN_PROGRESS = 0;
    public final static int LOAD_SUCCEED = 1;
    public final static int LOAD_FAILED = 2;
    public final static int LOAD_NEVER_STARTED = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LOAD_IN_PROGRESS, LOAD_SUCCEED, LOAD_FAILED, LOAD_NEVER_STARTED})
    @interface LoadStatus {
    }

    private final static String API_KEY = "wB4ozJxTijCwNuggJvPGtBGCRqaZVcF6jsrzUadF";

    private PhotoService photoService;
    private List<PhotoInfo> photos = new ArrayList<>();
    private boolean isLoading;
    private int totalPages;
    private int currentPage;
    private int lastLoadStatus = LOAD_NEVER_STARTED;
    private Set<PhotoManagerListener> listeners = new HashSet<>();

    public PhotoManager(PhotoService photoService) {
        this.photoService = photoService;
    }

    public void loadMore() {
        if (isLoading || !hasMoreToLoad()) {
            return;
        }
        isLoading = true;
        lastLoadStatus = LOAD_IN_PROGRESS;
        notifyListeners();
        if (photos.size() == 0) {
            photoService.retrievePhotos(PhotoService.FEATURE_POPULAR, API_KEY).enqueue(this);
        } else {
            photoService.retrievePhotos(PhotoService.FEATURE_POPULAR, API_KEY, currentPage + 1).enqueue(this);
        }
    }

    public boolean hasMoreToLoad() {
        return photos.size() == 0 || currentPage < totalPages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    @LoadStatus
    public int getLastLoadStatus() {
        return lastLoadStatus;
    }

    public List<PhotoInfo> getLoadedPhotos() {
        return new ArrayList<>(photos);
    }

    public boolean addListener(@NonNull PhotoManagerListener photoManagerListener) {
        return listeners.add(photoManagerListener);
    }

    public boolean removeListener(@NonNull PhotoManagerListener photoManagerListener) {
        return listeners.remove(photoManagerListener);
    }

    /**
     * <b>Not intended to be directly called</b>
     * {@inheritDoc}
     */
    @Override
    public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
        isLoading = false;
        lastLoadStatus = LOAD_SUCCEED;
        PhotosResponse photosResponse = response.body();
        totalPages = photosResponse.getTotalPages();
        currentPage = photosResponse.getCurrentPage();
        photos.addAll(photosResponse.getPhotos());
        notifyListeners();
    }

    /**
     * <b>Not intended to be directly called</b>
     * {@inheritDoc}
     */
    @Override
    public void onFailure(Call<PhotosResponse> call, Throwable t) {
        isLoading = false;
        lastLoadStatus = LOAD_FAILED;
        notifyListeners();
    }

    private void notifyListeners() {
        for (PhotoManagerListener listener : listeners) {
            listener.onStateChanged(this);
        }
    }
}
