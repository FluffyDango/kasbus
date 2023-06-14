package com.kasbus.kasbusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kasbus.kasbusapp.API.APICalls;
import com.kasbus.kasbusapp.Containers.Subject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements APICalls.SubjectCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LinearLayout block0 = findViewById(R.id.block0);
//        LinearLayout block1 = findViewById(R.id.block1);
//        LinearLayout block2 = findViewById(R.id.block2);
//        LinearLayout block3 = findViewById(R.id.block3);
//        LinearLayout block4 = findViewById(R.id.block3);
//        LinearLayout block5 = findViewById(R.id.block3);
//        LinearLayout block6 = findViewById(R.id.block3);
//        LinearLayout block7 = findViewById(R.id.block3);
//
//        block0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        block1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        block2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        block3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        block4.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                        startActivity(intent);
//                    }
//        });
//
//        block5.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                        startActivity(intent);
//                    }
//        });
//
//        block6.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                        startActivity(intent);
//                    }
//        });
//
//        block7.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                        startActivity(intent);
//                    }
//        });

        APICalls api_calls = new APICalls();
        api_calls.setAPICallBack(this);
    }

    @Override
    public void onSubjectsReceived(List<Subject> subjects) {
        RecyclerView rvSubjects = (RecyclerView) findViewById(R.id.bus_list);
        SubjectAdapter adapter = new SubjectAdapter(subjects);
        rvSubjects.setAdapter(adapter);
        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
    }
}
