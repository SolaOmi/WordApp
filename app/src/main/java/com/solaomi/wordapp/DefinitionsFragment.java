package com.solaomi.wordapp;


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

import net.jeremybrooks.knicker.dto.Definition;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefinitionsFragment extends Fragment {

    //    private static final String LOG_TAG = DefinitionsFragment.class.getName();
    private static final int DEFINITIONS_LOADER_ID = 1;
    private String mWord;
    private DefinitionsAdapter mAdapter;

    public DefinitionsFragment() {
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

//        // Create a new adapter that takes an empty list of Strings as input.
//        mAdapter = new DefinitionsAdapter(
//                getActivity(),
//                android.R.layout.simple_list_item_1,
//                new ArrayList<String>()
//        );
//        mAdapter = new DefinitionsAdapter(getActivity(), null);

        // Implement a loadercallback for the Definitions loader.
        LoaderCallbacks<List<Definition>> definitionsLoaderListener =
                new LoaderCallbacks<List<Definition>>() {
                    @Override
                    public Loader<List<Definition>> onCreateLoader(int id, Bundle args) {
                        return new DefinitionsLoader(getContext(), mWord);
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Definition>> loader, List<Definition> data) {
                        // Clear the adapter of previous definitions data
                        mAdapter.clear();

                        // Instantiate a {@link DefinitonsAdapter}, whose data source is a list of
                        // {@link Definition}s. The adapter knows how to create list items for each
                        // item in the list.
                        mAdapter = new DefinitionsAdapter(getActivity(), data);

                        ListView listView = rootView.findViewById(R.id.list);
                        listView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Definition>> loader) {
                        mAdapter.clear();
                    }
                };

        // A reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in the definitionsLoaderListener for the LoaderCallbacks parameter.
        loaderManager.initLoader(DEFINITIONS_LOADER_ID, null, definitionsLoaderListener);

        return rootView;
    }
}
