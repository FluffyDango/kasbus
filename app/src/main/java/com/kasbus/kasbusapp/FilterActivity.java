package com.kasbus.kasbusapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends Activity {
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_screen);

        prefs = getSharedPreferences("filter", Context.MODE_PRIVATE);

        setFacultyCheckboxes();
        setDeliveryGroup();
        setLanguageGroup();

        Button doneButton = findViewById(R.id.doneButton);
        Button emptyButton = findViewById(R.id.emptyButton);
        doneButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("need_to_filter", true);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        emptyButton.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            new Handler().postDelayed(() -> {
                setFacultyCheckboxes();
                setDeliveryGroup();
                setLanguageGroup();
            }, 50);
        });
    }

    private void setFacultyCheckboxes() {
        GridLayout grid_faculties = findViewById(R.id.grid_faculties);
        String[] faculties = getResources().getStringArray(R.array.all_faculties);

        if (grid_faculties.getChildCount() == 0) {
            List<CheckBox> checkbox_list = new ArrayList<>(faculties.length);
            for (int i = 0; i < faculties.length; i++) {
                int [][] states = {{android.R.attr.state_checked}, {}};
                int sand_color = Color.parseColor("#C0C0C0");
                int [] colors = {sand_color, sand_color};

                checkbox_list.add(new CheckBox(getApplicationContext()));
                checkbox_list.get(i).setText(faculties[i]);
                checkbox_list.get(i).setButtonTintList(new ColorStateList(states, colors));
                checkbox_list.get(i).setTextColor(Color.WHITE);
                checkbox_list.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                String key = "faculty_filter_" + Integer.toString(i+1);
                checkbox_list.get(i).setChecked(!prefs.contains(key));
                checkbox_list.get(i).setOnCheckedChangeListener((buttonView, isChecked) -> {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(key, !isChecked);
                    editor.apply();
                });
                grid_faculties.addView(checkbox_list.get(i));
            }
        } else {
            for (int i = 0; i < faculties.length; i++) {
                String key = "faculty_filter_" + Integer.toString(i+1);
                CheckBox button = (CheckBox) grid_faculties.getChildAt(i);
                button.setOnCheckedChangeListener(null); // Remove previous listener temporarily
                button.setChecked(!prefs.contains(key));
                button.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(key, !isChecked);
                    editor.apply();
                });
            }
        }
    }

    private void setDeliveryGroup() {
        int selected_delivery = prefs.getInt("filter_del_button_id", 0);
        RadioButton delivery_button;
        if (selected_delivery != 0) {
            delivery_button = findViewById(selected_delivery);
        } else {
            delivery_button = findViewById(R.id.filter_delivery_any);
        }
        delivery_button.setChecked(true);

        RadioGroup delivery_group = findViewById(R.id.delivery_group);
        delivery_group.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton button = findViewById(checkedId);
            String sel_button_text = button.getText().toString();
            String remote = getResources().getString(R.string.remote);
            String on_site = getResources().getString(R.string.on_site);
            String hybrid = getResources().getString(R.string.hybrid);
            String delivery;
            if(sel_button_text.equals(remote)) {
                delivery = "online";
            } else if(sel_button_text.equals(on_site)) {
                delivery = "live";
            } else if(sel_button_text.equals(hybrid)) {
                delivery = "blended";
            } else {
                delivery = "any";
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("filter_delivery", delivery);
            editor.putInt("filter_del_button_id", checkedId);
            editor.apply();
        });
    }

    private void setLanguageGroup() {
        int selected_language = prefs.getInt("filter_lang_button_id", 0);
        RadioButton lang_button;
        if (selected_language != 0) {
            lang_button = findViewById(selected_language);
        } else {
            lang_button = findViewById(R.id.filter_language_any);
        }
        lang_button.setChecked(true);

        RadioGroup language_group = findViewById(R.id.language_group);
        language_group.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton button = findViewById(checkedId);
            String sel_button_text = button.getText().toString();
            String english = getResources().getString(R.string.english);
            String lithuanian = getResources().getString(R.string.lithuanian);
            String lang;
            if (sel_button_text.equals(english)) {
                lang = "en";
            } else if (sel_button_text.equals(lithuanian)) {
                lang = "lt";
            } else {
                lang = "any";
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("filter_language", lang);
            editor.putInt("filter_lang_button_id", checkedId);
            editor.apply();
        });
    }
}
