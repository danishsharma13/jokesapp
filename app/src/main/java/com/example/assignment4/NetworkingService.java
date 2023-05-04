package com.example.assignment4;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {

    // Callback Listener
    interface OnNetworkingListener {
        void jokeJSONResult(String json);
    }

    // OnNetworkingListener instance
    public OnNetworkingListener networkJSONResult;

    // Declaring private class variables
    private String jokeAPIUrl = "https://v2.jokeapi.dev/joke/Any?format=json&amount=10&type=single&blacklistFlags=religious,sexist,racist";
    private String jokeContainsUrl = "&contains=";

    // Threads
    ExecutorService networkingExecutorService = Executors.newFixedThreadPool(4);
    Handler networkingHandler = new Handler(Looper.getMainLooper());

    // Default Constructor
    public NetworkingService() {}

    // getJokes Method
    public void getJokes(String query, boolean nsfw, boolean political, boolean explicit) {
        // Setting url parameters
        if(!nsfw) {
            jokeAPIUrl += ",nsfw";
        }

        if(!political) {
            jokeAPIUrl += ",political";
        }

        if(!explicit) {
            jokeAPIUrl += ",explicit";
        }

        if(query != "" && query != " ") {
            jokeContainsUrl += query;
            jokeAPIUrl += jokeContainsUrl;
        }

        connect(jokeAPIUrl);
    }

    // Connection method
    public void connect(String connectionString) {
        networkingExecutorService.execute(new Runnable() {
            // HTTP URL Connection
            HttpURLConnection urlConnection;

            @Override
            public void run() {
                // run in background thread
                try {
                    URL url = new URL(connectionString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);

                    int value = 0;
                    String jsonString = "";
                    while ((value = reader.read()) != -1) {
                        char current = (char) value;
                        jsonString += current;
                    }

                    final String json = jsonString;

                    // we need to comeback to main thread when done.
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            networkJSONResult.jokeJSONResult(json);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }
        });
    }
}
