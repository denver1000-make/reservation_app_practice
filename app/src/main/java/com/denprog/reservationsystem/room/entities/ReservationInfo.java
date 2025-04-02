package com.denprog.reservationsystem.room.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReservationInfo implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long reservationInfo;
    public String firstName;
    public String middleName;
    public String lastName;
    public String restaurantName;
    public float restaurantPrice;
    public String cuisineName;
    public float cuisinePrice;
    public int numOfAttendingDiners;
    public String reservationDate;
    public String reservationStart;
    public String reservationEnd;
    public float reservationPrice;

    public ReservationInfo(String firstName, String middleName, String lastName, String restaurantName, float restaurantPrice, String cuisineName, float cuisinePrice, int numOfAttendingDiners, String reservationDate, String reservationStart, String reservationEnd, float reservationPrice) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.restaurantName = restaurantName;
        this.restaurantPrice = restaurantPrice;
        this.cuisineName = cuisineName;
        this.cuisinePrice = cuisinePrice;
        this.numOfAttendingDiners = numOfAttendingDiners;
        this.reservationDate = reservationDate;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        this.reservationPrice = reservationPrice;
    }

    protected ReservationInfo(Parcel in) {
        reservationInfo = in.readLong();
        firstName = in.readString();
        middleName = in.readString();
        lastName = in.readString();
        restaurantName = in.readString();
        restaurantPrice = in.readFloat();
        cuisineName = in.readString();
        cuisinePrice = in.readFloat();
        numOfAttendingDiners = in.readInt();
        reservationDate = in.readString();
        reservationStart = in.readString();
        reservationEnd = in.readString();
        reservationPrice = in.readFloat();
    }

    public static final Creator<ReservationInfo> CREATOR = new Creator<ReservationInfo>() {
        @Override
        public ReservationInfo createFromParcel(Parcel in) {
            return new ReservationInfo(in);
        }

        @Override
        public ReservationInfo[] newArray(int size) {
            return new ReservationInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(reservationInfo);
        parcel.writeString(firstName);
        parcel.writeString(middleName);
        parcel.writeString(lastName);
        parcel.writeString(restaurantName);
        parcel.writeFloat(restaurantPrice);
        parcel.writeString(cuisineName);
        parcel.writeFloat(cuisinePrice);
        parcel.writeInt(numOfAttendingDiners);
        parcel.writeString(reservationDate);
        parcel.writeString(reservationStart);
        parcel.writeString(reservationEnd);
        parcel.writeFloat(reservationPrice);
    }
}
