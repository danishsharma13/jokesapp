package com.example.assignment4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        SearchJokeFragment.OnInputListener,
        NetworkingService.OnNetworkingListener
{

    // Activity Ids Buttons, Fragment Array
    // Fragment Manager
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Button searchButton;
    private Button savedButton;

    // Networking Service
    NetworkingService ns = new NetworkingService();

    // Joke Adapter Listener
    JokeAdapter jokeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding buttons by id
        searchButton = (Button) findViewById(R.id.searchButton);
        savedButton = (Button) findViewById(R.id.savedButton);

        // Networking Service Listener
        ns.networkJSONResult = this;

        // Setting searchButton listener
        searchButton.setOnClickListener(view -> {
            SearchJokeFragment searchJokeFragment = SearchJokeFragment.newInstance();

            searchJokeFragment.show(fragmentManager, "searchJokeFragment");
        });

        // Setting savedButton listener
        savedButton.setOnClickListener(view -> {
            //Toast.makeText(this, "Saved Button Clicked!", Toast.LENGTH_SHORT).show();
            Intent savedJokesIntent = new Intent(this, SavedJokeRecyclerViewActivity.class);
            startActivity(savedJokesIntent);
        });
    }

    @Override
    public void sendInput(String queryParam, boolean nsfwParam, boolean politicalParam, boolean explicitParam) {
        //Log.d("SEND INPUT", queryParam + " " + nsfwParam + " " + politicalParam + " " + explicitParam);
        //Toast.makeText(this, "sendInput from Main!", Toast.LENGTH_SHORT).show();
        //ns.connect("https://v2.jokeapi.dev/joke/Any?amount=10&type=single&blacklistFlags=religious,sexist,racist&contains=");
        ns.getJokes(queryParam, nsfwParam, politicalParam, explicitParam);
    }

    @Override
    public void jokeJSONResult(String json) {
        //Log.d("JSON from Main", json);
        //Toast.makeText(this, "json from Main!", Toast.LENGTH_SHORT).show();

        // First clear the previous jokes
        JokesStaticManager.clearJokesList();

        // Get the arraylist of jokes from json service
        ArrayList<Joke> jokesList = JsonService.getJokesFromJSONString(json);

        //Log.d("JokeJsonResult: ", jokesList.get(0).getJoke());

        // Set the jokesList in Manager
        JokesStaticManager.setJokesList(jokesList);

        // StartActivity with intent for JokeRecyclerViewActivity
        Intent jokesIntent = new Intent(this, JokeRecyclerViewActivity.class);
        startActivity(jokesIntent);
    }
}