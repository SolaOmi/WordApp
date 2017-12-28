package com.solaomi.wordapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.Definition;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefinitionsFragment extends Fragment {

//    private static final String LOG_TAG = DefinitionsFragment.class.getName();

    /** Constant value for the definitions loader ID */
    private static final int DEFINITIONS_LOADER_ID = 1;

    /** Constant value for the max line count of the content TextView */
    private static final int DEFAULT_MAX_LINE_COUNT = 3;

    /** Constant value for the expanded max line count of the content TextView (High enough to fit
     *  any definition text within the TextView)
     */
    private static final int EXPANDED_MAX_LINE_COUNT =  100;

    /** Word being looked up */
    private String mWord;

    /** Adapter for list of definition objects */
    private AttributesArrayAdapter<Definition> mAdapter;

    /** Textview that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /** ProgressBar that appears during data retrieval */
    private ProgressBar mLoadingIndicator;

    public DefinitionsFragment() {
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

        // Create a new adapter that takes an empty list of definition objects as input
        mAdapter = new AttributesArrayAdapter<>(getActivity(), new ArrayList<Definition>());

        // Set the adapter on the {@link ListView} so the list can be populated in the user
        // interface
        listView.setAdapter(mAdapter);

        // Set a click listener to expand/collapse the TextView's when ellipses are used
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView contentTextView = view.findViewById(R.id.word_attribute_content);
                Layout layout = contentTextView.getLayout();
                int lines = contentTextView.getLineCount();

                // expand Textview if their ellipses
                if (lines > 0) {
                    int ellipsisCount = layout.getEllipsisCount(lines - 1);
                    if (ellipsisCount > 0) {
                        contentTextView.setMaxLines(EXPANDED_MAX_LINE_COUNT);
                    }
                }

                // revert back to default state
                if (lines > DEFAULT_MAX_LINE_COUNT) {
                    contentTextView.setMaxLines(DEFAULT_MAX_LINE_COUNT);
                }
            }
        });

        // Implement a loadercallback for the Definitions loader.
        LoaderCallbacks<List<Definition>> definitionsLoaderListener =
                new LoaderCallbacks<List<Definition>>() {
                    @Override
                    public Loader<List<Definition>> onCreateLoader(int id, Bundle args) {
                        return new DefinitionsLoader(getContext(), mWord);
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Definition>> loader, List<Definition> data) {
                        // Hide loading indicator because data has been loaded
                        mLoadingIndicator.setVisibility(View.GONE);

                        // Set empty state text to display "No definitions found."
                        mEmptyStateTextView.setText(R.string.no_definitions);

                        // Clear the adapter of previous data
                        mAdapter.clear();

                        // If there is a valid list of {@link Definition}s, then add them to the
                        // adapter's data set. This will trigger the ListView to update
                        if (data != null) {
                            mAdapter.addAll(data);
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Definition>> loader) { mAdapter.clear(); }
                };

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            // A reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in the definitionsLoaderListener for the LoaderCallbacks parameter.
            loaderManager.initLoader(DEFINITIONS_LOADER_ID, null, definitionsLoaderListener);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            mLoadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message.
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        return rootView;
    }
}
