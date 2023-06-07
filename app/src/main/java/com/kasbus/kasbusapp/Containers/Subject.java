package com.kasbus.kasbusapp.Containers;

import com.google.gson.annotations.SerializedName;

public class Subject {
    @SerializedName("subjectId")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("faculty")
    private String faculty;
    @SerializedName("credits")
    private int credits;
    @SerializedName("delivery")
    private String delivery;
    @SerializedName("lecturers")
    private String lecturers;
    @SerializedName("language")
    private String language;
    @SerializedName("exam")
    private boolean exam;
    @SerializedName("hours")
    private int hours;
    @SerializedName("link")
    private String link;

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getFaculty() {
        return faculty;
    }
    public int getCredits() {
        return credits;
    }
    public String getDelivery() {
        return delivery;
    }
    public String getLecturers() {
        return lecturers;
    }
    public String getLanguage() {
        return language;
    }
    public Boolean getExam() {
        return exam;
    }
    public int getHours() {
        return hours;
    }
    public String getLink() {
        return link;
    }
}
