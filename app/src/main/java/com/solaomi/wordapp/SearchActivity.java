package com.solaomi.wordapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

// Library for Wordnik API (http://developer.wordnik.com/).
import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordsApi;
import net.jeremybrooks.knicker.dto.WordOfTheDay;


public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Kick off an {@link AsyncTask} to perform network request.
        WordOfTheDayAsyncTask task = new WordOfTheDayAsyncTask();
        task.execute();

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

    }

    /**
     * Update the screen to display the Wordnki API's Word-of-the-Day.
     * @param wordOfTheDay is a WordOfTheDay object containing the Word-of-the-Day, including
     *                     definitions and example sentences.
     */
    private void updateUI(WordOfTheDay wordOfTheDay) {

        // Word from wordOfTheDay object.
        String word = wordOfTheDay.getWord();

        // Find reference to the {@link TextView} in the layout and set text to Word-of-the-Day.
        TextView exampleTextView = findViewById(R.id.word_of_the_day);
        exampleTextView.setText(word);
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

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with WordOfTheDay Object.
     */
    private class WordOfTheDayAsyncTask extends AsyncTask<Void, Void, WordOfTheDay> {

        @Override
        protected WordOfTheDay doInBackground(Void... voids) {

            // Set API Key as a system property.
            System.setProperty("WORDNIK_API_KEY", getString(R.string.wordnik_api_key));

            // Perform HTTP request and recieve JSON response back (converted to WordOfTheDay object
            // by Knicker Library)
            try {
                return WordsApi.wordOfTheDay();
            } catch (KnickerException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         *  Update the screen with information from the given given WordOfTheDay object (which is
         *  the result of {@link WordOfTheDayAsyncTask}).
         */
        @Override
        protected void onPostExecute(WordOfTheDay wordOfTheDay) {
            updateUI(wordOfTheDay);
        }

    }
}
