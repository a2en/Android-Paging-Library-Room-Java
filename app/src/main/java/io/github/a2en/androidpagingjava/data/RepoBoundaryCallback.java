package io.github.a2en.androidpagingjava.data;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import io.github.a2en.androidpagingjava.api.GithubService;
import io.github.a2en.androidpagingjava.api.Util;
import io.github.a2en.androidpagingjava.db.GithubLocalCache;
import io.github.a2en.androidpagingjava.model.Repo;

public class RepoBoundaryCallback extends PagedList.BoundaryCallback<Repo> {

    private int lastRequestedPage=1;
    private final MutableLiveData<String> _networkErrors;
    private boolean isRequestInProgress=false;
    private final String query;
    private final GithubService service;
    private final GithubLocalCache cache;
    private static final int NETWORK_PAGE_SIZE = 50;

    public interface ResponseErrorListener {
        void onError(String error);
    }
    public interface ResponseSuccessListener {
        void onSuccess(List<Repo> repos);
    }

    public interface Callback{
        void insertFinished();
    }

    @Override
    public void onZeroItemsLoaded() {
        requestAndSaveData(query);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Repo itemAtEnd) {
        requestAndSaveData(query);
    }

    public RepoBoundaryCallback(String query, GithubService service, GithubLocalCache cache) {
        this._networkErrors = new MutableLiveData<>();
        this.query = query;
        this.service = service;
        this.cache = cache;
    }

    public LiveData<String> getNetworkErrors() {
        return this._networkErrors;
    }

    private void requestAndSaveData(String query){
        if (isRequestInProgress) return;

        isRequestInProgress=true;
        Util.searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, repos -> {
            cache.insert(repos, () -> {
                lastRequestedPage++;
                isRequestInProgress = false;
            });
        }, error -> {
            _networkErrors.postValue(error);
            isRequestInProgress = false;
        });
    }
}
