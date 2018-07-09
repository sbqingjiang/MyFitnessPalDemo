package com.alan.myfitnesspaldemo.mvc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.alan.myfitnesspaldemo.R;
import com.alan.myfitnesspaldemo.mvc.presentation.DetailsActivityPresentation;
import com.alan.myfitnesspaldemo.mvc.presenter.DetailsActivityPresenter;

public class DetailsActivity extends AppCompatActivity implements DetailsActivityPresentation {

    private static final String TAG = DetailsActivity.class.getName();

    private DetailsActivityPresenter presenter;
    private String url;
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        presenter = new DetailsActivityPresenter(this);
        url = getIntent().getStringExtra(MainActivity.ARTICLE_URL);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        presenter.setUpWebView(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.share_link);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_link:
                presenter.shareLink(url);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //region presentation methods
    @Override
    public void shareLink(@Nullable String link) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(share, getString(R.string.share_link)));
    }

    @Override
    public void showWebView(@NonNull String url) {
        progressBar.setIndeterminate(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setIndeterminate(false);
                webView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(url);
    }
    //endregion presentation methods
}
