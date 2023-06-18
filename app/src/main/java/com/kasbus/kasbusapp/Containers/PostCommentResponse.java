package com.kasbus.kasbusapp.Containers;

import com.google.gson.annotations.SerializedName;

public class PostCommentResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("subjectId")
    private String subjectId;
    @SerializedName("content")
    private String content;
    @SerializedName("faculty")
    private String faculty;
    @SerializedName("timestamp")
    private String timestamp;


    public String getId() {
        return id;
    }
    public String getSubjectId() {
        return subjectId;
    }
    public String getContent() {
        return content;
    }
    public String getFaculty() {
        return faculty;
    }
    public String getTimestamp() {
        return timestamp;
    }
}
