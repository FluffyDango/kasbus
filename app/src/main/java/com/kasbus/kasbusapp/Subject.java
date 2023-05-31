package com.kasbus.kasbusapp;

import com.google.gson.annotations.SerializedName;

public class Subject {
    private Object id;

    @SerializedName("name")
    private String superName;


    public Subject(String name) {
        this.superName = name;
    }

    public String getName() {
        return superName;
    }
}
