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

public class SavedJokeRecyclerViewActivity extends AppCompatActivity implements
        JokeAdapter.JokesClickListener,
        RoomDBManager.onRoomDBListener {

    // Declaring private variables for the JokeRecyclerViewActivity class
    private RecyclerView recyclerViewJoke;
    private JokeAdapter jokeAdapter;
    private ArrayList<Joke> jokesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_joke_recycler_view);

        // Setting Database
        ((MyApp)getApplication()).roomDBManager.roomDBListener = this;
        ((MyApp)getApplication()).roomDBManager.getDB(this);
        ((MyApp)getApplication()).roomDBManager.getAllJokes();

        // Setting JokeAdapter
        jokeAdapter = new JokeAdapter(this, JokesStaticManager.getSavedJokesList());

        // Adapter listener
        jokeAdapter.jokeListener = this;

        // Find views by Id
        recyclerViewJoke = (RecyclerView) findViewById(R.id.savedJokesListRecyclerView);
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
        jokeAdapter.setJokesList(JokesStaticManager.getSavedJokesList());
        jokeAdapter.notifyDataSetChanged();
    }

    @Override
    public void insertJokeResult() {

    }

    @Override
    public void deleteJokeResult() {
        ((MyApp)getApplication()).roomDBManager.getAllJokes();
    }

    @Override
    public void onJokeClicked(Joke selectedJoke) {

        Log.d("OnJokeClickedSaved: ", selectedJoke.getJoke());

        // Create Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to delete this joke from your database?");

        // If pressed no
        builder.setNegativeButton("No",null);

        // If pressed yes
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // Deleting Joke in the database
                ((MyApp)getApplication()).roomDBManager.deleteJoke(selectedJoke);
            }
        });

        // Build the alert
        builder.create().show();
    }
}