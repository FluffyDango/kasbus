package com.kasbus.kasbusapp;

import android.util.Log;

import com.kasbus.kasbusapp.Containers.*;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICalls {
    private List<Subject> subjects;
    private APIInterface api_interface;

    APICalls() {
        api_interface = APIClient.getClient().create(APIInterface.class);
        subjects = new ArrayList<Subject>();
        setSubjects(api_interface.getAllSubjectsEN());
    }
    /**
     * @param language the language in which the subject names will be given.
     *                 Possible values "EN", "LT"
    */
    APICalls(String language) {
        api_interface = APIClient.getClient().create(APIInterface.class);
        subjects = new ArrayList<Subject>();
        useLanguage(language);
    }

    private void setSubjects(Call<List<Subject>> call) {
        call.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                Log.d("CONNECTION", response.code() + "");
                subjects = response.body();
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

    public String[] getId() {
        String[] idList = new String[subjects.size()];
        for (int i = 0; i < subjects.size(); i++) {
            idList[i] = subjects.get(i).getId();
        }
        return idList;
    }

    public String[] getNames() {
        String[] names = new String[subjects.size()];
        for (int i = 0; i < subjects.size(); i++) {
            names[i] = subjects.get(i).getName();
        }
        return names;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     *
     * @param id subject id
     * @return subject if it was found and null if no such ID exists.
     */
    public Subject getSubjectByID(String id) {
        for (Subject subject : subjects) {
            if (subject.getId() == id)
                return subject;
        }
        return null;
    }

    public List<Comments> getCommentsByID(String id) {
        return null;
    }
}
