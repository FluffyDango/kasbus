package com.kasbus.kasbusapp.Containers;

import com.google.gson.annotations.SerializedName;

public class Comments {
    @SerializedName("content")
    private String content;
    @SerializedName("faculty")
    private String faculty;
    @SerializedName("subjectId")
    private String subjectId;
    @SerializedName("timestamp")
    private String timestamp;

    
    public String getContent(){
        return content;
    }

//    public void setContent(String newContent){
//        this.content = newContent;
//    }

    public String getFaculty(){
        return faculty;
    }

//    public void setFaculty(String newFaculty){
//        this.faculty = newFaculty;
//    }

    public String getSubjectId(){
        return subjectId;
    }

//    public void setSubjectId(String newSubjectId){
//        this.subjectId = newSubjectId;
//    }

    public String getTimestamp(){
        return timestamp;
    }

//    public void setTimestamp(String newTimestamp){
//        this.timestamp = newTimestamp;
//    }
    
}
