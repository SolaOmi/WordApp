package com.solaomi.wordapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class WordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        // Get word passed through from Search Activity.
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String word = bundle.getString("word");

            //Find example TextView
            TextView exampleTextView = findViewById(R.id.example_text);
            exampleTextView.setText(word);
        }
    }
}
