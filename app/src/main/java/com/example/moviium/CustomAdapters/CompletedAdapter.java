package com.example.moviium.CustomAdapters;

import android.content.Context;
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
import com.example.moviium.Movie;
import com.example.moviium.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CompletedAdapter extends ArrayAdapter<Movie> {

    ArrayList<Movie> completedList;
    Context context;


    public CompletedAdapter(Context context, ArrayList<Movie> completedList) {
        super(context, R.layout.movie_list_completed_items);
        this.context = context;
        this.completedList = completedList;
    }

    private static class ViewHolder {
        ImageView movieImg;
        TextView txtTitle;
        TextView txtRating;
        LinearLayout movieItemLinearlayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = completedList.get(position);
        CompletedAdapter.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new CompletedAdapter.ViewHolder();
            //
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_list_completed_items, parent, false);

            viewHolder.movieItemLinearlayout = (LinearLayout) convertView.findViewById(R.id.movie_item_linearlayout);
            viewHolder.movieImg = (ImageView) convertView.findViewById(R.id.movieIcon_CP);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.movieName_CP);
            viewHolder.txtRating = (TextView) convertView.findViewById(R.id.rate_CP);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CompletedAdapter.ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.movieImg.setImageDrawable(HelperClass.drawableFromUrl(movie.getMovieImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        viewHolder.txtTitle.setText(movie.getMovieTitle());
        viewHolder.txtRating.setText((int) movie.getMyRating());

        return convertView;
    }

    public void reload() {notifyDataSetChanged();}

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
