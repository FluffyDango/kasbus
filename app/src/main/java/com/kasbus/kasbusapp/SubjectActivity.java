package com.kasbus.kasbusapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kasbus.kasbusapp.API.APICalls;
import com.kasbus.kasbusapp.API.GetCallback;
import com.kasbus.kasbusapp.API.PostCallback;
import com.kasbus.kasbusapp.Containers.Comment;
import com.kasbus.kasbusapp.Containers.Ratings;
import com.kasbus.kasbusapp.Containers.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubjectActivity extends AppCompatActivity implements PostCallback, GetCallback {
    private String comment_faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        setSpinner();
        APICalls.setGetCallback(this);
        APICalls.setPostCallback(this);
        defaultText();
        updateInformation();
    }

    @SuppressLint("SetTextI18n")
    public void onRatingsReceived(Ratings ratings) {
//        Toast.makeText(SubjectActivity.this,"Ratings GetReceived",Toast.LENGTH_SHORT).show();
        List<TextView> r_textview_array = new ArrayList<TextView>() {
            {
                add((TextView) findViewById(R.id.r_interest));
                add((TextView) findViewById(R.id.r_work));
                add((TextView) findViewById(R.id.r_actuality));
                add((TextView) findViewById(R.id.r_teaching));
            }
        };
        List<Double> r_value_array = new ArrayList<Double>() {
            {
                add(ratings.getCategory1());
                add(ratings.getCategory2());
                add(ratings.getCategory3());
                add(ratings.getCategory4());
            }
        };

        for (int i = 0; i < r_value_array.size(); i++) {
            int value = (int) Math.round(r_value_array.get(i));
            if (value == 0)  {
                r_textview_array.get(i).setText("-");
            } else {
                r_textview_array.get(i).setText(Integer.toString(value));
            }
        }
    }

    public void onCommentsReceived(List<Comment> comments) {
        Toast.makeText(SubjectActivity.this,"Comments getReceived",Toast.LENGTH_SHORT).show();

        RecyclerView rv_comments = (RecyclerView) findViewById(R.id.comment_list);

        CommentRecycleViewAdapter adapter = new CommentRecycleViewAdapter(comments);
        rv_comments.setAdapter(adapter);
        rv_comments.setLayoutManager(new LinearLayoutManager(this));
    }


    public void onRatingPostComplete() {
        Toast.makeText(SubjectActivity.this,"Successfully posted",Toast.LENGTH_SHORT).show();
    }

    public void onCommentPostComplete() {
        Toast.makeText(SubjectActivity.this,"Successfully posted",Toast.LENGTH_SHORT).show();
        EditText plain_text_input = (EditText) findViewById(R.id.plain_text_input);
        plain_text_input.setText("");
    }

    private void defaultText() {
        TextView subject_name = (TextView) findViewById(R.id.subject_name);
        TextView subject_faculty = (TextView) findViewById(R.id.subject_faculty);
        TextView lecturers_names = (TextView) findViewById(R.id.lecturers_names);
        TextView credits = (TextView) findViewById(R.id.credits_amount);
        TextView language_type = (TextView) findViewById(R.id.language_type);
        Button official_program_site = (Button) findViewById(R.id.official_program_site);

        subject_name.setText("?");
        subject_faculty.setText("?");
        lecturers_names.setText("?");
        credits.setText("?");
        language_type.setText("?");
        official_program_site.setOnClickListener(v -> {
        });

        TextView r_interest = (TextView) findViewById(R.id.r_interest);
        TextView r_work = (TextView) findViewById(R.id.r_work);
        TextView r_actuality = (TextView) findViewById(R.id.r_actuality);
        TextView r_teaching = (TextView) findViewById(R.id.r_teaching);

        r_interest.setText("?");
        r_work.setText("?");
        r_actuality.setText("?");
        r_teaching.setText("?");

        RecyclerView rv_comments = (RecyclerView) findViewById(R.id.comment_list);

        rv_comments.setAdapter(new CommentRecycleViewAdapter(new ArrayList<>()));
    }

    @SuppressLint("SetTextI18n")
    private void updateInformation() {
        TextView subject_name = (TextView) findViewById(R.id.subject_name);
        TextView subject_faculty = (TextView) findViewById(R.id.subject_faculty);
        TextView lecturers_names = (TextView) findViewById(R.id.lecturers_names);
        TextView credits = (TextView) findViewById(R.id.credits_amount);
        TextView language = (TextView) findViewById(R.id.language_type);
        TextView hours = (TextView) findViewById(R.id.hours_amount);
        Button official_program_site = (Button) findViewById(R.id.official_program_site);

        Subject subject = (Subject) getIntent().getParcelableExtra("subject");
        if (subject != null) {
            APICalls.fetchRatings(subject.getId());
            APICalls.fetchComments(subject.getId());

            subject_name.setText(subject.getName() + "");
            subject_faculty.setText("(" + subject.getFaculty() + ")");
            lecturers_names.setText(subject.getLecturers());
            credits.setText(Integer.toString(subject.getCredits()));
            language.setText(subject.getLanguage());
            hours.setText(Integer.toString(subject.getHours()));

            official_program_site.setOnClickListener(v -> {
                String link = subject.getLink();
                Log.d("test", link);
                Uri uri = Uri.parse(link);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(launchBrowser);
            });
        }

        List<TextView> eval_textview_array = new ArrayList<TextView>() {
            {
                add((TextView) findViewById(R.id.evaluation_rating_1));
                add((TextView) findViewById(R.id.evaluation_rating_2));
                add((TextView) findViewById(R.id.evaluation_rating_3));
                add((TextView) findViewById(R.id.evaluation_rating_4));
            }
        };

        SharedPreferences prefs = getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        for (int i = 0; i < eval_textview_array.size(); i++) {
            int eval = prefs.getInt("eval_" + i, -1);
            if (eval != -1) {
                eval_textview_array.get(i).setText(Integer.toString(eval));
            }
        }

        Button send_button = (Button) findViewById(R.id.send_button);
        send_button.setOnClickListener(v -> {
            EditText plain_text_input = (EditText) findViewById(R.id.plain_text_input);
            String comment_content = plain_text_input.getText().toString();
            APICalls.postComment("testKey2", comment_faculty, comment_content);
        });
    }

    private void setSpinner() {
        Spinner spinner = findViewById(R.id.facultyDrop);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.FACULTIES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                comment_faculty = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }
}