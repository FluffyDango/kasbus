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
    private Integer credits;
    @SerializedName("delivery")
    private String delivery;
    @SerializedName("lecturers")
    private String lecturers;
    @SerializedName("language")
    private String language;
    @SerializedName("exam")
    private Boolean exam;
    @SerializedName("hours")
    private Integer hours;
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
    public Integer getCredits() {
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
    public Integer getHours() {
        return hours;
    }
    public String getLink() {
        return link;
    }
}
