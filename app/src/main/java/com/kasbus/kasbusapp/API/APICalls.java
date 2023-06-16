package com.kasbus.kasbusapp.API;

import android.util.Log;

import com.kasbus.kasbusapp.Containers.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICalls {
    private static APIInterface api_interface = APIClient.getClient().create(APIInterface.class);;
    private static SubjectCallback subject_callback;
    private static RatingCallback rating_callback;
    private static CommentCallback comment_callback;

//    public APICalls() {
//        api_interface = APIClient.getClient().create(APIInterface.class);
//    }

    public static void setSubjectCallback(SubjectCallback subject_cb) {
        subject_callback = subject_cb;
    }
    public static Boolean isSubjectCallbackSet() {
        return subject_callback == null;
    }

    /**
     * Starts the asynchronous fetching of data from API.
     *
     * @param language the language in which the subject names will be given.
     *                 Possible values "EN", "LT"
     */
    public static void fetchSubjects(String language) {
        if (isSubjectCallbackSet()) {
            Log.e("API", "fetchSubject ERROR: subject_callback has not been initialized");
            return;
        }

        String lang = language.toLowerCase();
        if (lang.equals("lt"))
            fetchSubjectsFromAPI(api_interface.getAllSubjectsLT(), language);
        else if (lang.equals("en"))
            fetchSubjectsFromAPI(api_interface.getAllSubjectsEN(), language);
        else
            Log.e("API", "Invalid language specified: " + language);
    }

    private static void fetchSubjectsFromAPI(Call<List<Subject>> call, String language) {
        call.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                Log.d("CONNECTION", response.code() + "");
                subject_callback.onSubjectsReceived(response.body());
            }
            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                subject_callback.onSubjectFailure(language);
                call.cancel();
            }
        });
    }

    /**
     * Starts the asynchronous fetching of data from API.
     *
     * @param id The subject id that is provided in Subject class
     */
    public static void fetchRatings(String id) {
        if (rating_callback == null) {
            Log.e("API", "fetchRatings ERROR: rating_callback has not been initialized");
            return;
        }
        fetchRatingsFromAPI(api_interface.getSubjectRatings(id));
    }

    private static void fetchRatingsFromAPI(Call<Ratings> call) {
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
    public static void fetchComments(String id) {
        if (comment_callback == null) {
            Log.e("API", "fetchComments ERROR: comment_callback has not been initialized");
            return;
        }
        fetchCommentsFromAPI(api_interface.getSubjectComments(id));
    }

    private static void fetchCommentsFromAPI(Call<List<Comment>> call) {
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
