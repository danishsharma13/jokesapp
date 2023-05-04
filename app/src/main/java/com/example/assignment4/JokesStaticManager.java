package com.example.assignment4;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JokesStaticManager {

    // Declare static private variables
    static private ArrayList<Joke> jokesList = new ArrayList<Joke>(0);

    static private ArrayList<Joke> savedJokesList = new ArrayList<Joke>(0);

    // Setter - jokesList
    static public void setJokesList(ArrayList<Joke> jl) {
        jokesList = jl;
    }

    // Getter - jokesList
    static public ArrayList<Joke> getJokesList() {
        return jokesList;
    }

    // Clear - jokesList
    static public void clearJokesList() {
        jokesList.clear();
    }

    // Setter - savedJokesList
    static public void setSavedJokesList(ArrayList<Joke> jl) {
        savedJokesList = jl;
    }

    // Getter - savedJokesList
    static public ArrayList<Joke> getSavedJokesList() {
        return savedJokesList;
    }

    // Clear - jokesList
    static public void clearSavedJokesList() {
        savedJokesList.clear();
    }
}
