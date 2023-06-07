package com.kasbus.kasbusapp;

import android.util.Log;

import com.kasbus.kasbusapp.Containers.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICalls {
    private List<Subject> subjects;
    private boolean isSubjectsNameInEnglish;
    private APIInterface api_interface;

    APICalls() {
        api_interface = APIClient.getClient().create(APIInterface.class);
        setSubjects(api_interface.getAllSubjectsEN());

    }

    private void setSubjects(Call<List<Subject>> call) {
        if (call == api_interface.getAllSubjectsEN())
            isSubjectsNameInEnglish = true;
        else
            isSubjectsNameInEnglish = false;
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

    public String[] getId() {
        String[] idList = new String[subjects.size()];
        for (int i = 0; i < subjects.size(); i++) {
            idList[i] = subjects.get(i).getId();
        }
        return idList;
    }

    public String[] getNamesLT() {
        if (isSubjectsNameInEnglish)
            setSubjects(api_interface.getAllSubjectsLT());

        String[] idList = new String[subjects.size()];
        for (int i = 0; i < subjects.size(); i++) {
            idList[i] = subjects.get(i).getId();
        }
        return idList;
    }
    public String[] getNamesEN() {
        if (!isSubjectsNameInEnglish)
            setSubjects(api_interface.getAllSubjectsEN());

        String[] idList = new String[subjects.size()];
        for (int i = 0; i < subjects.size(); i++) {
            idList[i] = subjects.get(i).getId();
        }
        return idList;
    }

    public List<Subject> getSubjectsLT() {
        if (isSubjectsNameInEnglish)
            setSubjects(api_interface.getAllSubjectsLT());

        return subjects;
    }
    public List<Subject> getSubjectsEN() {
        if (!isSubjectsNameInEnglish)
            setSubjects(api_interface.getAllSubjectsEN());

        return subjects;
    }
}
