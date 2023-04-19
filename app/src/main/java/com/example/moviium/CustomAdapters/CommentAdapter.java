package com.example.moviium.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviium.Models.Comment;
import com.example.moviium.R;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Comment> {
    ArrayList<Comment> listOfComments;
    Context context;

    //has a function to take id from item in homepage list
    public interface OnItemClickListener {
        //defined in HomePage activity
        void onMovieClick(String id);
    }

    public CommentAdapter(Context context, ArrayList<Comment> listOfComments) {
        super(context, R.layout.new_release_item, listOfComments);
        this.listOfComments = listOfComments;
        this.context = context;
    }

    private static class ViewHolder {
        TextView txtEmail;
        TextView txtComment;
        TextView txtDate;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Comment comment = listOfComments.get(position);
        CommentAdapter.ViewHolder viewHolder;

        //convertView object holds the whole item/row
        if (convertView == null) {

            viewHolder = new CommentAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            //inflator populates empty view with our design elements
            convertView = inflater.inflate(R.layout.comment_item, parent, false);

            //viewHolder only holds parts of the convertView
            viewHolder.txtEmail = (TextView) convertView.findViewById(R.id.comment_email);
            viewHolder.txtComment = (TextView) convertView.findViewById(R.id.comment_text);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.comment_date);

            //a tag makes the convertView see where the viewHolder is
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommentAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.txtEmail.setText(comment.getUserEmail());
        viewHolder.txtComment.setText(comment.getComment());
        viewHolder.txtDate.setText(comment.getDateTime().toString());


        return convertView;

    }

    public void reload() {
        notifyDataSetChanged();
    }
}