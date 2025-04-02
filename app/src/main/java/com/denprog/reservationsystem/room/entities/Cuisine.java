package com.denprog.reservationsystem.room.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cuisine implements Parcelable {
    public String cuisineName;
    public float cuisinePrice;

    public Cuisine(String cuisineName, float cuisinePrice) {
        this.cuisineName = cuisineName;
        this.cuisinePrice = cuisinePrice;
    }

    protected Cuisine(Parcel in) {
        cuisineName = in.readString();
        cuisinePrice = in.readFloat();
    }

    public static final Creator<Cuisine> CREATOR = new Creator<Cuisine>() {
        @Override
        public Cuisine createFromParcel(Parcel in) {
            return new Cuisine(in);
        }

        @Override
        public Cuisine[] newArray(int size) {
            return new Cuisine[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(cuisineName);
        parcel.writeFloat(cuisinePrice);
    }
}
