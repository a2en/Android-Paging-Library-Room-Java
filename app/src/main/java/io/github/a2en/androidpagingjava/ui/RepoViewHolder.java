package io.github.a2en.androidpagingjava.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.github.a2en.androidpagingjava.R;
import io.github.a2en.androidpagingjava.model.Repo;

/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class RepoViewHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final TextView description;
    private final TextView stars;
    private final TextView language;
    private final TextView forks;
    private Repo repo;

    private RepoViewHolder(@NonNull View view) {
        super(view);
        name =view.findViewById(R.id.repo_name);
        description = view.findViewById(R.id.repo_description);
        stars = view.findViewById(R.id.repo_stars);
        language = view.findViewById(R.id.repo_language);
        forks = view.findViewById(R.id.repo_forks);
        view.setOnClickListener(v -> {
            view.getContext()
                    .startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(repo.getUrl())));
        });
    }

    static RepoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_view_item, parent, false);
        return new RepoViewHolder(view);
    }

    void bind(Repo repo) {
        if(repo == null){
            Resources resources = this.itemView.getResources();
            name.setText(resources.getString(R.string.loading));
            description.setVisibility(View.GONE);
            language.setVisibility(View.GONE);
            stars.setText(resources.getString(R.string.unknown));
            forks.setText(resources.getString(R.string.unknown));
        }else {
            showRepoData(repo);
        }
    }

    private void showRepoData(Repo repo) {
        this.repo=repo;
        name.setText(repo.getFullName());

        // if the description is missing, hide the TextView
        int descriptionVisiblity = View.GONE;
        if(repo.getDescription()!=null){
            description.setText(repo.getDescription());
            descriptionVisiblity=View.VISIBLE;
        }

        description.setVisibility(descriptionVisiblity);
        stars.setText(String.valueOf(repo.getStars()));
        forks.setText(String.valueOf(repo.getForks()));

        // if the language is missing, hide the label and the value
        int languageVisibility = View.GONE;
        if(repo.getLanguage() != null && repo.getLanguage().isEmpty()){
            Resources resources = this.itemView.getContext().getResources();
            language.setText(resources.getString(R.string.language, repo.getLanguage()));
            languageVisibility = View.VISIBLE;
        }
        language.setVisibility(languageVisibility);
    }
}
