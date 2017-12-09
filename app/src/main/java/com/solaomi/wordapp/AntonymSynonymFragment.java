package com.solaomi.wordapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AntonymSynonymFragment extends Fragment {

    /** Tag for log messages */
    private static final String LOG_TAG = AntonymSynonymFragment.class.getName();

    public AntonymSynonymFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("Antonym/Synonym");
        return textView;
    }

}
