package com.example.vikashkumar.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Userdao {

    @Query("SELECT * FROM User")
    List<User>  getUser();

    @Query("SElect password from User where emailId = :username  or aadharId =:username or mobileNumber =:username")
    String getPassword(String username);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}
