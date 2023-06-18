package com.kasbus.kasbusapp.API;

import com.kasbus.kasbusapp.Containers.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<List<Comment>> getSubjectComments(@Path("id") String ID);
    @GET("ratings/{id}")
    Call<Ratings> getSubjectRatings(@Path("id") String ID);

    @POST("comments")
    Call<PostCommentResponse> postComment(
            @Field("subjectId") String subjectId,
            @Field("faculty") String faculty,
            @Field("content") String content
    );

    @FormUrlEncoded
    @POST("ratings")
    Call<PostRatingResponse> postRating(
            @Field("subjectId") String subjectId,
            @Field("category") String category,
            @Field("rating") int rating
    );
}
