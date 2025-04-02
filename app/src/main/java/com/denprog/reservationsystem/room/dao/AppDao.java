package com.denprog.reservationsystem.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.denprog.reservationsystem.room.entities.ReservationInfo;
import com.denprog.reservationsystem.room.entities.User;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    public long insertUser(User user);

    @Query("SELECT * FROM User WHERE email =:email")
    List<User> checkIfEmailAlreadyExists(String email);

    @Insert
    long insertReservationInfo(ReservationInfo reservationInfo);

    @Query("SELECT * FROM ReservationInfo")
    List<ReservationInfo> getAllReservationList();
}
