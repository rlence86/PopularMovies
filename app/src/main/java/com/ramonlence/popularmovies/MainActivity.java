package com.ramonlence.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ramonlence.popularmovies.entities.Movie;
import com.ramonlence.popularmovies.utilities.MovieReaderFromJson;
import com.ramonlence.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int POPULAR_OPTION = 1;
    public static final int RATED_OPTION = 2;
    private static final String PATH_POPULAR = "popular";
    private static final String PATH_RATED = "top_rated";
    private ArrayList<Movie> loadedMovies;

    private MoviePosterAdapter mPosterAdapter;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text_id);
        loadPopularMovies();
    }

    private void loadPopularMovies(){
        new FetchMoviesTask().execute(POPULAR_OPTION);
    }

    private void renderPopularMovies(){
        for(Movie movie : loadedMovies){
            mTextView.append(movie.getOriginal_title()+"\n\n\n");
        }
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
            loadedMovies = movies;
            renderPopularMovies();
            super.onPostExecute(movies);
        }
    }

}
