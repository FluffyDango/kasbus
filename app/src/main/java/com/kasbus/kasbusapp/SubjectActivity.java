package com.kasbus.kasbusapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
import java.util.List;

public class SubjectActivity extends AppCompatActivity implements PostCallback, GetCallback {
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
        List<TextView> r_textview_array = new ArrayList<TextView>() {
            {
                add(findViewById(R.id.r_interest));
                add(findViewById(R.id.r_work));
                add(findViewById(R.id.r_actuality));
                add(findViewById(R.id.r_teaching));
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
        RecyclerView rv_comments = findViewById(R.id.comment_list);

        CommentRecycleViewAdapter adapter = new CommentRecycleViewAdapter(comments);
        rv_comments.setAdapter(adapter);
        rv_comments.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onRatingPostComplete() {
        Toast.makeText(SubjectActivity.this,"Successfully Ratings posted",Toast.LENGTH_SHORT).show();
    }

    public void onCommentPostComplete() {
        Toast.makeText(SubjectActivity.this,"Successfully Ratings posted",Toast.LENGTH_SHORT).show();
        EditText plain_text_input = findViewById(R.id.plain_text_input);
        plain_text_input.setText("");
    }

    private void defaultText() {
        TextView subject_name = findViewById(R.id.subject_name);
        TextView subject_faculty = findViewById(R.id.subject_faculty);
        TextView lecturers_names = findViewById(R.id.lecturers_names);
        TextView credits = findViewById(R.id.credits_amount);
        TextView language_type = findViewById(R.id.language_type);
        Button official_program_site = findViewById(R.id.official_program_site);

        subject_name.setText("?");
        subject_faculty.setText("?");
        lecturers_names.setText("?");
        credits.setText("?");
        language_type.setText("?");
        official_program_site.setOnClickListener(v -> {
        });

        TextView r_interest = findViewById(R.id.r_interest);
        TextView r_work = findViewById(R.id.r_work);
        TextView r_actuality = findViewById(R.id.r_actuality);
        TextView r_teaching = findViewById(R.id.r_teaching);

        r_interest.setText("?");
        r_work.setText("?");
        r_actuality.setText("?");
        r_teaching.setText("?");

        RecyclerView rv_comments = findViewById(R.id.comment_list);
        rv_comments.setAdapter(new CommentRecycleViewAdapter(new ArrayList<>()));

        Button send_button = findViewById(R.id.send_button);
        send_button.setOnClickListener(v -> {
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateInformation() {
        TextView subject_name = findViewById(R.id.subject_name);
        TextView subject_faculty = findViewById(R.id.subject_faculty);
        TextView lecturers_names = findViewById(R.id.lecturers_names);
        TextView credits = findViewById(R.id.credits_amount);
        TextView language = findViewById(R.id.language_type);
        TextView hours = findViewById(R.id.hours_amount);
        TextView delivery = findViewById(R.id.delivery_type);
        TextView exam = findViewById(R.id.exam_value);
        Button official_program_site = findViewById(R.id.official_program_site);

        Subject subject = getIntent().getParcelableExtra("subject");
        if (subject != null) {
            APICalls.fetchRatings(subject.getId());
            APICalls.fetchComments(subject.getId());

            Resources res = getResources();

            subject_name.setText(subject.getName() + "");
            subject_faculty.setText("(" + subject.getFaculty() + ")");
            lecturers_names.setText(subject.getLecturers());
            credits.setText(Integer.toString(subject.getCredits()));

            String lt = res.getString(R.string.lithuanian);
            String en = res.getString(R.string.english);
            String lang_text = subject.getLanguage().equals("lt") ? lt : en;
            language.setText(lang_text);

            hours.setText(Integer.toString(subject.getHours()));

            String delivery_data = subject.getDelivery();
            String delivery_text;
            switch (delivery_data) {
                case "blended":
                    delivery_text = res.getString(R.string.hybrid);
                    break;
                case "online":
                    delivery_text = res.getString(R.string.remote);
                    break;
                case "face":
                    delivery_text = res.getString(R.string.on_site);
                    break;
                default:
                    delivery_text = "";
                    break;
            }
            delivery.setText(delivery_text);

            String yes = res.getString(R.string.yes);
            String no = res.getString(R.string.no);
            String exam_text = subject.getExam() ? yes : no;
            exam.setText(exam_text);

            official_program_site.setOnClickListener(v -> {
                String link = subject.getLink();
                Uri uri = Uri.parse(link);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(launchBrowser);
            });

            updateRatings(subject.getId());
        }
    }

    private void setSpinner() {
        Spinner spinner = findViewById(R.id.facultyDrop);
        String[] faculties = getResources().getStringArray(R.array.all_faculties);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, faculties);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void updateRatings(String subjectId){
        List<TextView> eval_textview_array = new ArrayList<TextView>() {
            {
                add(findViewById(R.id.evaluation_rating_1));
                add(findViewById(R.id.evaluation_rating_2));
                add(findViewById(R.id.evaluation_rating_3));
                add(findViewById(R.id.evaluation_rating_4));
            }
        };
        List<RatingBar> eval_ratingbar_array = new ArrayList<RatingBar>() {
            {
                add(findViewById(R.id.interest_rating));
                add(findViewById(R.id.work_rating));
                add(findViewById(R.id.actuality_rating));
                add(findViewById(R.id.teaching_rating));
            }
        };

        for (int i = 0; i < 4; i++) {
            int index = i;
            eval_ratingbar_array.get(i).setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                String number = Integer.toString((int) rating);
                eval_textview_array.get(index).setText(number);
            });
        }

        String app_name = getResources().getString(R.string.app_name);
        SharedPreferences prefs = getSharedPreferences(app_name, Context.MODE_PRIVATE);
        for (int i = 0; i < eval_textview_array.size(); i++) {
            String key = "eval_" + subjectId + "_" + String.valueOf(i+1);
            int eval = prefs.getInt(key, -1);
            if (eval != -1) {
                eval_textview_array.get(i).setText(Integer.toString(eval));
                eval_ratingbar_array.get(i).setRating(eval);
                eval_ratingbar_array.get(i).setIsIndicator(true);
            }
        }

        Button send_button = findViewById(R.id.send_button);
        send_button.setOnClickListener(v -> {
            for (int i = 0; i < 4; i++)  {
                int rating = (int) eval_ratingbar_array.get(i).getRating();
                if (rating != 0) {
                    SharedPreferences.Editor editor = prefs.edit();
                    String key = "eval_" + subjectId + "_" + String.valueOf(i+1);
                    editor.putInt(key, rating);
                    editor.apply();
                    eval_ratingbar_array.get(i).setIsIndicator(true);
                    String category = "category" + String.valueOf(i+1);
                    APICalls.postRating("testKey2", category, rating);
                }
            }

            Spinner spinner = findViewById(R.id.facultyDrop);
            String comment_faculty = spinner.getSelectedItem().toString();
            EditText plain_text_input = findViewById(R.id.plain_text_input);
            String comment_content = plain_text_input.getText().toString();
            if (!comment_content.isEmpty())
                APICalls.postComment("testKey2", comment_faculty, comment_content);
        });
    }
}