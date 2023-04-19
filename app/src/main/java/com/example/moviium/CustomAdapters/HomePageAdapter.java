package com.example.moviium.CustomAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
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
import com.example.moviium.HomePage;
import com.example.moviium.Movie;
import com.example.moviium.R;
import com.example.moviium.RatingPage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomePageAdapter extends ArrayAdapter<Movie> {
    ArrayList<Movie> listOfMovies;
    Context context;
    //object from interface
    private OnItemClickListener listener;

    //has a function to take id from item in homepage list
    public interface OnItemClickListener {
        //defined in HomePage activity
        void onMovieClick(String id);
    }

    public HomePageAdapter(Context context, ArrayList<Movie> listOfMovies, OnItemClickListener listener) {
        super(context, R.layout.new_release_item, listOfMovies);
        this.listOfMovies = listOfMovies;
        this.context = context;
        this.listener = listener;
    }

    private static class ViewHolder {
        ImageView movieImg;
        TextView txtTitle;
        LinearLayout movieItemLinearlayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Movie movie = listOfMovies.get(position);
        ViewHolder viewHolder;

        //convertView object holds the whole item/row
        if (convertView == null) {

            viewHolder = new ViewHolder();
            //
            LayoutInflater inflater = LayoutInflater.from(getContext());
            //inflator populates empty view with our design elements
            convertView = inflater.inflate(R.layout.new_release_item, parent, false);

            //viewHolder only holds parts of the convertView
            viewHolder.movieItemLinearlayout = (LinearLayout) convertView.findViewById(R.id.movie_item_linearlayout);
            viewHolder.movieImg = (ImageView) convertView.findViewById(R.id.movie_icon);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txtmovie_description);

            //a tag makes the convertView see where the viewHolder is
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.movieImg.setImageDrawable(HelperClass.drawableFromUrl(movie.getMovieImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        viewHolder.txtTitle.setText(movie.getMovieTitle());

        //when the item linear layout is clicked on, get the id
        viewHolder.movieItemLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMovieClick(movie.getId());
            }
        });

        return convertView;

    }

    public void reload() {notifyDataSetChanged();}



//    //convert url to image
//    public Drawable drawableFromUrl(String url) throws IOException {
//        Bitmap x;
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//        connection.connect();
//        InputStream input = connection.getInputStream();
//
//        x = BitmapFactory.decodeStream(input);
//        return new BitmapDrawable(Resources.getSystem(), x);
//    }
}
