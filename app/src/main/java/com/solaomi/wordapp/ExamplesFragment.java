package com.solaomi.wordapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.Example;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamplesFragment extends Fragment {

//    private static final String LOG_TAG = ExamplesFragment.class.getName();
    private static final int EXAMPLES_LOADER_ID = 1;
    private String mWord;

    public ExamplesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mWord = bundle.getString("word");
        }

        final TextView textView = new TextView(getActivity());

        // Implement a loadercallback for the Examples loader.
        LoaderCallbacks<List<Example>> examplesLoaderListener =
                new LoaderCallbacks<List<Example>>() {
                    @Override
                    public Loader<List<Example>> onCreateLoader(int id, Bundle args) {
                        return new ExamplesLoader(getContext(), mWord);
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Example>> loader, List<Example> data) {
                        String text;
                        if (data.size() == 0) {
                            text = getString(R.string.examples_fragment);
                        } else {
                            text = data.get(0).getText();
                        }
                        textView.setText(text);
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Example>> loader) { mWord = ""; }
                };

        // A reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in the examplesLoaderListener for the LoaderCallbacks parameter.
        loaderManager.initLoader(EXAMPLES_LOADER_ID, null, examplesLoaderListener);

        return textView;
    }

}
