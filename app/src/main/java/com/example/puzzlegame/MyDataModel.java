package com.example.puzzlegame;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class MyDataModel implements Parcelable, Serializable {

    public static final Creator<MyDataModel> CREATOR = new Creator<MyDataModel>() {
        @Override
        public MyDataModel createFromParcel(Parcel in) {
            return new MyDataModel(in);
        }

        @Override
        public MyDataModel[] newArray(int size) {
            return new MyDataModel[size];
        }
    };
    int id;
    String name;
    String image;

    public MyDataModel(int id, String name, String image) {
        this.id = id;

        this.name = name;
        this.image = image;
    }

    protected MyDataModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
    }
}
