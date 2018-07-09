package com.alan.myfitnesspaldemo.mvp.presenter;

import com.alan.myfitnesspaldemo.R;
import com.alan.myfitnesspaldemo.mvp.adapter.ArticleAdapter;
import com.alan.myfitnesspaldemo.mvp.presentation.MainActivityPresentation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class MainActivityPresenterTest {

    @Mock
    MainActivityPresentation presentation;
    @Mock
    MainActivityPresenter presenter;
    @Mock
    ArticleAdapter adapter;

    @Before
    public void setUp() {
        presentation = mock(MainActivityPresentation.class);
        presenter = mock(MainActivityPresenter.class);
        adapter = mock(ArticleAdapter.class);
    }

    @Test
    public void onItemClickClick_Null_link() {

        presentation.showToast(R.string.invalid_link);
        presenter.onItemClickClick(null);

        verify(presentation).showToast(R.string.invalid_link);
        verifyNoMoreInteractions(presentation);
    }

    @Test
    public void onItemClickClick_Valid_link() {
        String link = "test";
        presentation.navigateToDetailsActivity(link);
        presenter.onItemClickClick(link);

        verify(presentation).navigateToDetailsActivity(link);
        verifyNoMoreInteractions(presentation);
    }

    @Test
    public void query_No_NetWork() {
        when(presentation.isNetWorkAvailable()).thenReturn(false);
        presentation.showProgressBar(true);
        presentation.showProgressBar(false);
        presentation.showToast(R.string.net_work_error);
        presenter.query("test", adapter);

        verify(presentation).showProgressBar(true);
        verify(presentation).showProgressBar(false);
        verify(presentation).showToast(R.string.net_work_error);
        verifyNoMoreInteractions(presentation);
    }

}