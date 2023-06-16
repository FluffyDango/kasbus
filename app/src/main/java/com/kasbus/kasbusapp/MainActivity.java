package com.kasbus.kasbusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private List<Subject> subjects;
    private SubjectRecycleViewAdapter adapter;
    private RecyclerView rv_subjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_subjects = findViewById(R.id.bus_list);
        adapter = new SubjectRecycleViewAdapter(new ArrayList<>());
        rv_subjects.setAdapter(adapter);
        rv_subjects.setLayoutManager(new LinearLayoutManager(this));

//        APICalls api_calls = new APICalls();
        APICalls.setSubjectCallback(this);
        APICalls.fetchSubjects("EN");
    }

    @Override
    public void onSubjectsReceived(List<Subject> subjects) {
        this.subjects = subjects;
        adapter = new SubjectRecycleViewAdapter(subjects);
        rv_subjects.setAdapter(adapter);

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
            public boolean onQueryTextSubmit(String s) {
                searchSubjects(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchSubjects(s);
                return true; // Return true to consume the event
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

    private void searchSubjects(String query) {
        List<Subject> filteredSubjects = new ArrayList<>();

        if (subjects != null) {
            for (Subject subject : subjects) {
                if (subject.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredSubjects.add(subject);
                }
            }
        }

        adapter = new SubjectRecycleViewAdapter(filteredSubjects);
        rv_subjects.setAdapter(adapter);
    }
}
