package com.ramonlence.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ramon on 24/1/17.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MovieViewHolder> {

    private int mNumberOfItems;

    public MoviePosterAdapter(int numberOfItems){
        mNumberOfItems = numberOfItems;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mNumberOfItems;
    }

    @Override
    public MoviePosterAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public MovieViewHolder(View view){
            super(view);

        }
    }

}
