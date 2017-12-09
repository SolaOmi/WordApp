package com.solaomi.wordapp;


import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordApi;
import net.jeremybrooks.knicker.dto.Definition;

import java.util.List;

/**
 * Loads Definitions of looked up word by using an AsyncTask to perform the request
 */
public class DefinitionsLoader extends AsyncTaskLoader<List<Definition>> {

    //    private static final String LOG_TAG = DefinitionsLoader.class.getName();
    private String mWord;

    public DefinitionsLoader(Context context, String word) {
        super(context);
        mWord = word;

        // Set API Key as a system property.
        System.setProperty("WORDNIK_API_KEY", context.getString(R.string.wordnik_api_key));
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Definition> loadInBackground() {
        // Perform HTTP request and recieve JSON response (converted to a List of Definition
        // objects by Knicker Library) back.
        try {
            return WordApi.definitions(mWord);
        } catch (KnickerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
