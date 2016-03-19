package com.mpodolian.photogallery;

import com.google.gson.annotations.SerializedName;
import com.mpodolian.photogallery.datamodels.PhotoInfo;

import java.util.List;

public class PhotosResponse {

    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_items")
    private int totalItems;

    public List<PhotoInfo> getPhotos() {
        return photos;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    private List<PhotoInfo> photos;

}
