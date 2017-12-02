package com.solaomi.wordapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordsApi;
import net.jeremybrooks.knicker.dto.WordOfTheDay;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        WordOfTheDayAsyncTask task = new WordOfTheDayAsyncTask();
        task.execute();
    }

    private void updateUI(WordOfTheDay wordObj) {

        String word = "example";

        if ( wordObj != null ) { word = wordObj.getWord(); }

        TextView exampleTextView = (TextView) findViewById(R.id.example);
        exampleTextView.setText(word);
    }

    private class WordOfTheDayAsyncTask extends AsyncTask<Void, Void, WordOfTheDay> {

        @Override
        protected WordOfTheDay doInBackground(Void... voids) {

            System.setProperty("WORDNIK_API_KEY", getString(R.string.wordnik_api_key));

            try {
                return WordsApi.wordOfTheDay();
            } catch (KnickerException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(WordOfTheDay wordObj) {
            updateUI(wordObj);
        }

    }
}
