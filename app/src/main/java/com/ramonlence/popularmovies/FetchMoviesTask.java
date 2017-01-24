package com.ramonlence.popularmovies;

import android.os.AsyncTask;

import com.ramonlence.popularmovies.entities.Movie;
import com.ramonlence.popularmovies.utilities.MovieReaderFromJson;
import com.ramonlence.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ramon on 24/1/17.
 */

public class FetchMoviesTask extends AsyncTask<Integer, Integer, ArrayList<Movie>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Movie> doInBackground(Integer... option) {

        if (option.length==0 || (MainActivity.POPULAR_OPTION != option[0] && MainActivity.RATED_OPTION != option[0])) {
            return null;
        }

        URL moviesRequestUrl = NetworkUtils.buildUrl(option[0]);

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
        super.onPostExecute(movies);
    }
}
