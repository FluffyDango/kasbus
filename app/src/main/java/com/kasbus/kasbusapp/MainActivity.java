package com.kasbus.kasbusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.kasbus.kasbusapp.API.*;
import com.kasbus.kasbusapp.Containers.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SubjectCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APICalls.setSubjectCallback(this);
        APICalls.fetchSubjects("EN");
    }

    @Override
    public void onSubjectsReceived(List<Subject> subjects) {
        RecyclerView rv_subjects = (RecyclerView) findViewById(R.id.bus_list);
        SearchView searchView = findViewById(R.id.searchView);
        ConstraintLayout loading_screen = (ConstraintLayout) findViewById(R.id.loading_screen);
        ConstraintLayout retry_loading_screen = (ConstraintLayout) findViewById(R.id.retry_loading_screen);

        SubjectRecycleViewAdapter adapter = new SubjectRecycleViewAdapter(subjects);
        rv_subjects.setAdapter(adapter);
        rv_subjects.setLayoutManager(new LinearLayoutManager(this));

        loading_screen.setVisibility(View.GONE);
        retry_loading_screen.setVisibility(View.GONE);
        rv_subjects.setVisibility(View.VISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                searchSubjects(text, subjects);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                searchSubjects(text, subjects);
                return true;
            }
        });
    }

    @Override
    public void onSubjectFailure(String language) {
        ConstraintLayout retry_loading_screen = (ConstraintLayout) findViewById(R.id.retry_loading_screen);
        Button retry_button = (Button) findViewById(R.id.retry_button);
        ConstraintLayout loading_screen = (ConstraintLayout) findViewById(R.id.loading_screen);

        retry_loading_screen.setVisibility(View.VISIBLE);
        loading_screen.setVisibility(View.GONE);

        retry_button.setOnClickListener(v -> {
            loading_screen.setVisibility(View.VISIBLE);
            retry_loading_screen.setVisibility(View.GONE);
            APICalls.fetchSubjects(language);
        });
    }

    private void searchSubjects(String query, List<Subject> subjects) {
        if (subjects == null) {
            return;
        }
        List<Subject> filteredSubjects = new ArrayList<>();

        for (Subject subject : subjects) {
            if (subject.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredSubjects.add(subject);
            }
        }

        RecyclerView rv_subjects = (RecyclerView) findViewById(R.id.bus_list);
        SubjectRecycleViewAdapter adapter = (SubjectRecycleViewAdapter) rv_subjects.getAdapter();
        adapter.setSubjects(filteredSubjects);
    }

    public void sendToFilterPage(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, SelectionActivity.class);
        context.startActivity(intent);
    }
}
