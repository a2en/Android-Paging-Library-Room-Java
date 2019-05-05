package io.github.a2en.androidpagingjava.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import io.github.a2en.androidpagingjava.data.GithubRepository;
import io.github.a2en.androidpagingjava.model.Repo;
import io.github.a2en.androidpagingjava.model.RepoSearchResult;

public class SearchRepositoriesViewModel extends ViewModel {
    private final GithubRepository repository;

    private MutableLiveData<String> queryLiveData;
    LiveData<String> networkErrors;
    private LiveData<RepoSearchResult> repoResult;
    final LiveData<PagedList<Repo>> repos;


    public SearchRepositoriesViewModel(GithubRepository repository) {
        this.repository = repository;
        queryLiveData = new MutableLiveData<>();
        repoResult = Transformations.map(queryLiveData, repository::search);
        repos = Transformations.switchMap(repoResult, RepoSearchResult::getData);
        networkErrors = Transformations.switchMap(repoResult,RepoSearchResult::getNetworkErrors);
    }

    public void searchRepo(String queryString){
        queryLiveData.postValue(queryString);
    }

    public String lastQueryValue() {
        return this.queryLiveData.getValue();
    }
}
