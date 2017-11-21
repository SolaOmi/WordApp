package com.solaomi.wordapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String word = "example";

        TextView exampleTextView = (TextView) findViewById(R.id.example);
        exampleTextView.setText(word);
    }
}
