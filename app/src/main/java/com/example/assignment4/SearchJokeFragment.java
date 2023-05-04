package com.example.assignment4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link SearchJokeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchJokeFragment extends DialogFragment {

    private static final String TAG = "searchJokeFragment";

    // Interface to tell Main Activity about the Search Query
    public interface OnInputListener {
        void sendInput(String queryParam, boolean nsfwParam,
                       boolean politicalParam, boolean explicitParam);
    }

    public OnInputListener onInputListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String insertQueryParam1 = "param1";
    private static final String nsfwParam2 = "param2";
    private static final String politicalParam3 = "param3";
    private static final String explicitParam4 = "param4";

    // TODO: Rename and change types of parameters
    private String insertQuery;
    private boolean nsfw;
    private boolean political;
    private boolean explicit;

    public SearchJokeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    /* public static SearchJokeFragment newInstance(String queryParam, boolean nsfwParam,
                                                 boolean politicalParam, boolean explicitParam) {
        SearchJokeFragment fragment = new SearchJokeFragment();
        Bundle args = new Bundle();
        args.putString(insertQueryParam1, queryParam);
        args.putBoolean(nsfwParam2, nsfwParam);
        args.putBoolean(politicalParam3, politicalParam);
        args.putBoolean(explicitParam4, explicitParam);
        fragment.setArguments(args);
        return fragment;
    } */

    public static SearchJokeFragment newInstance() {
        SearchJokeFragment fragment = new SearchJokeFragment();
        return fragment;
    }

    /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            insertQuery = getArguments().getString(insertQueryParam1);
            nsfw = getArguments().getBoolean(nsfwParam2);
            political = getArguments().getBoolean(politicalParam3);
            explicit = getArguments().getBoolean(explicitParam4);
        }
    } */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View dialogFragment = inflater.inflate(R.layout.fragment_search_joke, container, false);

        // Getting all the UI components
        EditText searchQueryInputEditText = (EditText) dialogFragment.findViewById(R.id.queryInput);
        Switch nsfwInputSwitch = (Switch) dialogFragment.findViewById(R.id.nsfwSwitch);
        Switch politicalInputSwitch = (Switch) dialogFragment.findViewById(R.id.politicalSwitch);
        Switch explicitInputSwitch = (Switch) dialogFragment.findViewById(R.id.explicitSwitch);
        Button searchButton = (Button) dialogFragment.findViewById(R.id.searchFragmentButton);
        Button cancelButton = (Button) dialogFragment.findViewById(R.id.cancelFragmentButton);

        // Setting UI components default values
        searchQueryInputEditText.setText(" ");
        nsfwInputSwitch.setChecked(true);
        politicalInputSwitch.setChecked(true);
        explicitInputSwitch.setChecked(true);

        // Set search button to use callback function
        searchButton.setOnClickListener(view -> {
            // get the data from the UI
            String getQueryInput = searchQueryInputEditText.getText().toString();
            boolean getNsfwInput = nsfwInputSwitch.isChecked();
            boolean getPoliticalInput = politicalInputSwitch.isChecked();
            boolean getExplicitInput = explicitInputSwitch.isChecked();

            // Use callback to send the updated data
            onInputListener.sendInput(getQueryInput, getNsfwInput, getPoliticalInput, getExplicitInput);

            // dismiss the dialog
            getDialog().dismiss();

        });

        // CANCEL button pressed
        cancelButton.setOnClickListener(view -> {
            getDialog().dismiss();
        });

        return dialogFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getActivity();
        } catch(ClassCastException error) {
            Log.d(TAG, "onAttach: " + error.getMessage());
        }
    }
}