package com.denprog.reservationsystem.room.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public long userId;
    public String firstName;
    public String middleName;
    public String lastName;
    public String username;
    public String password;
    public String roleCode;
    public String email;
    public String profilePath;
}
