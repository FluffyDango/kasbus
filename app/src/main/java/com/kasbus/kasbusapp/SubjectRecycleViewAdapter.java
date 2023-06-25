package com.kasbus.kasbusapp;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kasbus.kasbusapp.Containers.Subject;

import java.util.List;

public class SubjectRecycleViewAdapter extends RecyclerView.Adapter<SubjectRecycleViewAdapter.ViewHolder> {

    private List<Subject> subjects;

    public SubjectRecycleViewAdapter(List<Subject> subjects) {
        this.subjects = subjects;
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout containerLayout;
        public TextView nameTextView;
        public TextView facultyTextView;
        public TextView lecturersTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            containerLayout = (LinearLayout) itemView.findViewById(R.id.bus_container);
            nameTextView = (TextView) itemView.findViewById(R.id.bus_name);
            facultyTextView = (TextView) itemView.findViewById(R.id.faculty);
            lecturersTextView = (TextView) itemView.findViewById(R.id.lecturers);
        }
    }
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SubjectRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View subjectView = inflater.inflate(R.layout.bus_template, parent, false);

        return new ViewHolder(subjectView);
    }

    @Override
    public void onBindViewHolder(SubjectRecycleViewAdapter.ViewHolder holder, int position) {
        Subject subject = subjects.get(position);

        holder.nameTextView.setText(subject.getName());
        holder.facultyTextView.setText(subject.getFaculty());
        holder.lecturersTextView.setText(subject.getLecturers());

        holder.containerLayout.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, SubjectActivity.class);
            intent.putExtra("subject", subject);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
