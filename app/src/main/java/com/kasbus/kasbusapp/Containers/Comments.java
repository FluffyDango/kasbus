package com.kasbus.kasbusapp.Containers;

import com.google.gson.annotations.SerializedName;

public class Comments {
    @SerializedName("content")
    public String content;
    @SerializedName("faculty")
    public String faculty;
    @SerializedName("subjectId")
    public String subjectId;
    @SerializedName("timestamp")
    public String timestamp;

    
    public String getContent(){
        return content;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }

    public String getFaculty(){
        return faculty;
    }

    public void setFaculty(String newFaculty){
        this.faculty = newFaculty;
    }

    public String getSubjectId(){
        return subjectId;
    }

    public void setSubjectId(String newSubjectId){
        this.subjectId = newSubjectId;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(String newTimestamp){
        this.timestamp = newTimestamp;
    }
    
}
