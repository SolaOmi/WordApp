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
import android.widget.ProgressBar;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamplesFragment extends Fragment {

//    private static final String LOG_TAG = ExamplesFragment.class.getName();

    /** Constant value for the examples loader ID */
    private static final int EXAMPLES_LOADER_ID = 1;

    /** Word being looked up */
    private String mWord;

    /** Adapter for list of example objects */
    private AttributesArrayAdapter<Example> mAdapter;

    /** Textview that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /** ProgressBar that appears during data retrieval */
    private ProgressBar mLoadingIndicator;

    public ExamplesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Get word to be looked up that was passed on from the SearchActivity
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mWord = bundle.getString("word");
        }

        // Find a reference to the {@link ListView} in the layout
        ListView listView = rootView.findViewById(R.id.list);

        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);

        // Create a new adapter that takes an empty list of example objects as input
        mAdapter = new AttributesArrayAdapter<>(getActivity(), new ArrayList<Example>());

        // Set the adapter on the {@link ListView} so the list can be populated in the user
        // interface
        listView.setAdapter(mAdapter);

        // Implement a loadercallback for the Examples loader.
        LoaderCallbacks<List<Example>> examplesLoaderListener =
                new LoaderCallbacks<List<Example>>() {
                    @Override
                    public Loader<List<Example>> onCreateLoader(int id, Bundle args) {
                        return new ExamplesLoader(getContext(), mWord);
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Example>> loader, List<Example> data) {
                        // Hide loading indicator because data has been loaded
                        mLoadingIndicator.setVisibility(View.GONE);

                        // Set empty state text to display "No examples found."
                        mEmptyStateTextView.setText(R.string.no_examples);

                        // Clear the adapter of previous data
                        mAdapter.clear();

                        // If there is a valid list of {@link Example}s, then add them to the
                        // adapter's data set. This will trigger the ListView to update
                        if (data != null) {
                            mAdapter.addAll(data);
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Example>> loader) { mAdapter.clear(); }
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
            // First, hide loading indicator so error message will be visible
            mLoadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        return rootView;
    }

}
