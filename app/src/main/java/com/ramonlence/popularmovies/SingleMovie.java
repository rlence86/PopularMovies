package com.ramonlence.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SingleMovie extends AppCompatActivity {

    TextView mMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        mMovieTitle = (TextView) findViewById(R.id.movie_title_in_single);

        Intent currentIntent = getIntent();

        if(currentIntent.hasExtra(Intent.EXTRA_TEXT)){
            String textToShow = currentIntent.getStringExtra(Intent.EXTRA_TEXT);
            mMovieTitle.setText(textToShow);
        }
    }
}
