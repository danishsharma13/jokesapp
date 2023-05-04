package com.example.assignment4;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Joke.class}, version = 1)
public abstract class JokesDatabase extends RoomDatabase {
    // Declaring getDao abstract method
    public abstract JokesDAO getDao();
}
