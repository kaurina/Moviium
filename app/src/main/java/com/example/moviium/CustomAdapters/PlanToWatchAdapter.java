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
import com.example.moviium.R;

import java.io.IOException;
import java.util.ArrayList;

public class PlanToWatchAdapter extends ArrayAdapter<Movie> {
    ArrayList<Movie> watchList;
    Context context;


    public PlanToWatchAdapter(Context context, ArrayList<Movie> watchList) {
        super(context, R.layout.movie_list_plantowatch_items);
        this.context = context;
        this.watchList = watchList;
    }

    private static class ViewHolder {
        ImageView movieImg;
        TextView txtTitle;
        LinearLayout movieItemLinearlayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = watchList.get(position);
        PlanToWatchAdapter.ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new PlanToWatchAdapter.ViewHolder();
            //
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_list_plantowatch_items, parent, false);

            viewHolder.movieItemLinearlayout = (LinearLayout) convertView.findViewById(R.id.movie_item_linearlayout);
            viewHolder.movieImg = (ImageView) convertView.findViewById(R.id.movieIcon_PTW);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.movieName_PTW);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PlanToWatchAdapter.ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.movieImg.setImageDrawable(HelperClass.drawableFromUrl(movie.getMovieImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        viewHolder.txtTitle.setText(movie.getMovieTitle());

        return convertView;
    }

    //where do we call this?
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


