package com.solaomi.wordapp;


import android.content.AsyncTaskLoader;
import android.content.Context;

import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordsApi;
import net.jeremybrooks.knicker.dto.WordOfTheDay;

/**
 * Loads Word-of-the-Day by using an AsyncTask to perform the request
 */
public class WordOfTheDayLoader extends AsyncTaskLoader<WordOfTheDay> {

//    private static final String LOG_TAG = WordOfTheDayLoader.class.getName();
    public WordOfTheDayLoader(Context context) {
        super(context);

        // Set API Key as a system property.
        System.setProperty("WORDNIK_API_KEY", context.getString(R.string.wordnik_api_key));
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * Fetches WordOfTheDay object on a background thread.
     */
    @Override
    public WordOfTheDay loadInBackground() {
        // Perform HTTP request and recieve JSON response back (converted to WordOfTheDay object
        // by Knicker Library)
        try {
            return WordsApi.wordOfTheDay();
        } catch (KnickerException e) {
            e.printStackTrace();
            return null;
        }
    }

}
