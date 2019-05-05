package io.github.a2en.androidpagingjava.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.github.a2en.androidpagingjava.api.GithubService;
import io.github.a2en.androidpagingjava.db.GithubLocalCache;
import io.github.a2en.androidpagingjava.model.Repo;
import io.github.a2en.androidpagingjava.model.RepoSearchResult;

public class GithubRepository {
    private final GithubService service;
    private final GithubLocalCache cache;
    private static final int DATABASE_PAGE_SIZE = 20;

    public GithubRepository(GithubService service, GithubLocalCache cache) {
        this.service = service;
        this.cache = cache;
    }
    /**
     * Search repositories whose names match the query.
     */
    public RepoSearchResult search(String query){
        Log.d("GithubRepository", "New query:"+query);
        RepoBoundaryCallback boundaryCallback = new RepoBoundaryCallback(query,service,cache);
        LiveData<String> networkErrors = boundaryCallback.getNetworkErrors();
        // Get data from the local cache
        DataSource.Factory<Integer, Repo> dataSourceFactory = cache.reposByName(query);
        final LiveData<PagedList<Repo>> data = new LivePagedListBuilder(dataSourceFactory,DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build();

        return new RepoSearchResult(data,networkErrors);
    }
}
