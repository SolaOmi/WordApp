package com.solaomi.wordapp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamplesFragment extends Fragment {

//    private static final String LOG_TAG = ExamplesFragment.class.getName();
    private static final int EXAMPLES_LOADER_ID = 1;
    private String mWord;
    private ArrayAdapter<String> mAdapter;

    public ExamplesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.word_list, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mWord = bundle.getString("word");
        }

        // Create a new adapter that takes an empty list of Strings as input.
        mAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                new ArrayList<String>()
        );

        // Implement a loadercallback for the Examples loader.
        LoaderCallbacks<List<Example>> examplesLoaderListener =
                new LoaderCallbacks<List<Example>>() {
                    @Override
                    public Loader<List<Example>> onCreateLoader(int id, Bundle args) {
                        return new ExamplesLoader(getContext(), mWord);
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Example>> loader, List<Example> data) {
                        // Create a list of example sentences.
                        ArrayList<String> examples = new ArrayList<>();

                        for (Example e : data) {
                            examples.add(e.getText());
                        }

                        // Clear the adapter of previous example sentences data
                        mAdapter.clear();

                        mAdapter.addAll(examples);

                        ListView listView = rootView.findViewById(R.id.list);

                        listView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Example>> loader) {
//                      // Loader reset, so we can clear out our existing data.
                        mAdapter.clear();
                    }
                };

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            // A reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in the examplesLoaderListener for the LoaderCallbacks parameter.
            loaderManager.initLoader(EXAMPLES_LOADER_ID, null, examplesLoaderListener);
        } else {
            TextView emptyStateTextView = rootView.findViewById(R.id.empty_view);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }

        return rootView;
    }

}
