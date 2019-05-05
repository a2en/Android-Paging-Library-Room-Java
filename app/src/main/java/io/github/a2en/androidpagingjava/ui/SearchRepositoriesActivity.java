package io.github.a2en.androidpagingjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import io.github.a2en.androidpagingjava.Injection;
import io.github.a2en.androidpagingjava.R;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchRepositoriesActivity extends AppCompatActivity {

    private static final String LAST_SEARCH_QUERY = "last_search_query";
    private static final String DEFAULT_QUERY = "Android";

    private SearchRepositoriesViewModel viewModel;
    private ReposAdapter adapter;
    private RecyclerView list;
    private EditText searchRepo;
    private TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_repositories);
        searchRepo= findViewById(R.id.search_repo);
        emptyList= findViewById(R.id.emptyList);
        list = findViewById(R.id.list);
        adapter = new ReposAdapter();
        viewModel = ViewModelProviders.of(this, Injection.INSTANCE.provideViewModelFactory(this))
                .get(SearchRepositoriesViewModel.class);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        list.addItemDecoration(dividerItemDecoration);
        initAdapter();

        String query = savedInstanceState != null ? savedInstanceState.getString(LAST_SEARCH_QUERY):DEFAULT_QUERY;
        viewModel.searchRepo(query);
        initSearch(query);
    }

    private void initAdapter() {
        list.setAdapter(adapter);
        viewModel.repos.observe(this, repos -> {
            Log.d("Activity", "list: "+repos.size());
            showEmptyList(repos.size()==0);
            adapter.submitList(repos);
        });

        viewModel.networkErrors.observe(this,repos-> Toast.makeText(this, "\uD83D\uDE28 Wooops " + repos, Toast.LENGTH_LONG).show());
    }
    private void initSearch(String query) {
       searchRepo.setText(query);
       searchRepo.setOnEditorActionListener((v, actionId, event) -> {
           if (actionId == EditorInfo.IME_ACTION_GO) {
               updateRepoListFromInput();
               return true;
           } else {
               return false;
           }
       });
       searchRepo.setOnKeyListener((v, keyCode, event) -> {
           if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
               updateRepoListFromInput();
               return true;
           } else {
               return false;
           }
       });
    }

    private void updateRepoListFromInput() {
        String query = searchRepo.getText().toString().trim();
        if(!query.isEmpty()){
            list.scrollToPosition(0);
            viewModel.searchRepo(query);
            adapter.submitList(null);
        }
    }

    private void showEmptyList(boolean show) {
        if (show) {
            emptyList.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        } else {
            emptyList.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue());
    }
}

