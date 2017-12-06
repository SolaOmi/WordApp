package com.solaomi.wordapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class WordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        // Get word passed through from Search Activity.
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            String word = bundle.getString("word");
//
//            //Find example TextView
//            TextView exampleTextView = findViewById(R.id.example_text);
//            exampleTextView.setText(word);
//        }

        // Find the view pager that will allow the user to swipe between fragments.
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        WordAttributesAdapter adapter = new WordAttributesAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
    }
}
