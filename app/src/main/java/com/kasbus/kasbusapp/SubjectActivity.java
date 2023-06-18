package com.kasbus.kasbusapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.kasbus.kasbusapp.API.APICalls;
import com.kasbus.kasbusapp.API.PostRatingCallback;
import com.kasbus.kasbusapp.Containers.PostRatingResponse;

public class SubjectActivity extends AppCompatActivity implements PostRatingCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        APICalls.setPostRatingCallback(this);

        Button send_button = (Button) findViewById(R.id.send_button);
        send_button.setOnClickListener(view -> {
            APICalls.postRating("testKey2", "category3", 3);
        });
    }

    public void onRatingPostComplete(PostRatingResponse response) {
        EditText plain_text_input = (EditText) findViewById(R.id.plain_text_input);
        Log.d("API", response.getRating());
//        plain_text_input.setText(response.getRating());
    }
}