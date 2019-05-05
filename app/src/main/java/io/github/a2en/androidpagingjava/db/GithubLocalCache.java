package io.github.a2en.androidpagingjava.db;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.arch.core.util.Function;
import androidx.paging.DataSource;
import io.github.a2en.androidpagingjava.data.RepoBoundaryCallback;
import io.github.a2en.androidpagingjava.model.Repo;

public final class GithubLocalCache {
    private final RepoDao repoDao;
    private final Executor ioExecutor;

    public final void insert(final List repos, RepoBoundaryCallback.Callback callback) {
        this.ioExecutor.execute((Runnable)(new Runnable() {
            public final void run() {
                Log.d("GithubLocalCache", "inserting " + repos.size() + " repos");
                GithubLocalCache.this.repoDao.insert(repos);
                callback.insertFinished();
            }
        }));
    }

    
    public final DataSource.Factory<Integer, Repo> reposByName(String name) {
        String query = '%' + name.replace(' ', '%') + '%';
        return this.repoDao.reposByName(query);
    }

    public GithubLocalCache( RepoDao repoDao,  Executor ioExecutor) {
        this.repoDao = repoDao;
        this.ioExecutor = ioExecutor;
    }
}
