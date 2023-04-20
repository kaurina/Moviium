package com.example.moviium.CustomAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviium.Models.PlanToWatch;
import com.example.moviium.R;

import java.util.ArrayList;

public class PlanToWatchAdapter extends RecyclerView.Adapter<PlanToWatchAdapter.MyViewHolder> {
    ArrayList<PlanToWatch> watchList;


    public PlanToWatchAdapter(ArrayList<PlanToWatch> watchList) {
        this.watchList = watchList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_plantowatch_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtTitle.setText(this.watchList.get(position).getMovieTitle());
    }

    @Override
    public int getItemCount() {
        return this.watchList.size();
    }

    public ArrayList<PlanToWatch> getData(){
        return this.watchList;
    }

    public void removeItem(int pos){
        this.watchList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, getItemCount());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle =  itemView.findViewById(R.id.movieName_PTW);
        }
    }
    public void reload() {notifyDataSetChanged();}




}


