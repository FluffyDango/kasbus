package com.kasbus.kasbusapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kasbus.kasbusapp.API.APICalls;
import com.kasbus.kasbusapp.API.GetCallback;
import com.kasbus.kasbusapp.API.PostCallback;
import com.kasbus.kasbusapp.Containers.Comment;
import com.kasbus.kasbusapp.Containers.Ratings;
import com.kasbus.kasbusapp.Containers.Subject;

import java.util.List;

public class SubjectActivity extends AppCompatActivity implements PostCallback, GetCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        Spinner spinner = findViewById(R.id.facultyDrop);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.FACULTIES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        updateInformation();
    }

    public void onRatingsReceived(Ratings ratings) {
        Toast.makeText(SubjectActivity.this,"Ratings GetReceived",Toast.LENGTH_SHORT).show();

        TextView r_interest = (TextView) findViewById(R.id.r_interest);
        TextView r_work = (TextView) findViewById(R.id.r_work);
        TextView r_actuality = (TextView) findViewById(R.id.r_actuality);
        TextView r_teaching = (TextView) findViewById(R.id.r_teaching);

        String category1 = Double.toString(ratings.getCategory1());
        r_interest.setText(category1);
        String category2 = Double.toString(ratings.getCategory2());
        r_work.setText(category2);
        String category3 = Double.toString(ratings.getCategory3());
        r_actuality.setText(category3);
        String category4 = Double.toString(ratings.getCategory4());
        r_teaching.setText(category4);
    }

    public void onCommentsReceived(List<Comment> comments) {
        Toast.makeText(SubjectActivity.this,"Comments getReceived",Toast.LENGTH_SHORT).show();
    }


    public void onRatingPostComplete() {
//        EditText plain_text_input = (EditText) findViewById(R.id.plain_text_input);
        Toast.makeText(SubjectActivity.this,"Successfully posted",Toast.LENGTH_SHORT).show();
//        plain_text_input.setText("Successful");
    }

    public void onCommentPostComplete() {
        Toast.makeText(SubjectActivity.this,"Successfully posted",Toast.LENGTH_SHORT).show();
//        EditText plain_text_input = (EditText) findViewById(R.id.plain_text_input);
//        plain_text_input.setText("");
    }

    public void updateInformation() {
//        if (getSubject() == null) {
//            Log.e("test", "subject is null");
//            return;
//        }
        Button send_button = (Button) findViewById(R.id.send_button);
        send_button.setOnClickListener(view -> {
            APICalls.postComment("testKey2", "MIF", "I love android");
        });

        TextView subject_name = (TextView) findViewById(R.id.subject_name);
        TextView subject_faculty = (TextView) findViewById(R.id.subject_faculty);
        TextView lecturers_names = (TextView) findViewById(R.id.lecturers_names);
        TextView credits = (TextView) findViewById(R.id.credits_amount);
        TextView language_type = (TextView) findViewById(R.id.language_type);
        Button official_program_site = (Button) findViewById(R.id.official_program_site);

//        Subject subject = getSubject();
//        subject_name.setText(subject.getName());
//        subject_faculty.setText(subject.getFaculty());
//        lecturers_names.setText(subject.getLecturers());
//        credits.setText(subject.getCredits());
//        language_type.setText(subject.getLanguage());

        official_program_site.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
//            intent.setData(Uri.parse(subject.getLink()));
            startActivity(intent);
        });
    }
}