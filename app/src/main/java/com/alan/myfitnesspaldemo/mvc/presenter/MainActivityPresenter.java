package com.alan.myfitnesspaldemo.mvc.presenter;

import android.support.annotation.Nullable;

import com.alan.myfitnesspaldemo.R;
import com.alan.myfitnesspaldemo.api.ApiCall;
import com.alan.myfitnesspaldemo.api.model.ApiResponse;
import com.alan.myfitnesspaldemo.api.model.Doc;
import com.alan.myfitnesspaldemo.mvc.adapter.ArticleAdapter;
import com.alan.myfitnesspaldemo.mvc.presentation.MainActivityPresentation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    private MainActivityPresentation presentation;
    private int page = 0;

    public MainActivityPresenter(MainActivityPresentation presentation) {
        this.presentation = presentation;
    }

    public void changeToNextPage(String input, ArticleAdapter adapter) {
        page++;
        query(input, adapter);
    }

    public void onItemClickClick(@Nullable String url) {
        if (url == null) {
            presentation.showToast(R.string.invalid_link);
            return;
        }
        presentation.navigateToDetailsActivity(url);
    }

    public boolean query(String q, ArticleAdapter adapter) {
        presentation.showProgressBar(true);
        if (!presentation.isNetWorkAvailable()) {
            presentation.showProgressBar(false);
            presentation.showToast(R.string.net_work_error);
            return false;
        }
        Call<ApiResponse> call = ApiCall.getInstance().query(q, null, null, null, null, page);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                presentation.showProgressBar(false);
                if (response.body() != null && response.body().getResponse() != null) {
                    ArrayList<Doc> docs = (ArrayList<Doc>) response.body().getResponse().getDocs();
                    if (docs.isEmpty()) {
                        presentation.showToast(R.string.no_results);
                    } else {
                        adapter.appendArticles(docs);
                        presentation.initButtonViews();
                    }
                    return;
                }
                presentation.showToast(R.string.no_results);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                presentation.showProgressBar(false);
                presentation.showToast(R.string.request_fail);
            }
        });
        return true;
    }
}
