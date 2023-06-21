package com.kasbus.kasbusapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import java.util.HashSet;
import java.util.Set;

public class FilterActivity extends Activity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_screen);

        sharedPreferences = getSharedPreferences("Selection", Context.MODE_PRIVATE);

        CheckBox checkBoxFILF = findViewById(R.id.checkBoxFILF);
        CheckBox checkBoxTSPMI = findViewById(R.id.checkBoxTSPMI);
        CheckBox checkBoxFF = findViewById(R.id.checkBoxFF);
        CheckBox checkBoxGMC = findViewById(R.id.checkBoxGMC);
        CheckBox checkBoxKNF = findViewById(R.id.checkBoxKNF);
        CheckBox checkBoxTF = findViewById(R.id.checkBoxTF);
        CheckBox checkBoxKOMF = findViewById(R.id.checkBoxKOMF);
        CheckBox checkBoxVM = findViewById(R.id.checkBoxVM);
        CheckBox checkBoxIF = findViewById(R.id.checkBoxIF);
        CheckBox checkBoxEVAF = findViewById(R.id.checkBoxEVAF);
        CheckBox checkBoxMF = findViewById(R.id.checkBoxMF);
        CheckBox checkBoxŠA = findViewById(R.id.checkBoxŠA);
        CheckBox checkBoxFLF = findViewById(R.id.checkBoxFLF);
        CheckBox checkBoxCHGF = findViewById(R.id.checkBoxCHGF);

        RadioButton radioButtonRemote = findViewById(R.id.radioButtonRemote);
        RadioButton radioButtonOnSite = findViewById(R.id.radioButtonOnSite);
        RadioButton radioButtonHybrid = findViewById(R.id.radioButtonHybrid);

        Set<String> savedSelectionSet = sharedPreferences.getStringSet("SavedFaculties", new HashSet<String>());
        String selectedOption = sharedPreferences.getString("SavedOption", "");

        checkBoxFILF.setChecked(savedSelectionSet.contains("FILF"));
        checkBoxTSPMI.setChecked(savedSelectionSet.contains("TSPMI"));
        checkBoxFF.setChecked(savedSelectionSet.contains("FF"));
        checkBoxGMC.setChecked(savedSelectionSet.contains("GMC"));
        checkBoxKNF.setChecked(savedSelectionSet.contains("KNF"));
        checkBoxTF.setChecked(savedSelectionSet.contains("TF"));
        checkBoxKOMF.setChecked(savedSelectionSet.contains("KOMF"));
        checkBoxVM.setChecked(savedSelectionSet.contains("VM"));
        checkBoxIF.setChecked(savedSelectionSet.contains("IF"));
        checkBoxEVAF.setChecked(savedSelectionSet.contains("EVAF"));
        checkBoxMF.setChecked(savedSelectionSet.contains("MF"));
        checkBoxŠA.setChecked(savedSelectionSet.contains("ŠA"));
        checkBoxFLF.setChecked(savedSelectionSet.contains("FLF"));
        checkBoxCHGF.setChecked(savedSelectionSet.contains("CHGF"));

        if (selectedOption.equals("Remote")) {
            radioButtonRemote.setChecked(true);
        } else if (selectedOption.equals("On Site")) {
            radioButtonOnSite.setChecked(true);
        } else if (selectedOption.equals("Hybrid")) {
            radioButtonHybrid.setChecked(true);
        }

        Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> selectionSet = new HashSet<>();
                if (checkBoxFILF.isChecked()) selectionSet.add("FILF");
                if (checkBoxTSPMI.isChecked()) selectionSet.add("TSPMI");
                if (checkBoxFF.isChecked()) selectionSet.add("FF");
                if (checkBoxGMC.isChecked()) selectionSet.add("GMC");
                if (checkBoxKNF.isChecked()) selectionSet.add("KNF");
                if (checkBoxTF.isChecked()) selectionSet.add("TF");
                if (checkBoxKOMF.isChecked()) selectionSet.add("KOMF");
                if (checkBoxVM.isChecked()) selectionSet.add("VM");
                if (checkBoxIF.isChecked()) selectionSet.add("IF");
                if (checkBoxEVAF.isChecked()) selectionSet.add("EVAF");
                if (checkBoxMF.isChecked()) selectionSet.add("MF");
                if (checkBoxŠA.isChecked()) selectionSet.add("ŠA");
                if (checkBoxFLF.isChecked()) selectionSet.add("FLF");
                if (checkBoxCHGF.isChecked()) selectionSet.add("CHGF");

                String selectedOption = "";
                if (radioButtonRemote.isChecked()) selectedOption = "Remote";
                else if (radioButtonOnSite.isChecked()) selectedOption = "On Site";
                else if (radioButtonHybrid.isChecked()) selectedOption = "Hybrid";

                sharedPreferences.edit().putStringSet("SavedFaculties", selectionSet).apply();
                sharedPreferences.edit().putString("SavedOption", selectedOption).apply();

                finish();
            }
        });
    }
}