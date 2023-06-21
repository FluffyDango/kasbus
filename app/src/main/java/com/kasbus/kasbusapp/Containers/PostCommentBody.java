package com.kasbus.kasbusapp.Containers;

public class PostCommentBody {
    private final String subjectId;
    private final String faculty;
    private final String content;

    public PostCommentBody(String subjectId, String faculty, String content) {
        this.subjectId = subjectId;
        this.faculty = faculty;
        this.content = content;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getContent() {
        return content;
    }
}
