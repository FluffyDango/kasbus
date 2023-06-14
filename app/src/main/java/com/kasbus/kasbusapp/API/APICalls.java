package com.kasbus.kasbusapp.API;

import android.util.Log;

import com.kasbus.kasbusapp.Containers.*;
import com.kasbus.kasbusapp.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICalls {
    private List<Subject> subjects;
    private APIInterface api_interface;
    private SubjectCallback api_callback;


    public interface SubjectCallback {
        void onSubjectsReceived(List<Subject> subjects);
    }

    public APICalls() {
        api_interface = APIClient.getClient().create(APIInterface.class);
        setSubjects(api_interface.getAllSubjectsEN());
    }
    /**
     * @param language the language in which the subject names will be given.
     *                 Possible values "EN", "LT"
    */
    public APICalls(String language) {
        api_interface = APIClient.getClient().create(APIInterface.class);
        useLanguage(language);
    }

    public void setAPICallBack(SubjectCallback api_callback) {
        this.api_callback = api_callback;
    }

    private void setSubjects(Call<List<Subject>> call) {
        call.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                Log.d("CONNECTION", response.code() + "");
                subjects = response.body();
                api_callback.onSubjectsReceived(subjects);
            }
            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                call.cancel();
            }
        });
    }


    /**
     * Changes the internal subjects list to use the specified language
     *
     * @param language the language in which the subject names will be given.
     *                 Possible values "EN", "LT"
     */
    public void useLanguage(String language) {
        if (language == "LT")
            setSubjects(api_interface.getAllSubjectsLT());
        else if (language == "EN")
            setSubjects(api_interface.getAllSubjectsEN());
        else
            Log.d("API", "useLanguage: invalid language " + language);
    }

    public List<Subject> getSubjects() {
        Log.d("test", "subject size - " + Integer.toString(subjects.size()));
        return subjects;
    }

    /**
     *
     * @param id subject id
     * @return subject if it was found and null if no such ID exists.
     */
    public Subject getSubjectById(String id) {
        for (Subject subject : subjects) {
            if (subject.getId() == id)
                return subject;
        }
        return null;
    }

    public List<Comment> getCommentsById(String id) {
        return null;
    }
    public Ratings getRatingsById(String id) {
        return null;
    }
}
