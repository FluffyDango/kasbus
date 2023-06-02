package com.kasbus.kasbusapp;

import com.kasbus.kasbusapp.Containers.Ratings;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
//    @GET("/subjects/en")
//    Call<List<Subject>> getEnSubjects();

    @GET("ratings/tNmmvuCzwF60cZiQEvwd")
    Call<Ratings> getSubjectRatings();
}
