package com.solaomi.wordapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.Related;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AntonymSynonymFragment extends Fragment {

//    private static final String LOG_TAG = AntonymSynonymFragment.class.getName();
    private static final int ANTONYM_SYNONYM_LOADER_ID = 1;
    private String mWord;

    public AntonymSynonymFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            mWord = bundle.getString("word");
        }

        // Two textviews for Antonyms and Synonyms.
        final TextView antonymTextView = new TextView(getActivity());
        final TextView synonymTextView = new TextView(getActivity());

        // A LinearLayout to contain the two text views above.
        final LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

        LoaderCallbacks<List<Related>> antonymSynonymLoaderListener =
                new LoaderCallbacks<List<Related>>() {
                    @Override
                    public Loader<List<Related>> onCreateLoader(int id, Bundle args) {
                        return new AntonymSynonymLoader(getContext(), mWord);
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Related>> loader, List<Related> data) {
                        StringBuilder antonymText = new StringBuilder();
                        StringBuilder synonymText = new StringBuilder();
                        List<String> antonyms = null;
                        List<String> synonyms = null;

                        // Get the list of Antonyms and Synonyms from the List of Related Objects.
                        for(Related r : data) {
                            if (r.getRelType().equals("antonym")) {
                                antonyms = r.getWords();
                            }
                            if (r.getRelType().equals("synonym")) {
                                synonyms = r.getWords();
                            }
                        }

                        // Check for null values and make sure there are words in the lists.
                        if ((antonyms != null ? antonyms.size() : 0) == 0) {
                            antonymText.append(getString(R.string.antonym_synonym_fragment));
                        } else {
                            for (String s : antonyms) antonymText.append(s).append(" ");
                        }

                        if ((synonyms != null ? synonyms.size() : 0) == 0) {
                            synonymText.append(getString(R.string.antonym_synonym_fragment));
                        } else {
                            for (String s : synonyms) { synonymText.append(s).append(" "); }
                        }

                        antonymTextView.setText(antonymText);
                        synonymTextView.setText(synonymText);

                        if (antonymTextView.getParent() != null && synonymTextView.getParent() != null) {
                            ((ViewGroup) antonymTextView.getParent()).removeView(antonymTextView);
                            ((ViewGroup) synonymTextView.getParent()).removeView(synonymTextView);
                        }
                        linearLayout.addView(antonymTextView);
                        linearLayout.addView(synonymTextView);
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Related>> loader) { mWord = "";}
                };

        // A reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in the antonymSynonymLoaderListener for the LoaderCallbacks parameter.
        loaderManager.initLoader(ANTONYM_SYNONYM_LOADER_ID, null, antonymSynonymLoaderListener);

        return linearLayout;
    }

}
