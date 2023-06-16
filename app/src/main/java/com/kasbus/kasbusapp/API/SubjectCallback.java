package com.kasbus.kasbusapp.API;

import com.kasbus.kasbusapp.Containers.Subject;

import java.util.List;

import retrofit2.Call;

public interface SubjectCallback {
    void onSubjectsReceived(List<Subject> subjects);
    void onSubjectFailure(String language);
}
