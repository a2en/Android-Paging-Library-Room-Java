package io.github.a2en.androidpagingjava.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.github.a2en.androidpagingjava.model.Repo;

public class RepoSearchResponse {
    @SerializedName("total_count")
    private final Integer total;
    @SerializedName("items")
    private final List<Repo> items;
    private final Integer nextPage;

    public Integer getTotal() {
        return total;
    }

    public List<Repo> getItems() {
        return items;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public RepoSearchResponse(Integer total, List<Repo> items, Integer nextPage) {
        this.total = total;
        this.items = items;
        this.nextPage = nextPage;
    }
}
