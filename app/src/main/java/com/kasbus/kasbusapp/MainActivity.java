package com.kasbus.kasbusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.kasbus.kasbusapp.API.*;
import com.kasbus.kasbusapp.Containers.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SubjectCallback {

    public static String PACKAGE_NAME;
    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        SharedPreferences prefs = getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
        if (!prefs.contains("language")) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("language", "EN");
            editor.commit();
        }

        ImageButton filter = (ImageButton) findViewById(R.id.filter);
        filter.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, SelectionActivity.class);
            context.startActivity(intent);
        });

        Button lang_button = (Button) findViewById(R.id.lang_button);

        APICalls.setSubjectCallback(this);
        String lang = prefs.getString("language", "FAILED");
        APICalls.fetchSubjects(lang);
        setButtonText(lang);

        lang_button.setOnClickListener(view -> {
            String current_language = prefs.getString("language", "FAILED");
            String next_language = getNextLanguage(current_language);
            if (next_language == null) {
                Log.e("main",
                        "Unknown language in language preferences: " + current_language);
                return;
            }

            SharedPreferences.Editor editor = prefs.edit();
            APICalls.fetchSubjects(next_language);
            editor.putString("language", next_language);
            editor.apply();
            setButtonText(next_language);
        });
    }

    @Override
    public void onSubjectsReceived(List<Subject> subjects) {
        RecyclerView rv_subjects = (RecyclerView) findViewById(R.id.bus_list);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
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
                List<Subject> filteredSubjects = searchSubjects(text, subjects);
                if (filteredSubjects == null) {
                    Log.e("filter",
                            "searchSubjects returned null, subjects array is likely null");
                    return false;
                }
                adapter.setSubjects(filteredSubjects);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
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

    /**
     *
     * @param query the thing we want to search for
     * @param subjects the list of subjects to filter out
     * @return null or filtered subjects
     *
     */
    private List<Subject> searchSubjects(String query, List<Subject> subjects) {
        if (subjects == null)
            return null;

        List<Subject> filteredSubjects = new ArrayList<>();
        for (Subject subject : subjects) {
            if (subject.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredSubjects.add(subject);
            }
        }
        return filteredSubjects;
    }

    private String getNextLanguage(String current_language) {
        String next_language;
        if (current_language.equals("LT"))
            next_language = "EN";
        else if (current_language.equals("EN"))
            next_language = "LT";
        else
            return null;

        return next_language;
    }

    private void setButtonText(String language) {
        Button lang_button = (Button) findViewById(R.id.lang_button);
        if (language.equals("EN"))
            lang_button.setText(Html.fromHtml("<big><b>EN</b></big>/<small>LT</small>"));
        else if (language.equals("LT"))
            lang_button.setText(Html.fromHtml("<small>EN</small>/<big><b>LT</b></big>"));
        else
            Log.e("main_activity", "Failed to set lang_button text");
    }

}
