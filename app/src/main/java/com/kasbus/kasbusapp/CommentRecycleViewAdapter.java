package com.kasbus.kasbusapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kasbus.kasbusapp.Containers.Comment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentRecycleViewAdapter extends RecyclerView.Adapter<CommentRecycleViewAdapter.ViewHolder> {
    private List<Comment> comments;

    public CommentRecycleViewAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comment_content;
        public TextView comment_date;
        public TextView comment_faculty;

        public ViewHolder(View itemView) {
            super(itemView);

            comment_content = (TextView) itemView.findViewById(R.id.comment_content);
            comment_date = (TextView) itemView.findViewById(R.id.comment_date);
            comment_faculty = (TextView) itemView.findViewById(R.id.comment_faculty);
        }
    }
    
    @SuppressLint("NotifyDataSetChanged")
    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public CommentRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View commentView = inflater.inflate(R.layout.comment_template, parent, false);

        return new CommentRecycleViewAdapter.ViewHolder(commentView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRecycleViewAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);

        int seconds = comment.getTimestamp().seconds;
        int nano_seconds = comment.getTimestamp().nano_seconds;
        long milli_seconds = seconds * 1000L + nano_seconds / 1000000L;
        Locale locale = new Locale("lt", "LT");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", locale);
        String date = dateFormat.format(milli_seconds);

        holder.comment_content.setText(comment.getContent());
        holder.comment_date.setText(date);
        holder.comment_faculty.setText(comment.getFaculty());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
