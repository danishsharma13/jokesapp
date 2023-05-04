package com.example.assignment4;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Joke {
    // Auto Generated id for database
    @PrimaryKey(autoGenerate = true)
    int id;

    // Private class variables
    private String category;
    private String joke;
    private boolean nsfw;
    private boolean political;
    private boolean explicit;

    // 5 Args Constructor
    public Joke(String category, String joke, boolean nsfw, boolean political, boolean explicit) {
        this.category = category;
        this.joke = joke;
        this.nsfw = nsfw;
        this.political = political;
        this.explicit = explicit;
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public String getJoke() {
        return joke;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public boolean isPolitical() {
        return political;
    }

    public boolean isExplicit() {
        return explicit;
    }
}
