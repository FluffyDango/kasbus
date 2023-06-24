package com.kasbus.kasbusapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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
        super.onCreate(Bundle.EMPTY);
        setContentView(R.layout.filter_screen);

        prefs = getSharedPreferences("filter", Context.MODE_PRIVATE);

        setFacultyCheckboxes();
        setDeliveryGroup();
        setLanguageGroup();

        Button doneButton = findViewById(R.id.doneButton);
        Button emptyButton = findViewById(R.id.emptyButton);

        doneButton.setOnClickListener(v -> {
            finish();
        });
        emptyButton.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            setFacultyCheckboxes();
            setDeliveryGroup();
            setLanguageGroup();
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

                String key = "faculty_filter" + Integer.toString(i);
                checkbox_list.get(i).setChecked(!prefs.contains(key));
                checkbox_list.get(i).setOnCheckedChangeListener((buttonView, isChecked) -> {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt(key, buttonView.getId());
                    editor.apply();
                });

                grid_faculties.addView(checkbox_list.get(i));
            }
        } else {
            for (int i = 0; i < grid_faculties.getChildCount(); i++) {
                CheckBox button = (CheckBox) grid_faculties.getChildAt(i);
                button.setChecked(true);
            }
        }
    }

    private void setDeliveryGroup() {
        int selected_delivery = prefs.getInt("filter_delivery", 0);
        RadioButton delivery_button;
        if (selected_delivery != 0) {
            delivery_button = findViewById(selected_delivery);
        } else {
            delivery_button = findViewById(R.id.filter_delivery_any);
        }
        delivery_button.setChecked(true);

        RadioGroup delivery_group = findViewById(R.id.delivery_group);
        delivery_group.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("filter_delivery", checkedId);
            editor.apply();
        });
    }

    private void setLanguageGroup() {
        int selected_language = prefs.getInt("filter_language", 0);
        RadioButton lang_button;
        if (selected_language != 0) {
            lang_button = findViewById(selected_language);
        } else {
            lang_button = findViewById(R.id.filter_language_any);
        }
        lang_button.setChecked(true);

        RadioGroup language_group = findViewById(R.id.language_group);
        language_group.setOnCheckedChangeListener((group, checkedId) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("filter_language", checkedId);
            editor.apply();
        });
    }
}
