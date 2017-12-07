package com.solaomi.wordapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.WordOfTheDay;

public class SearchActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<WordOfTheDay> {

     /** Tag for log messages */
    private static final String LOG_TAG = SearchActivity.class.getName();

    /** Constant value for the Word of the Day loader ID. */
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

        // A reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(WORD_OF_THE_DAY_LOADER_ID, null, this);
    }

    /**
     * Update the screen to display the Wordnki API's Word-of-the-Day.
     * @param wordOfTheDay is a WordOfTheDay object containing the Word-of-the-Day, including
     *                     definitions and example sentences.
     */
    private void updateUI(WordOfTheDay wordOfTheDay) {

        // Word from wordOfTheDay object.
        mWordOfTheDay= wordOfTheDay.getWord();

        // Definition from wordOfTheDay object.
        mWordOfTheDayDefinition = wordOfTheDay.getDefinitions().get(0).getText();

        // Example from wordOfTheDay object.
        mWordOfTheDayExample = wordOfTheDay.getExamples().get(0).getText();

        // Find reference to the {@link TextView} in the layout and set text to Word-of-the-Day.
        TextView wordTextView = findViewById(R.id.word_of_the_day);
        wordTextView.setText(mWordOfTheDay);

        // Find reference to the {@link TextView} in the layout and set text to Word-of-the-Day's
        // first definition.
        TextView definitionTextView = findViewById(R.id.word_of_the_day_definition);
        definitionTextView.setText(mWordOfTheDayDefinition);

        // Find reference to the {@link TextView} in the layout and set text to Word-of-the-Day's
        // first example.
        TextView exampleTextView = findViewById(R.id.word_of_the_day_example);
        exampleTextView.setText(mWordOfTheDayExample);
    }

    /**
     * Helper class to start the Word Activity.
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

    @Override
    public Loader<WordOfTheDay> onCreateLoader(int i, Bundle bundle) {
        return new WordOfTheDayLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<WordOfTheDay> loader, WordOfTheDay wordOfTheDay) {
        updateUI(wordOfTheDay);
    }

    @Override
    public void onLoaderReset(Loader<WordOfTheDay> loader) {
        // Loader reset, so we can clear out existing data.
        mWordOfTheDay = "";
        mWordOfTheDayDefinition = "";
        mWordOfTheDayExample = "";
    }
}
