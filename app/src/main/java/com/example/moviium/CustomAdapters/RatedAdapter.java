package com.example.moviium.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviium.HelperClass;
import com.example.moviium.Models.Movie;
import com.example.moviium.Models.Rating;
import com.example.moviium.R;

import java.io.IOException;
import java.util.ArrayList;

public class RatedAdapter extends ArrayAdapter<Rating> {

    ArrayList<Rating> movies;
    Context context;


    public RatedAdapter(Context context, ArrayList<Rating> movies) {
        super(context, R.layout.movie_list_completed_items, movies);
        this.context = context;
        this.movies = movies;
    }

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtRating;
        LinearLayout movieItemLinearlayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Rating rating = movies.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            //
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_list_completed_items, parent, false);

            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.movieName_CP);
            viewHolder.txtRating = (TextView) convertView.findViewById(R.id.rate_CP);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RatedAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.txtTitle.setText(rating.getMovieTitle());
        viewHolder.txtRating.setText(rating.getRate().toString());

        return convertView;
    }

    public void reload() {notifyDataSetChanged();}
}
