package com.example.assignment4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {

    // Getter Method to get Jokes from JSON string
    public static ArrayList<Joke> getJokesFromJSONString (String json) {
        // Create a variable to store the jokes in
        ArrayList<Joke> jokesList = new ArrayList<>(0);

        // JSON Parsing
        try {
            JSONObject jokeJsonObject = new JSONObject(json);
            boolean errorResponse = jokeJsonObject.getBoolean("error");
            int amount = jokeJsonObject.getInt("amount");
            JSONArray jokesArray = jokeJsonObject.getJSONArray("jokes");

            // If error response is false then we have data from the api
            if(!errorResponse) {
                // loop through the jokes
                for(int i = 0; i < amount; i++) {
                    JSONObject currentObject = jokesArray.getJSONObject(i);
                    String category = currentObject.getString("category");
                    String jokeString = currentObject.getString("joke");
                    JSONObject flag = currentObject.getJSONObject("flags");
                    boolean nsfw = flag.getBoolean("nsfw");
                    boolean political = flag.getBoolean("political");
                    boolean explicit = flag.getBoolean("explicit");

                    Joke joke = new Joke(category, jokeString, nsfw, political, explicit);
                    jokesList.add(joke);
                }
            }
            else {
                // Creating a default Joke
                Joke joke = new Joke("Any",
                        "This is a default joke since your query is not found, Knock knock...",
                        false, false, false);
                jokesList.add(joke);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jokesList;
    }
}
