package com.alan.myfitnesspaldemo.mvc.presenter;

import android.support.annotation.Nullable;

import com.alan.myfitnesspaldemo.mvc.presentation.DetailsActivityPresentation;

public class DetailsActivityPresenter {

    private DetailsActivityPresentation presentation;

    public DetailsActivityPresenter(DetailsActivityPresentation presentation) {
        this.presentation = presentation;
    }

    public void setUpWebView(@Nullable String url) {
        presentation.showWebView(url);
    }

    public void shareLink(@Nullable String url) {
        presentation.shareLink(url);
    }
}
