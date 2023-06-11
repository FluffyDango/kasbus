package com.kasbus.kasbusapp.API;

import com.kasbus.kasbusapp.Containers.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("/subjects/en")
    Call<List<Subject>> getAllSubjectsEN();
    @GET("/subjects/en/{id}")
    Call<Subject> getOneSubjectEN(@Path("id") String ID);
    @GET("/subjects/lt")
    Call<List<Subject>> getAllSubjectsLT();
    @GET("/subjects/lt/{id}")
    Call<List<Subject>> getOneSubjectLT(@Path("id") String ID);
    @GET("/comments/{id}")
    Call<Comment> getSubjectComments(@Path("id") String ID);
    @GET("ratings/{id}")
    Call<Ratings> getSubjectRatings(@Path("id") String ID);
}
