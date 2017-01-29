package com.ramonlence.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ramonlence.popularmovies.entities.Movie;
import com.ramonlence.popularmovies.utilities.MovieReaderFromJson;
import com.ramonlence.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterClickHandler {

    public static final int POPULAR_OPTION = 1;
    public static final int RATED_OPTION = 2;
    private static final String PATH_POPULAR = "popular";
    private static final String PATH_RATED = "top_rated";
    private ArrayList<Movie> loadedMovies;

    private MoviePosterAdapter mPosterAdapter;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mProgressBar;


    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager
                = new GridLayoutManager(MainActivity.this, 2);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mPosterAdapter = new MoviePosterAdapter(this);

        mRecyclerView.setAdapter(mPosterAdapter);

        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        loadPopularMovies();
    }

    private void loadPopularMovies(){
        new FetchMoviesTask().execute(POPULAR_OPTION);
    }

    private void loadTopRatedMovies(){
        new FetchMoviesTask().execute(RATED_OPTION);
    }

    public void onClick(Movie selectedMovie){
        Context context = MainActivity.this;
        Class destinationActivity = SingleMovie.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, selectedMovie.getOriginal_title());
        startActivity(startChildActivityIntent);
    }

    public class FetchMoviesTask extends AsyncTask<Integer, Integer, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Integer... option) {

            if (option.length==0 || (POPULAR_OPTION != option[0] && RATED_OPTION != option[0])) {
                return null;
            }
            String queryPath = "";
            if(POPULAR_OPTION == option[0]){
                queryPath = PATH_POPULAR;
            } else if(RATED_OPTION == option[0]){
                queryPath = PATH_RATED;
            }

            URL moviesRequestUrl = NetworkUtils.buildUrl(queryPath);

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);

                ArrayList<Movie> result = MovieReaderFromJson.readMovies(jsonMoviesResponse);

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            if (movies != null) {
                mPosterAdapter.setMoviesData(movies);
            } else {

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.menu_popular) {
            loadPopularMovies();
            return true;
        } else if(itemThatWasClickedId == R.id.menu_toprated) {
            loadTopRatedMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
