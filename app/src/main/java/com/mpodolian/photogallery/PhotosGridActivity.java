package com.mpodolian.photogallery;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.mpodolian.photogallery.datamodels.PhotoInfo;

import java.util.ArrayList;
import java.util.List;

public class PhotosGridActivity extends AppCompatActivity implements PhotoManager.PhotoManagerListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PhotosAdapter photosAdapter;
    private PhotoManager photoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoManager = ((PhotoGalleryApplication) getApplication()).providePhotoManager();
        int gridColumns = getResources().getInteger(R.integer.grid_columns);

        setContentView(R.layout.activity_photos_grid);
        recyclerView = (RecyclerView) findViewById(R.id.photos_grid);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColumns));
        photosAdapter = new PhotosAdapter();
        recyclerView.setAdapter(photosAdapter);
        photoManager.addListener(this);

        if (photoManager.getLastLoadStatus() == PhotoManager.LOAD_NEVER_STARTED) {
            photoManager.loadMore();
        } else {
            adjustUIAccordingToManagerState();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        photoManager.removeListener(this);
    }

    @Override
    public void onStateChanged(PhotoManager photoManager) {
        adjustUIAccordingToManagerState();
    }

    private void adjustUIAccordingToManagerState() {
        if (photoManager.isLoading()) {
            if (photoManager.getLoadedPhotos().size() == 0) {
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            photosAdapter.setItems(photoManager.getLoadedPhotos());
        }
    }

    private class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
            implements View.OnClickListener {

        private final static int ITEM_VIEW_TYPE_PHOTO = 0;
        private final static int ITEM_VIEW_TYPE_PROGRESS = 1;

        private final static int NEW_PAGE_REQUEST_THRESHOLD = 10;

        private List<PhotoInfo> items = new ArrayList<>();

        public void setItems(@NonNull List<PhotoInfo> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            switch (viewType) {
                case ITEM_VIEW_TYPE_PHOTO:
                    View view = inflater.inflate(R.layout.raw_grid_photo, parent, false);
                    view.setOnClickListener(this);
                    return new PhotoHolder(view);
                case ITEM_VIEW_TYPE_PROGRESS:
                    return new RecyclerView.ViewHolder(inflater.inflate(R.layout.raw_progress, parent, false)) {
                    };
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == ITEM_VIEW_TYPE_PHOTO) {
                PhotoInfo photoInfo = items.get(position);
                ImageView photo = ((PhotoHolder) holder).photo;
                String imageUrl = photoInfo.getImageUrl();
                Context context = photo.getContext();
                Glide.with(context).load(imageUrl).into(photo);
                if (items.size() - position < NEW_PAGE_REQUEST_THRESHOLD) {
                    photoManager.loadMore();
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position < items.size() ? ITEM_VIEW_TYPE_PHOTO : ITEM_VIEW_TYPE_PROGRESS;
        }

        @Override
        public int getItemCount() {
            return photoManager.hasMoreToLoad() ? items.size() + 1 : items.size();
        }

        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildAdapterPosition(v);
            PhotoViewActivity.start(PhotosGridActivity.this, items.get(itemPosition));
        }
    }

    private static class PhotoHolder extends RecyclerView.ViewHolder {

        private ImageView photo;

        public PhotoHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView;
        }
    }
}
