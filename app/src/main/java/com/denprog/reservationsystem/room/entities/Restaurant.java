package com.denprog.reservationsystem.room.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Parcelable {

    public String restaurantName;
    public float restaurantPrice;
    public Cuisine[] restaurantCuisineList;

    public Restaurant(String restaurantName, float restaurantPrice, Cuisine[] restaurantCuisineList) {
        this.restaurantName = restaurantName;
        this.restaurantPrice = restaurantPrice;
        this.restaurantCuisineList = restaurantCuisineList;
    }

    protected Restaurant(Parcel in) {
        restaurantName = in.readString();
        restaurantPrice = in.readFloat();
        restaurantCuisineList = in.createTypedArray(Cuisine.CREATOR);
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public static List<Restaurant> generateRestaurantList() {
        List<Restaurant> restaurants = new ArrayList<>();
        List<Cuisine> cuisines = new ArrayList<>();
        Cuisine[] cuisineArray = new Cuisine[]{new Cuisine("Asian", 3000), new Cuisine("Mexican", 3000)};
        restaurants.add(new Restaurant("Nx's", 300, cuisineArray));
        restaurants.add(new Restaurant("Mx's", 300, cuisineArray));
        restaurants.add(new Restaurant("Ox's", 300, cuisineArray));
        restaurants.add(new Restaurant("Px's", 300, cuisineArray));
        return restaurants;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(restaurantName);
        parcel.writeFloat(restaurantPrice);
        parcel.writeTypedArray(restaurantCuisineList, i);
    }
}
