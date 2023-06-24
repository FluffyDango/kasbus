package com.kasbus.kasbusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SubjectCallback {


    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = initPreferences();
        changeLocale(prefs);
        setContentView(R.layout.activity_main);

        setFilterOnClick();
        APICalls.setSubjectCallback(this);
        String lang = prefs.getString("language", "");
        APICalls.fetchSubjects(lang);
        setButtonText(lang);
        setLangButtonOnClick(prefs);
    }

    @Override
    public void onSubjectsReceived(List<Subject> subjects) {
        APICalls.setSubjects(subjects);

        RecyclerView rv_subjects = findViewById(R.id.bus_list);
        SubjectRecycleViewAdapter adapter = new SubjectRecycleViewAdapter(subjects);
        rv_subjects.setAdapter(adapter);
        rv_subjects.setLayoutManager(new LinearLayoutManager(this));

        ConstraintLayout loading_screen = findViewById(R.id.loading_screen);
        ConstraintLayout retry_loading_screen = findViewById(R.id.retry_loading_screen);
        loading_screen.setVisibility(View.GONE);
        retry_loading_screen.setVisibility(View.GONE);
        rv_subjects.setVisibility(View.VISIBLE);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                if (APICalls.getSubjects() != null) {
                    List<Subject> filtered_list = searchSubjects(text, APICalls.getSubjects());
                    if (filtered_list == null) {
                        Log.e("kasbus",
                                "searchSubjects returned null, subjects array is likely null");
                        return false;
                    }
                    adapter.setSubjects(filtered_list);
                } else  {
                    Log.e("kasbus", "Subjects are null in APICalls");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                if (text.isEmpty() && APICalls.getSubjects() != null) {
                    adapter.setSubjects(APICalls.getSubjects());
                }
                return true;
            }
        });
    }

    @Override
    public void onSubjectFailure(String language) {
        ConstraintLayout retry_loading_screen = findViewById(R.id.retry_loading_screen);
        Button retry_button = findViewById(R.id.retry_button);
        ConstraintLayout loading_screen = findViewById(R.id.loading_screen);

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
        if (current_language.equals("lt"))
            next_language = "en";
        else if (current_language.equals("en"))
            next_language = "lt";
        else
            return null;

        return next_language;
    }

    private void setButtonText(String language) {
        Button lang_button = findViewById(R.id.lang_button);
        String lang = language.toLowerCase();
        if (lang.equals("en"))
            lang_button.setText(Html.fromHtml("<big><b>EN</b></big>/<small>LT</small>"));
        else if (lang.equals("lt"))
            lang_button.setText(Html.fromHtml("<small>EN</small>/<big><b>LT</b></big>"));
        else
            Log.e("kasbus", "Failed to set lang_button text");
    }

    private void setFilterOnClick() {
        ImageButton filter = findViewById(R.id.filter);
        filter.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, FilterActivity.class);
            context.startActivity(intent);
        });
    }

    private void setLangButtonOnClick(SharedPreferences prefs) {
        Button lang_button = findViewById(R.id.lang_button);
        lang_button.setOnClickListener(view -> {

            String current_language = prefs.getString("language", "");
            if (current_language.isEmpty()) {
                Log.e("kasbus",
                        "Failed to get language: " + current_language);
                return;
            }
            String next_language = getNextLanguage(current_language);
            if (next_language == null) {
                Log.e("kasbus",
                        "Unknown language in language preferences: " + current_language);
                return;
            }

            SharedPreferences.Editor editor = prefs.edit();
            APICalls.fetchSubjects(next_language);
            editor.putString("language", next_language);
            editor.apply();
            setButtonText(next_language);

            changeLocale(prefs);

            // Reload activity to update language
            finish();
            startActivity(getIntent());
        });
    }

    @SuppressLint("ApplySharedPref")
    private SharedPreferences initPreferences() {
        String app_name = getResources().getString(R.string.app_name);
        SharedPreferences prefs = getSharedPreferences(app_name, Context.MODE_PRIVATE);
        if (!prefs.contains("language")) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("language", "en");
            editor.commit();
        }
        return prefs;
    }

    private void changeLocale(SharedPreferences prefs) {
        String language = prefs.getString("language", "");
        if (!language.isEmpty()) {
            Locale lithuanianLocale = new Locale(language);
            Locale.setDefault(lithuanianLocale);
            Resources resources = getResources();
            Configuration configuration = resources.getConfiguration();
            configuration.setLocale(lithuanianLocale);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
    }
}
