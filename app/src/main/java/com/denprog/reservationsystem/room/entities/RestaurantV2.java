package com.denprog.reservationsystem.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public
class RestaurantV2 {
    @PrimaryKey(autoGenerate = true)
    public long restaurantId;
    public String restaurantName;
    public float restaurantPrice;

}
