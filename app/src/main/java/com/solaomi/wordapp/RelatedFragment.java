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
import android.widget.ListView;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.Related;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RelatedFragment extends Fragment {

//    private static final String LOG_TAG = RelatedFragment.class.getName();
    private static final int RELATED_LOADER_ID = 1;
    private String mWord;
    private AttributesArrayAdapter mAdapter;

    public RelatedFragment() {
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

        // Implement a loadercallback for the Related Loader
        LoaderCallbacks<List<Related>> relatedLoaderListener =
                new LoaderCallbacks<List<Related>>() {
                    @Override
                    public Loader<List<Related>> onCreateLoader(int id, Bundle args) {
                        return new RelatedLoader(getContext(), mWord);
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Related>> loader, List<Related> data) {
                        String[] types = new String[] {getString(R.string.antonym),
                                getString(R.string.synonym)};
                        List<String> antonyms = null;
                        List<String> synonyms = null;
                        ArrayList<RelatedWord> words = new ArrayList<>();

                        // Get the list of Antonyms and Synonyms from the List of Related Objects.
                        for(Related r : data) {
                            if (r.getRelType().equals(types[0])) {
                                antonyms = r.getWords();
                            }
                            if (r.getRelType().equals(types[1])) {
                                synonyms = r.getWords();
                            }
                        }

                        // Add the list of words from antonyms and synonyms to the list of
                        // RelatedWord objects.
                        if (antonyms != null) {
                            for(String antonym : antonyms) {
                                words.add(new RelatedWord(types[0], antonym));
                            }
                        }

                        if (synonyms != null) {
                            for (String synonym : synonyms) {
                                words.add(new RelatedWord(types[1], synonym));
                            }
                        }

                        // Clear the adapter of previous RelatedWord data
                        if (mAdapter != null) {
                            mAdapter.clear();
                        }

                        // Instantiate a {@link AttributesArrayAdapter}, whose data source is a list of
                        // {@link RelatedWord}s. The adapter knows how to create list items for each
                        // item in the list.
                        mAdapter = new AttributesArrayAdapter<>(getActivity(), words);

                        ListView listView = rootView.findViewById(R.id.list);
                        listView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Related>> loader) { mAdapter.clear();}
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
            // the bundle. Pass in the relatedLoaderListener for the LoaderCallbacks parameter.
            loaderManager.initLoader(RELATED_LOADER_ID, null, relatedLoaderListener);
        } else {
            TextView emptyStateTextView = rootView.findViewById(R.id.empty_view);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }

        return rootView;
    }
}
