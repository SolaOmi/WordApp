package com.solaomi.wordapp;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordApi;
import net.jeremybrooks.knicker.dto.Example;

import java.util.List;

/**
 * Loads Examples of looked up word by using an AsyncTask to perform the request
 */
public class ExamplesLoader extends AsyncTaskLoader<List<Example>> {

    //    private static final String LOG_TAG = ExamplesLoader.class.getName();
    private String mWord;

    public ExamplesLoader(Context context, String word) {
        super(context);
        mWord = word;

        // Set API Key as a system property.
        System.setProperty("WORDNIK_API_KEY", context.getString(R.string.wordnik_api_key));
    }

    @Override
    protected void onStartLoading() { forceLoad(); }

    @Override
    public List<Example> loadInBackground() {
        // Perform HTTP request and recieve JSON response (converted to a List of Definition
        // objects by Knicker Library) back.
        try {
            return WordApi.examples(mWord).getExamples();
        } catch (KnickerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
