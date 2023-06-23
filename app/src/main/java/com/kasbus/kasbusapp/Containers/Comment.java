package com.kasbus.kasbusapp.Containers;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("content")
    private String content;
    @SerializedName("faculty")
    private String faculty;
    @SerializedName("subjectId")
    private String subjectId;
    @SerializedName("timestamp")
    private Timestamp timestamp;

    public class Timestamp {
        @SerializedName("_seconds")
        public Integer seconds;
        @SerializedName("_nanoseconds")
        public Integer nano_seconds;
    }


    public String getContent() {
        return content;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
