package com.example.assignment4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {

    // Callback listener
    interface JokesClickListener{
        void onJokeClicked(Joke selectedJoke);
    }

    public JokesClickListener jokeListener;

    // Declaring private variables for JokeAdapter class
    private Context context;
    private ArrayList<Joke> jokesList = new ArrayList<Joke>(0);

    public JokeAdapter(Context context, ArrayList<Joke> jokesList) {
        this.context = context;
        this.jokesList = jokesList;
    }

    // Setter - jokesList
    public void setJokesList(ArrayList<Joke> jokesList) {
        this.jokesList = jokesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view with the data list
        View v = LayoutInflater.from(context).inflate(R.layout.joke_row, parent, false);
        ViewHolder vh = new ViewHolder(v, context);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Insert information by using setText
        Joke joke = jokesList.get(position);
        holder.jokeCategory.setText(joke.getCategory());
        holder.jokeFlags.setText(
                "NSFW: " + joke.isNsfw()
                        + " | POLITICAL: " + joke.isPolitical()
                        + " | EXPLICIT: " + joke.isExplicit());
        holder.jokeJoke.setText(joke.getJoke());
    }

    @Override
    public int getItemCount() {
        return jokesList.size();
    }

    // JokeViewHolder class needs to be initialized
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Declaring private variables for the JokeViewHolder class
        //private LinearLayout jokeLinearLayout;
        private TextView jokeCategory;
        private TextView jokeFlags;
        private TextView jokeJoke;

        // 1-args Constructor
        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            // Find LinearLayout by Id
            //jokeLinearLayout = (LinearLayout) itemView.findViewById(R.id.jokeLinearLayout);
            // Find TextView by Id
            jokeCategory = (TextView) itemView.findViewById(R.id.categoryTextView);
            jokeFlags = (TextView) itemView.findViewById(R.id.flagsTextView);
            jokeJoke = (TextView) itemView.findViewById(R.id.jokeTextView);

            // SetOnClickListener to check if the itemView has been pressed.
            itemView.setOnClickListener(view -> {

                // Getting current joke
                Joke currentJoke = jokesList.get(getAdapterPosition());
                jokeListener.onJokeClicked(currentJoke);
            });
        }
    }
}
