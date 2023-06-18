package com.kasbus.kasbusapp.Containers;

import com.google.gson.annotations.SerializedName;

public class PostRatingResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("subjectId")
    private String subjectId;
    @SerializedName("category")
    private String category;
    @SerializedName("faculty")
    private String rating;
    @SerializedName("timestamp")
    private String timestamp;


    public String getId() {
        return id;
    }
    public String getSubjectId() {
        return subjectId;
    }
    public String getCategory() {
        return category;
    }
    public String getRating() {
        return rating;
    }
    public String getTimestamp() {
        return timestamp;
    }
}
