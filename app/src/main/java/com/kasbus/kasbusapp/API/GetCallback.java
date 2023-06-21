package com.kasbus.kasbusapp.API;

import com.kasbus.kasbusapp.Containers.Comment;
import com.kasbus.kasbusapp.Containers.Ratings;

import java.util.List;

public interface GetCallback {
    void onRatingsReceived(Ratings ratings);
    void onCommentsReceived(List<Comment> comments);
}
