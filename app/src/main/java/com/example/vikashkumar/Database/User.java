package com.example.vikashkumar.Database;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"aadharId", "mobileNumber","emailId","token"},
        unique = true)})
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="token")
    private String token;

    @NonNull
    @ColumnInfo(name = "aadharId")
    private String AadharId;



    @ColumnInfo(name = "name")
    private String Name;

    @ColumnInfo(name = "address")
    private String Address;

    @NonNull
    @ColumnInfo(name = "mobileNumber")
    private String mobileNumber;

    @NonNull
    @ColumnInfo(name = "emailId")
    private String emailId;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "pincode")
    private String PinCode;

    public int getId() {
        return id;
    }

    public String getToken(){
        return token;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }
}
