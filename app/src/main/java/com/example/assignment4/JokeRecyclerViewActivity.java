package com.example.assignment4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class JokeRecyclerViewActivity extends AppCompatActivity implements
        JokeAdapter.JokesClickListener,
        RoomDBManager.onRoomDBListener {

    // Declaring private variables for the JokeRecyclerViewActivity class
    private RecyclerView recyclerViewJoke;
    private JokeAdapter jokeAdapter;
    private ArrayList<Joke> jokesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_recycler_view);

        // Setting Database
        ((MyApp)getApplication()).roomDBManager.roomDBListener = this;
        ((MyApp)getApplication()).roomDBManager.getDB(this);

        // Setting JokeAdapter
        jokeAdapter = new JokeAdapter(this, JokesStaticManager.getJokesList());

        // Adapter listener
        jokeAdapter.jokeListener = this;

        // Find views by Id
        recyclerViewJoke = (RecyclerView) findViewById(R.id.jokesListRecyclerView);
        recyclerViewJoke.setLayoutManager(new LinearLayoutManager(this));

        // Setup recycler view with adapter and notify
        recyclerViewJoke.setAdapter(jokeAdapter);
        jokeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getAllJokesResult(Joke[] j) {
        jokesList = new ArrayList<Joke>(Arrays.asList(j));
        JokesStaticManager.clearSavedJokesList();
        JokesStaticManager.setSavedJokesList(jokesList);
    }

    @Override
    public void insertJokeResult() {
        ((MyApp)getApplication()).roomDBManager.getAllJokes();
    }

    @Override
    public void deleteJokeResult() {
    }

    @Override
    public void onJokeClicked(Joke selectedJoke) {

        Log.d("OnJokeClicked: ", selectedJoke.getJoke());

        // Create Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to store this joke in your database?");

        // If pressed no
        builder.setNegativeButton("No",null);

        // If pressed yes
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // Saving Joke in the database
                ((MyApp)getApplication()).roomDBManager.insertJoke(selectedJoke);
            }
        });

        // Build the alert
        builder.create().show();
    }
}