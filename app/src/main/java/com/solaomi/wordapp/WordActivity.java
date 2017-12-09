package com.solaomi.wordapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class WordActivity extends AppCompatActivity {

    //    private static final String LOG_TAG = WordActivity.class.getName();
    private String mWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        // Get word passed through from Search Activity.
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mWord = bundle.getString("word");

            //Find example TextView
            TextView exampleTextView = findViewById(R.id.word_text);
            exampleTextView.setText(mWord);
        }

        // Find the view pager that will allow the user to swipe between fragments.
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page,
        // and passes on word to those fragments..
        WordAttributesAdapter adapter = new WordAttributesAdapter(this,
                getSupportFragmentManager(), mWord);

        // Set the adapter onto the view pager.
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs.
        TabLayout tabLayout = findViewById(R.id.tabs);

        // Connect the tab layout with the view pager.
        tabLayout.setupWithViewPager(viewPager);
    }
}
