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

    public User(String firstName, String middleName, String lastName, String username, String password, String roleCode) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roleCode = roleCode;
    }
}
