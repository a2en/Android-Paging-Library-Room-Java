package io.github.a2en.androidpagingjava.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class RepoSearchResult {
    @NonNull
    private final LiveData<PagedList<Repo>> data;
    private final LiveData<String> networkErrors;

    @NonNull
    public final LiveData<PagedList<Repo>> getData() {
        return this.data;
    }

    public final LiveData<String> getNetworkErrors() {
        return this.networkErrors;
    }

    public RepoSearchResult(@NonNull LiveData<PagedList<Repo>> data, LiveData<String> networkErrors) {
        this.data = data;
        this.networkErrors = networkErrors;
    }
}
