package io.github.a2en.androidpagingjava;

import android.content.Context;

import java.util.concurrent.Executors;

import androidx.lifecycle.ViewModelProvider;
import io.github.a2en.androidpagingjava.api.Util;
import io.github.a2en.androidpagingjava.data.GithubRepository;
import io.github.a2en.androidpagingjava.db.GithubLocalCache;
import io.github.a2en.androidpagingjava.db.RepoDatabase;
import io.github.a2en.androidpagingjava.ui.ViewModelFactory;

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
public final class Injection {
    public static final Injection INSTANCE;

    /**
     * Creates an instance of [GithubLocalCache] based on the database DAO.
     */
    private GithubLocalCache provideCache(Context context) {
        RepoDatabase database = RepoDatabase.getInstance(context);
        return new GithubLocalCache(database.reposDao(),Executors.newSingleThreadExecutor());
    }
    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private GithubRepository provideGithubRepository(Context context) {
        return new GithubRepository(Util.createGithubService(), this.provideCache(context));
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    public ViewModelProvider.Factory provideViewModelFactory(Context context) {
        return new ViewModelFactory(this.provideGithubRepository(context));
    }

    static {
        INSTANCE = new Injection();
    }
}
