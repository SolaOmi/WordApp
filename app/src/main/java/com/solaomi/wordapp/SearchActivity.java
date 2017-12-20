package com.solaomi.wordapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.WordOfTheDay;

public class SearchActivity extends AppCompatActivity {

//    private static final String LOG_TAG = SearchActivity.class.getName();
    private static final int WORD_OF_THE_DAY_LOADER_ID = 1;
    private String mWordOfTheDay;
    private String mWordOfTheDayDefinition;
    private String mWordOfTheDayExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Find View that shows Word-of-the-Day.
        final TextView wordOfTheDayTextView = findViewById(R.id.word_of_the_day);

        // Set a click listener on Word-of-the-Day TextView.
        wordOfTheDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = wordOfTheDayTextView.getText().toString();
                startWordActivity(word);
            }
        });

        // Find SearchView for word lookup.
        SearchView wordLookupSearchView = findViewById(R.id.word_search_view);

        // Set a query text listener on the word SearchView.
        wordLookupSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String word) {
                startWordActivity(word);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        // Implement a loadercallback for the Word-of-the-Day loader.
        LoaderCallbacks<WordOfTheDay> wordOfTheDayLoaderListener =
                new LoaderCallbacks<WordOfTheDay>() {
                    @Override
                    public Loader<WordOfTheDay> onCreateLoader(int i, Bundle bundle) {
                        return new WordOfTheDayLoader(SearchActivity.this);
                    }

                    @Override
                    public void onLoadFinished(Loader<WordOfTheDay> loader, WordOfTheDay wordOfTheDay) {
                        updateUI(wordOfTheDay, true);
                    }

                    @Override
                    public void onLoaderReset(Loader<WordOfTheDay> loader) {
                        // Loader reset, so we can clear out existing data.
                        mWordOfTheDay = "";
                        mWordOfTheDayDefinition = "";
                        mWordOfTheDayExample = "";
                    }
                };

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            // A reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in the wordOfTheDayLoaderListener for the LoaderCallbacks parameter.
            loaderManager.initLoader(WORD_OF_THE_DAY_LOADER_ID, null,
                    wordOfTheDayLoaderListener);
        } else {
            updateUI(null, false);
        }
    }

    /**
     * Update the screen to display the relevant information depending on the network state.
     *
     * @param wordOfTheDay is a WordOfTheDay object containing the Word-of-the-Day, including
     *                     definitions and example sentences.
     */
    private void updateUI(WordOfTheDay wordOfTheDay, boolean isConnected) {
        // Find reference to the {@link TextView}'s.
        TextView wordTextView = findViewById(R.id.word_of_the_day);
        TextView definitionTextView = findViewById(R.id.word_of_the_day_definition);
        TextView exampleTextView = findViewById(R.id.word_of_the_day_example);

        if (isConnected && wordOfTheDay != null) {
            // Word from wordOfTheDay object.
            mWordOfTheDay = wordOfTheDay.getWord();

            // Definition from wordOfTheDay object.
            mWordOfTheDayDefinition = wordOfTheDay.getDefinitions().get(0).getText();

            // Example from wordOfTheDay object.
            mWordOfTheDayExample = wordOfTheDay.getExamples().get(0).getText();

            // Set text to the {@link TextView}'s
            wordTextView.setText(mWordOfTheDay);
            definitionTextView.setText(mWordOfTheDayDefinition);
            exampleTextView.setText(mWordOfTheDayExample);
        } else {
            // Disable Views
            wordTextView.setVisibility(View.GONE);
            definitionTextView.setVisibility(View.GONE);
            exampleTextView.setVisibility(View.GONE);

            SearchView wordLookupSearchView = findViewById(R.id.word_search_view);
            wordLookupSearchView.setVisibility(View.GONE);

            TextView wordOfTheDayHeader = findViewById(R.id.word_of_the_day_header);
            wordOfTheDayHeader.setVisibility(View.GONE);

            // Update empty state with no connection error message
            TextView emptyStateTextView = findViewById(R.id.activity_search_empty_view);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    /**
     * Helper class to start the Word Activity.
     *
     * @param word is a String of the word to be looked up in the Word Activity.
     */
    private void startWordActivity(String word) {
        // Create a new intent to open the {@link WordActivity}
        Intent wordIntent = new Intent(SearchActivity.this, WordActivity.class);

        // Pass word to the new Activity.
        wordIntent.putExtra("word", word);

        // Start the new activity
        startActivity(wordIntent);
    }
}
