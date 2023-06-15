package com.kasbus.kasbusapp.API;

import android.util.Log;

import com.kasbus.kasbusapp.Containers.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICalls {
    private APIInterface api_interface;
    private SubjectCallback subject_callback;
    private RatingCallback rating_callback;
    private CommentCallback comment_callback;

    public APICalls() {
        api_interface = APIClient.getClient().create(APIInterface.class);
    }

    public void setSubjectCallback(SubjectCallback subject_callback) {
        this.subject_callback = subject_callback;
    }


    /**
     * Starts the asynchronous fetching of data from API.
     *
     * @param language the language in which the subject names will be given.
     *                 Possible values "EN", "LT"
     */
    public void fetchSubjects(String language) {
        if (subject_callback == null) {
            Log.e("API", "fetchSubject ERROR: subject_callback has not been initialized");
            return;
        }

        String lang = language.toLowerCase();
        if (lang.equals("lt"))
            fetchSubjectsFromAPI(api_interface.getAllSubjectsLT());
        else if (lang.equals("en"))
            fetchSubjectsFromAPI(api_interface.getAllSubjectsEN());
        else
            Log.e("API", "Invalid language specified: " + language);
    }

    private void fetchSubjectsFromAPI(Call<List<Subject>> call) {
        call.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                Log.d("CONNECTION", response.code() + "");
                subject_callback.onSubjectsReceived(response.body());
            }
            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    /**
     * Starts the asynchronous fetching of data from API.
     *
     * @param id The subject id that is provided in Subject class
     */
    public void fetchRatings(String id) {
        if (rating_callback == null) {
            Log.e("API", "fetchRatings ERROR: rating_callback has not been initialized");
            return;
        }
        fetchRatingsFromAPI(api_interface.getSubjectRatings(id));
    }

    private void fetchRatingsFromAPI(Call<Ratings> call) {
        call.enqueue(new Callback<Ratings>() {
            @Override
            public void onResponse(Call<Ratings> call, Response<Ratings> response) {
                Log.d("CONNECTION", response.code() + "");
                rating_callback.onRatingsReceived(response.body());
            }
            @Override
            public void onFailure(Call<Ratings> call, Throwable t) {
                call.cancel();
            }
        });
    }

    /**
     * Starts the asynchronous fetching of data from API.
     *
     * @param id The subject id that is provided in Subject class
     */
    public void fetchComments(String id) {
        if (comment_callback == null) {
            Log.e("API", "fetchComments ERROR: comment_callback has not been initialized");
            return;
        }
        fetchCommentsFromAPI(api_interface.getSubjectComments(id));
    }

    private void fetchCommentsFromAPI(Call<List<Comment>> call) {
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                Log.d("CONNECTION", response.code() + "");
                comment_callback.onCommentsReceived(response.body());
            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
