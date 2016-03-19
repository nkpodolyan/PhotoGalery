package com.mpodolian.photogallery.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("lastname")
    private String lastname;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("userpic_url")
    private String userpicUrl;

    @SerializedName("userpic_https_url")
    private String userpicHttpsUrl;

    @SerializedName("cover_url")
    private String coverUrl;

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.fullname = in.readString();
        this.userpicUrl = in.readString();
        this.userpicHttpsUrl = in.readString();
        this.coverUrl = in.readString();
        this.followersCount = in.readInt();
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUserpicUrl() {
        return userpicUrl;
    }

    public String getUserpicHttpsUrl() {
        return userpicHttpsUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public int followersCount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.firstname);
        dest.writeString(this.lastname);
        dest.writeString(this.fullname);
        dest.writeString(this.userpicUrl);
        dest.writeString(this.userpicHttpsUrl);
        dest.writeString(this.coverUrl);
        dest.writeInt(this.followersCount);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
