package io.github.a2en.androidpagingjava.model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "repos")
public class Repo {
    @SerializedName("id")
    @PrimaryKey
    private final long id;
    @SerializedName("name")
    @NonNull
    private final String name;
    @SerializedName("full_name")
    @NonNull
    private final String fullName;
    @SerializedName("description")
    @Nullable
    private final String description;
    @SerializedName("html_url")
    @NonNull
    private final String url;
    @SerializedName("stargazers_count")
    private final int stars;
    @SerializedName("forks_count")
    private final int forks;
    @SerializedName("language")
    @Nullable
    private final String language;

    public final long getId() {
        return this.id;
    }

    @NonNull
    public final String getName() {
        return this.name;
    }

    @NonNull
    public final String getFullName() {
        return this.fullName;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @NonNull
    public final String getUrl() {
        return this.url;
    }

    public final int getStars() {
        return this.stars;
    }

    public final int getForks() {
        return this.forks;
    }

    @Nullable
    public final String getLanguage() {
        return this.language;
    }

    public Repo(long id, @NonNull String name, @NonNull String fullName, @Nullable String description, @NonNull String url, int stars, int forks, @Nullable String language) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.url = url;
        this.stars = stars;
        this.forks = forks;
        this.language = language;
    }
}
