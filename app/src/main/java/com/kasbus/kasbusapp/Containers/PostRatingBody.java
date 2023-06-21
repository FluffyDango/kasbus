package com.kasbus.kasbusapp.Containers;

public class PostRatingBody {
    private final String subjectId;
    private final String category;
    private final int rating;

    public PostRatingBody(String subjectId, String category, int rating) {
        this.subjectId = subjectId;
        this.category = category;
        this.rating = rating;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }
}
