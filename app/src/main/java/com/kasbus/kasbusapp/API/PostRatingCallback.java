package com.kasbus.kasbusapp.API;

import com.kasbus.kasbusapp.Containers.PostRatingResponse;

public interface PostRatingCallback {
    void onRatingPostComplete(PostRatingResponse response);
}
