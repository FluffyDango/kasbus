package com.kasbus.kasbusapp.API;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kasbus.kasbusapp.Containers.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICalls {
    private static List<Subject> subjects;
    private final static APIInterface api_interface = APIClient.getClient().create(APIInterface.class);
    private static SubjectCallback subject_callback;
    private static GetCallback get_callback;
    private static PostCallback post_callback;

    public static void setSubjects(List<Subject> subjects_list) {
        if (subjects == null) {
            subjects = subjects_list;
        } else {
            Log.d("API", "subjects are already set");
        }
    }
    public static List<Subject> getSubjects() {
        return subjects;
    }

    public static void setSubjectCallback(SubjectCallback subject_cb) {
        subject_callback = subject_cb;
    }
    public static void setGetCallback(GetCallback get_cb) {
        get_callback = get_cb;
    }
    public static void setPostCallback(PostCallback post_cb) {
        post_callback = post_cb;
    }

    public static Boolean isSubjectCallbackSet() {
        return subject_callback != null;
    }
    public static Boolean isGetCallbackSet() {
        return get_callback != null;
    }
    public static Boolean isPostCallbackSet() {
        return post_callback != null;
    }

    /**
     * Starts the asynchronous fetching of data from API.
     *
     * @param language the language in which the subject names will be given.
     *                 Possible values "EN", "LT"
     */
    public static void fetchSubjects(String language) {
        if (!isSubjectCallbackSet()) {
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
            public void onResponse(@NonNull Call<List<Subject>> call, @NonNull Response<List<Subject>> response) {
                Log.d("CONNECTION", response.code() + "");
                Handler handler = new Handler();
                handler.postDelayed(() -> subject_callback.onSubjectsReceived(response.body()), 300);
            }
            @Override
            public void onFailure(@NonNull Call<List<Subject>> call, @NonNull Throwable t) {
                Log.d("CONNECTION", "FAILED TO CONNECT");
                Handler handler = new Handler();
                handler.postDelayed(() -> subject_callback.onSubjectFailure(language), 300);

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
        if (isGetCallbackSet())
            fetchRatingsFromAPI(api_interface.getSubjectRatings(id));
        else
            Log.e("API", "fetchRatings ERROR: get_callback has not been initialized");
    }

    private static void fetchRatingsFromAPI(@NonNull Call<Ratings> call) {
        call.enqueue(new Callback<Ratings>() {
            @Override
            public void onResponse(@NonNull Call<Ratings> call, @NonNull Response<Ratings> response) {
                Log.d("CONNECTION", response.code() + "");
                get_callback.onRatingsReceived(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<Ratings> call, @NonNull Throwable t) {
                Log.d("API", "GetRatingFromAPI failed");
                call.cancel();
            }
        });
    }

    public static void postRating(String subjectId, String category, int rating) {
        if (isPostCallbackSet()){
            PostRatingBody body = new PostRatingBody(subjectId, category, rating);
            PostRatingToAPI(api_interface.postRating(body));
        }
        else
            Log.e("API", "post_callback is null");
    }
    private static void PostRatingToAPI(@NonNull Call<Object> call) {
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call,
                                   @NonNull Response<Object> response) {
                Log.d("API", response.code() + "");
                if (response.isSuccessful())
                    post_callback.onRatingPostComplete();
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, Throwable t) {
                Log.d("API", "PostRatingToAPI failed");
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
        if (isGetCallbackSet()) {
            fetchCommentsFromAPI(api_interface.getSubjectComments(id));
            Log.d("API", "id: " + id);
        }
        else
            Log.e("API", "get_callback is null");
    }

    private static void fetchCommentsFromAPI(@NonNull Call<List<Comment>> call) {
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call, @NonNull Response<List<Comment>> response) {
                Log.d("CONNECTION", response.code() + "");
                get_callback.onCommentsReceived(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<List<Comment>> call, @NonNull Throwable t) {
                Log.d("API", "GetCommentsFromAPI failed");
                call.cancel();
            }
        });
    }

    public static void postComment(String subjectId, String faculty, String content) {
        if (isPostCallbackSet())  {
            PostCommentBody body = new PostCommentBody(subjectId, faculty, content);
            PostCommentToAPI(api_interface.postComment(body));
        }
        else
            Log.e("API", "post_callback is null");
    }
    private static void PostCommentToAPI(@NonNull Call<Object> call) {
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call,
                                   @NonNull Response<Object> response) {
                Log.d("API", response.code() + "");
                if (response.isSuccessful())
                    post_callback.onCommentPostComplete();
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, Throwable t) {
                Log.d("API", "PostCommentToAPI failed");
                call.cancel();
            }
        });
    }


}
