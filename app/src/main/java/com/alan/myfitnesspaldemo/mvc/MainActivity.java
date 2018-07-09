package com.alan.myfitnesspaldemo.mvc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.alan.myfitnesspaldemo.R;
import com.alan.myfitnesspaldemo.api.model.Doc;
import com.alan.myfitnesspaldemo.mvc.adapter.ArticleAdapter;
import com.alan.myfitnesspaldemo.mvc.presentation.MainActivityPresentation;
import com.alan.myfitnesspaldemo.mvc.presenter.MainActivityPresenter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityPresentation, ArticleAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getName();
    public static final String ARTICLE_URL = "ARTICLE_URL";

    private List<Doc> articles;
    private ArticleAdapter adapter;
    private RecyclerView recyclerView;
    private MainActivityPresenter presenter;
    private String input;
    private Button nextButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycler_view);
        nextButton = findViewById(R.id.next_button);
        progressBar = findViewById(R.id.home_progressbar);

        presenter = new MainActivityPresenter(this);
        articles = new ArrayList<>();
        adapter = new ArticleAdapter(articles);
        adapter.setListener(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changeToNextPage(input, adapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                input = query;
                return presenter.query(query, adapter);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //region OnItemClickListener method
    @Override
    public void onItemClickClick(@Nullable String url) {
        presenter.onItemClickClick(url);
    }
    //endregion OnItemClickListener method

    //region presentation methods
    @Override
    public void initButtonViews() {
        if (adapter.getItemCount() == 0) {
            nextButton.setVisibility(View.GONE);
            return;
        }
        nextButton.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isNetWorkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    public void navigateToDetailsActivity(@NonNull String url) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(ARTICLE_URL, url);
        startActivity(intent);
    }

    @Override
    public void showProgressBar(boolean isShow) {
        if (isShow) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(isShow);
            return;
        }
        progressBar.setVisibility(View.GONE);
        progressBar.setIndeterminate(false);
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }
    //endregion presentation methods
}
