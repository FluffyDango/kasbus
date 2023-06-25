package com.kasbus.kasbusapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.kasbus.kasbusapp.API.*;
import com.kasbus.kasbusapp.Containers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SubjectCallback {

    private SharedPreferences filter_prefs;
    private SharedPreferences general_prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        general_prefs = getSharedPreferences("kasbus", Context.MODE_PRIVATE);
        initPreferences();
        filter_prefs = getSharedPreferences("filter", Context.MODE_PRIVATE);
        changeLocale();
        setContentView(R.layout.activity_main);

        String lang = general_prefs.getString("language", "");
        APICalls.setSubjectCallback(this);
        APICalls.fetchSubjects(lang);

        setFilterOnClick();
        setButtonText(lang);
        setLangButtonOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout search_bar_layout = findViewById(R.id.search_and_filter);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQuery("", false);
        search_bar_layout.requestFocus();
    }


    private List<Subject> filterSubjects() {
        List<Subject> current_subjects = APICalls.getSubjects();
        List<Subject> filtered_subjects = new ArrayList<>(current_subjects);
        filtered_subjects.addAll(current_subjects);

        filterSubjectsByFaculty(filtered_subjects);
        filterSubjectsByDelivery(filtered_subjects);
        filterSubjectsByLanguage(filtered_subjects);

        return filtered_subjects;
    }

    // subjects are passed by reference so they are changed
    private void filterSubjectsByFaculty(List<Subject> subjects) {
        String[] faculties = getResources().getStringArray(R.array.all_faculties);
        for (int i = 0; i < faculties.length; i++) {
            String key = "faculty_filter_" + String.valueOf(i + 1);
            if (filter_prefs.getBoolean(key, false)) {
                for (int j = 0; j < subjects.size(); j++) {
                    String faculty = subjects.get(j).getFaculty();
                    if (faculty.equals(faculties[i])) {
                        subjects.remove(j);
                        j--;
                    }
                }
            }
        }
    }

    // subjects are passed by reference so they are changed
    private void filterSubjectsByDelivery(List<Subject> subjects) {
        String sel_delivery = filter_prefs.getString("filter_delivery", "");
        if (sel_delivery.equals("any")) {
            return;
        } else if (sel_delivery.isEmpty()) {
            Log.d("kasbus", "filter_delivery is empty");
        } else {
            for (int i = 0; i < subjects.size(); i++) {
                String subject_language = subjects.get(i).getDelivery();
                if (!subject_language.equals(sel_delivery)) {
                    subjects.remove(i);
                    i--;
                }
            }
        }
    }

    // subjects are passed by reference so they are changed
    private void filterSubjectsByLanguage(List<Subject> subjects) {
        String sel_language = filter_prefs.getString("filter_language", "");
        if (sel_language.equals("any")) {
            return;
        } else if (sel_language.isEmpty()) {
            Log.d("kasbus", "filter_language is empty");
        } else {
            for (int i = 0; i < subjects.size(); i++) {
                String subject_language = subjects.get(i).getLanguage();
                if (!subject_language.equals(sel_language)) {
                    subjects.remove(i);
                    i--;
                }
            }
        }
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
                searchSubjects(text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                if (text.isEmpty()) {
                    List<Subject> subjects;
                    if (APICalls.getFilteredSubjects() != null) {
                        subjects = APICalls.getFilteredSubjects();
                    } else if (APICalls.getSubjects() != null) {
                        subjects = APICalls.getSubjects();
                    } else {
                        return true;
                    }

                    SubjectRecycleViewAdapter adapter = (SubjectRecycleViewAdapter) rv_subjects.getAdapter();
                    assert adapter != null;

                    adapter.setSubjects(subjects);
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
     *
     */
    private void searchSubjects(String query) {
        RecyclerView rv_subjects = findViewById(R.id.bus_list);
        SubjectRecycleViewAdapter adapter = (SubjectRecycleViewAdapter) rv_subjects.getAdapter();
        assert adapter != null;

        List<Subject> subjects;
        if (APICalls.getFilteredSubjects() != null) {
            subjects = APICalls.getFilteredSubjects();
        } else if (APICalls.getSubjects() != null) {
            subjects = APICalls.getSubjects();
        } else {
            return;
        }

        List<Integer> update_index = new ArrayList<>(16);
        List<Subject> filteredSubjects = new ArrayList<>();
        for (int i = 0; i < subjects.size(); i++) {
            String name = subjects.get(i).getName().toLowerCase();
            if (name.contains(query.toLowerCase())) {
                update_index.add(i);
                filteredSubjects.add(subjects.get(i));
            }
        }
        adapter.setSubjects(filteredSubjects);
        for (int i = 0; i < update_index.size(); i++) {
            adapter.notifyItemChanged(update_index.get(i));
        }
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
            Intent intent = new Intent(this, FilterActivity.class);
            launchFilterForResult.launch(intent);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    ActivityResultLauncher<Intent> launchFilterForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    if (data.getBooleanExtra("need_to_filter", false)) {
                        List<Subject> filtered_subjects = filterSubjects();
                        APICalls.setFilteredSubjects(filtered_subjects);

                        RecyclerView rv_subjects = findViewById(R.id.bus_list);
                        SubjectRecycleViewAdapter adapter = new SubjectRecycleViewAdapter(filtered_subjects);
                        rv_subjects.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

    private void setLangButtonOnClick() {
        Button lang_button = findViewById(R.id.lang_button);
        lang_button.setOnClickListener(view -> {
            APICalls.setSubjects(null);
            APICalls.setFilteredSubjects(null);

            String current_language = general_prefs.getString("language", "");
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

            SharedPreferences.Editor editor = general_prefs.edit();
            APICalls.fetchSubjects(next_language);
            editor.putString("language", next_language);
            editor.apply();
            setButtonText(next_language);

            changeLocale();

            // Reload activity to update language
            finish();
            startActivity(getIntent());
        });
    }

    @SuppressLint("ApplySharedPref")
    private void initPreferences() {
        if (!general_prefs.contains("language")) {
            SharedPreferences.Editor editor = general_prefs.edit();
            editor.putString("language", "en");
            editor.commit();
        }
    }

    private void changeLocale() {
        String language = general_prefs.getString("language", "");
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
