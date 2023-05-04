package com.example.assignment4;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

public class RoomDBManager {

    // Callback Listeners
    interface onRoomDBListener {
        void getAllJokesResult(Joke[] j);
        void insertJokeResult();
        void deleteJokeResult();
    }

    onRoomDBListener roomDBListener;

    // Declare private variables
    JokesDatabase jokeRoomDB;

    // Main Thread Handler
    Handler handler = new Handler(Looper.getMainLooper());

    // Getting room database
    public JokesDatabase getDB(Context context) {
        // If jokeRoomDB is null then we initialize the database
        if(jokeRoomDB == null) {
            jokeRoomDB = Room.databaseBuilder(context,
                    JokesDatabase.class, "database-jokes").build();
        }

        // Return the jokeRoomDB
        return jokeRoomDB;
    }

    void getAllJokes() {
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                Joke[] jokesArray = jokeRoomDB.getDao().getAllJokes();

                // Main Thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        roomDBListener.getAllJokesResult(jokesArray);
                    }
                });
            }
        });
    }

    void insertJoke(Joke j) {
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                jokeRoomDB.getDao().insertJoke(j);

                // Main Thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        roomDBListener.insertJokeResult();
                    }
                });
            }
        });
    }

    void deleteJoke(Joke j) {
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                jokeRoomDB.getDao().deleteJoke(j);

                // Main Thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        roomDBListener.deleteJokeResult();
                    }
                });
            }
        });
    }

}
