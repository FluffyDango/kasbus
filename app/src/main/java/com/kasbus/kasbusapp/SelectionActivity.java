package com.kasbus.kasbusapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectionActivity extends Activity {

    String[] faculties = new String[] {"FILF", "TSPMI", "FF", "GMC", "KNF", "TF", "KOMF", "VM", "IF", "EVAF", "MF", "ŠA", "FF", "FLF", "CHGF"};
    boolean[] selectedFaculties;
    String[] options = new String[] {"Remote", "On Site", "Hybrid"};
    boolean[] selectedOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_screen);

        selectedFaculties = new boolean[faculties.length];
        selectedOptions = new boolean[options.length];

        Button facultySelectButton = findViewById(R.id.facultySelectButton);
        Button remoteOnSiteButton = findViewById(R.id.remoteOnSiteButton);
        Button doneButton = findViewById(R.id.doneButton);

        facultySelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectionActivity.this);
                builder.setTitle("Select Faculties")
                        .setMultiChoiceItems(faculties, selectedFaculties, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                                selectedFaculties[i] = isChecked;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
            }
        });

        remoteOnSiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectionActivity.this);
                builder.setTitle("Remote or On Site")
                        .setMultiChoiceItems(options, selectedOptions, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                                selectedOptions[i] = isChecked;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}