package io.github.a2en.androidpagingjava.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.github.a2en.androidpagingjava.data.GithubRepository;

public final class ViewModelFactory implements ViewModelProvider.Factory {
    private final GithubRepository repository;

    public ViewModelFactory( GithubRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchRepositoriesViewModel.class)) {
            return (T) new SearchRepositoriesViewModel(this.repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
    



}