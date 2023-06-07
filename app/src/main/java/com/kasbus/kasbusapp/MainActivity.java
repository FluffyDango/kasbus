package com.kasbus.kasbusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        APICalls api_calls = new APICalls();

//        String[] idList = api_calls.getIdList();
        List<Subject> subjectList = api_calls.getSubjectsEN();

        TextView responseText = (TextView) findViewById(R.id.Aesthetics);

        TextView text = (TextView) findViewById(R.id.AppliedSocialPsychology);

    }
}

