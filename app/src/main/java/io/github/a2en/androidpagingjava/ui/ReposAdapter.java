package io.github.a2en.androidpagingjava.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import io.github.a2en.androidpagingjava.model.Repo;

public class ReposAdapter extends PagedListAdapter<Repo, RepoViewHolder> {

    private static final DiffUtil.ItemCallback<Repo> REPO_COMPARATOR =
            new DiffUtil.ItemCallback<Repo>() {
                @Override
                public boolean areItemsTheSame(@NonNull Repo oldItem, @NonNull Repo newItem) {
                    return oldItem.getId()==newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Repo oldItem, @NonNull Repo newItem) {
                    return oldItem.getFullName().equals(newItem.getFullName());
                }
            };

    protected ReposAdapter() {
        super(REPO_COMPARATOR);
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RepoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
      final Repo repo = getItem(position);
      if(repo!=null){
          holder.bind(repo);
      }
    }
}
