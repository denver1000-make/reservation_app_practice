package com.denprog.reservationsystem.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.denprog.reservationsystem.room.entities.User;

@Dao
public interface AppDao {
    @Insert
    public long insertUser(User user);
}
