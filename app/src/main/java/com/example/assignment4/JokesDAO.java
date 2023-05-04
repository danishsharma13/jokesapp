package com.example.assignment4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface JokesDAO {

    // To get all the jokes from the database
    @Query("select * from Joke")
    Joke[] getAllJokes();

    // To insert a new joke to the database
    @Insert
    void insertJoke(Joke j);

    // To delete a joke from the database
    @Delete
    void deleteJoke(Joke j);
}
