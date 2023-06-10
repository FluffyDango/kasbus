package com.kasbus.kasbusapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kasbus.kasbusapp.Containers.Ratings;
import com.kasbus.kasbusapp.Containers.Subject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout block0 = findViewById(R.id.block0);
        LinearLayout block1 = findViewById(R.id.block1);
        LinearLayout block2 = findViewById(R.id.block2);
        LinearLayout block3 = findViewById(R.id.block3);
        LinearLayout block4 = findViewById(R.id.block3);
        LinearLayout block5 = findViewById(R.id.block3);
        LinearLayout block6 = findViewById(R.id.block3);
        LinearLayout block7 = findViewById(R.id.block3);

        block0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        block1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        block2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        block3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        block4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }
        });

        block5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }
        });

        block6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }
        });

        block7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }
        });

        APICalls api_calls = new APICalls();

        String[] names = api_calls.getNames();

//        List<Subject> subjectList = api_calls.getSubjectsEN();

//        TextView responseText = (TextView) findViewById(R.id.Aesthetics);
//        TextView text = (TextView) findViewById(R.id.AppliedSocialPsychology);
    }
}
