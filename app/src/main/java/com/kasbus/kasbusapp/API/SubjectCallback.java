package com.kasbus.kasbusapp.API;

import com.kasbus.kasbusapp.Containers.Subject;

import java.util.List;

public interface SubjectCallback {
    void onSubjectsReceived(List<Subject> subjects);
}
