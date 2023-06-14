package com.kasbus.kasbusapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kasbus.kasbusapp.Containers.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private List<Subject> subjects;

    public SubjectAdapter(List<Subject> subjects) {
        this.subjects = subjects;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView facultyTextView;
        public TextView lecturersTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.bus_name);
            facultyTextView = (TextView) itemView.findViewById(R.id.faculty);
            lecturersTextView = (TextView) itemView.findViewById(R.id.lecturers);
        }
    }

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View subjectView = inflater.inflate(R.layout.bus_subject, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(subjectView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SubjectAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Subject subject = subjects.get(position);

        // Set item views based on your views and data model
        holder.nameTextView.setText(subject.getName());
        holder.facultyTextView.setText(subject.getFaculty());
        holder.lecturersTextView.setText(subject.getLecturers());
//        Button button = holder.messageButton;
//        button.setText(subject.getExam() ? "Message" : "Offline");
//        button.setEnabled(subject.getExam());
    }

//     Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
