package com.kasbus.kasbusapp.API;

import com.kasbus.kasbusapp.Containers.Comment;

import java.util.List;

public interface CommentCallback {
    void onCommentsReceived(List<Comment> comments);
}
