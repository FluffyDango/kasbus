package com.kasbus.kasbusapp.Containers;

import com.google.gson.annotations.SerializedName;

public class Subject {
    @SerializedName("name")
    public String name;
    @SerializedName("faculty")
    public String faculty;
    @SerializedName("credits")
    public int credits;
    @SerializedName("delivery")
    public String delivery;
    @SerializedName("lecturers")
    public String lecturers;
    @SerializedName("language")
    public String language;
    @SerializedName("exam")
    public boolean exam;
    @SerializedName("hours")
    public int hours;
    @SerializedName("link")
    public String link;

    /*
    public String getId(){
        return id;
    }

    public void setId(String newId){
        this.id = newId;
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getFaculty(){
        return faculty;
    }

    public void setFaculty(String newFaculty){
        this.faculty = newFaculty;
    }

    public int setCredits(){
        return credits;
    }

    public void getCredits(int newCredits){
        this.credits = newCredits;
    }
    */
}
