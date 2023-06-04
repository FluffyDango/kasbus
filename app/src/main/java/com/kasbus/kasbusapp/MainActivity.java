package com.kasbus.kasbusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.kasbus.kasbusapp.Containers.Ratings;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APICalls api_calls = new APICalls();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

//        TextView responseText = (TextView) findViewById(R.id.Aesthetics);
//        api_calls.setRatings(apiInterface.getSubjectRatings(), responseText);
    }
}

