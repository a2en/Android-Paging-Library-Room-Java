package io.github.a2en.androidpagingjava.api;

import android.text.Editable;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import io.github.a2en.androidpagingjava.data.RepoBoundaryCallback;
import io.github.a2en.androidpagingjava.model.Repo;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Util {
    private static GithubService githubService;
    private static final String BASE_URL = "https://api.github.com/";
    private static final String TAG = "GithubService";
    private static final String IN_QUALIFIER = "in:name,description";

    /**
     * Search repos based on a query.
     * Trigger a request to the Github searchRepo API with the following params:
     * @param query searchRepo keyword
     * @param page request page index
     * @param itemsPerPage number of repositories to be returned by the Github API per page
     *
     * The result of the request is handled by the implementation of the functions passed as params
     * @param successListener callback that defines how to handle the list of repos received
     * @param errorListener callback that defines how to handle request failure
     */
    public static void searchRepos(GithubService service, String query, int page, int itemsPerPage,
                                   RepoBoundaryCallback.ResponseSuccessListener successListener, RepoBoundaryCallback.ResponseErrorListener errorListener) {

        Log.d(TAG, "query: " + query + ", page: " + page + ", itemsPerPage: " + itemsPerPage);

        String apiQuery = query + IN_QUALIFIER;
        service.searchRepos(apiQuery, page, itemsPerPage).enqueue(new Callback<RepoSearchResponse>() {
            @Override
            public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {
                if(response.isSuccessful()){
                    List<Repo> repos = response.body() != null ? response.body().getItems() : Collections.emptyList();
                    successListener.onSuccess(repos);
                }else{
                    try {
                        errorListener.onError(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RepoSearchResponse> call, Throwable t) {
                Log.d(TAG, "fail to get data");
                errorListener.onError(t.getMessage()!=null?t.getMessage(): "unknown error");

            }
        });
    }

    public static GithubService createGithubService() {
        if (githubService == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build();
            githubService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GithubService.class);
        }
        return githubService;
    }
}
