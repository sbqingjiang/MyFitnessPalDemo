package com.alan.myfitnesspaldemo.mvc.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public interface MainActivityPresentation {

    void initButtonViews();

    boolean isNetWorkAvailable();

    void navigateToDetailsActivity(@NonNull String url);

    void showProgressBar(boolean isShow);

    void showToast(@StringRes int resId);
}
