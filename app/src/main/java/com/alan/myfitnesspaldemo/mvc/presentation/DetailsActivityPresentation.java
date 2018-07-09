package com.alan.myfitnesspaldemo.mvc.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface DetailsActivityPresentation {

    void shareLink(@Nullable String link);

    void showWebView(@NonNull String url);

}
