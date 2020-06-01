package com.example.vikashkumar.Database;

import androidx.annotation.ColorInt;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "aadharId")
    private String AadharId;

    @ColumnInfo(name = "name")
    private String Name;

    @ColumnInfo(name = "mobileNumber")
    private String mobileNumber;

    @ColumnInfo(name = "emailId")
    private String emailId;

    @ColumnInfo(name = "password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAadharId() {
        return AadharId;
    }

    public void setAadharId(String aadharId) {
        AadharId = aadharId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emialId) {
        this.emailId = emialId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
