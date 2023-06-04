package com.kasbus.kasbusapp;

import android.util.Log;
import android.widget.TextView;

import com.kasbus.kasbusapp.Containers.Ratings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICalls {

    public void setRatings(Call<Ratings> call, TextView responseText) {
        call.enqueue(new Callback<Ratings>() {
            @Override
            public void onResponse(Call<Ratings> call, Response<Ratings> response) {
                Log.d("CONNECTION", response.code() + "");

                String displayResponse = "";

                Ratings resource = response.body();
                String category1 = Double.toString(resource.category1);
                String category2 = Double.toString(resource.category2);
                String category3 = Double.toString(resource.category3);
                String category4 = Double.toString(resource.category4);

                displayResponse = category1 + " " + category2 + " " + category3 + " " + category4;

                responseText.setText(displayResponse);
            }

            @Override
            public void onFailure(Call<Ratings> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
