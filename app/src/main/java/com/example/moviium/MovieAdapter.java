package com.example.moviium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {
    ArrayList<Movie> movies;
    Context context;

    public MovieAdapter(ArrayList<Movie> movies, Context context) {
        super(context, R.layout.movie_list_completed_items, movies);
        this.movies = movies;
        this.context = context;
    }

    private static class ViewHolder {
        ImageView thumbnail;
        TextView txtMovieTitle;
        TextView txtComment;
        TextView txtRating;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = movies.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_list_completed_items, parent, false);
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.movieIcon_CP);
            viewHolder.txtMovieTitle = (TextView) convertView.findViewById(R.id.movieName_CP);
            viewHolder.txtComment = (TextView) convertView.findViewById(R.id.comment_CP);
            viewHolder.txtRating = (TextView) convertView.findViewById(R.id.rate_CP);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //viewHolder.thumbnail.setImageURI(movie.getMovieImage());
        viewHolder.txtMovieTitle.setText(movie.getMovieTitle());
        viewHolder.txtComment.setText(movie.getComment());
        viewHolder.txtRating.setText(movie.getRating());

        return convertView;
    }
}
