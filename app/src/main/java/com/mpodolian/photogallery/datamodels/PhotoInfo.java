package com.mpodolian.photogallery.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PhotoInfo implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("camera")
    private String camera;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("user")
    private User user;

    public PhotoInfo() {
    }

    protected PhotoInfo(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.camera = in.readString();
        this.imageUrl = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.licensingRequested = in.readByte() != 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getCamera() {
        return camera;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public User getUser() {
        return user;
    }

    public boolean isLicensingRequested() {
        return licensingRequested;
    }

    public boolean licensingRequested;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.camera);
        dest.writeString(this.imageUrl);
        dest.writeParcelable(this.user, flags);
        dest.writeByte(licensingRequested ? (byte) 1 : (byte) 0);
    }

    public static final Parcelable.Creator<PhotoInfo> CREATOR = new Parcelable.Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel source) {
            return new PhotoInfo(source);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };
}
