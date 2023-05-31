package com.kasbus.kasbusapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "http://api.kasbus.lt/subjects/en";
    @GET("tNmmvuCzwF60cZiQEvwd")
    Call<List<Subject>> getsuperHeroes();
}
