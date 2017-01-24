package com.ramonlence.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    public static final int POPULAR_OPTION = 1;
    public static final int RATED_OPTION = 2;

    private MoviePosterAdapter mPosterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPosterAdapter = new MoviePosterAdapter();
        loadPopularMovies();
    }

    private void loadPopularMovies(){
        new FetchMoviesTask().execute(POPULAR_OPTION);
    }
}
