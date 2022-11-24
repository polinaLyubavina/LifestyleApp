package com.lifestyleapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

//    What is the DAO?
//    A DAO (data access object) validates your SQL at compile-time and associates it with a method.
//    In your Room DAO, you use handy annotations, like @Insert, to represent the most common database operations!
//    Room uses the DAO to create a clean API for your code.
//    The DAO must be an interface or abstract class.
//    By default, all queries must be executed on a separate thread.

@Dao
public interface UserDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE) // Completely replaces any User with the same key
        void insert(User user);

       /*@Query("SELECT * FROM user_table WHERE :fullName == fullName ")  // returns only one row from the table, if there is a match
       LiveData<User> getUser(String name);
*/



     @Query("SELECT * FROM user_table ORDER BY fullName ASC LIMIT 1 ")  // returns only one row from the table, if there is a match

     LiveData<User> getUser();

    @Query("select * from user_table where fullName= :id")
    LiveData<User>  getUserById(String id);

    @RawQuery
    int checkpoint(SupportSQLiteQuery supportSQLiteQuery);

        @Query("UPDATE user_table SET steps=:steps where fullName=:fullName")
        void update(int steps, String fullName);

        @Query("SELECT steps FROM user_table WHERE fullName=:fullName")
        int getSteps(String fullName);

}
