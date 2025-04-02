package com.denprog.reservationsystem.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.denprog.reservationsystem.room.dao.AppDao;
import com.denprog.reservationsystem.room.entities.ReservationInfo;
import com.denprog.reservationsystem.room.entities.User;

@Database(version = 1, entities = {User.class, ReservationInfo.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao getAppDao();
}
