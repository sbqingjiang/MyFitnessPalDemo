package com.alan.myfitnesspaldemo.mvp.presenter;

import com.alan.myfitnesspaldemo.mvp.presentation.DetailsActivityPresentation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class DetailsActivityPresenterTest {

    private static final String TEST_LINK = "test";

    @Mock
    DetailsActivityPresentation presentation;
    @Mock
    DetailsActivityPresenter presenter;

    @Before
    public void setUp() {
        presentation = mock(DetailsActivityPresentation.class);
        presenter = mock(DetailsActivityPresenter.class);
    }

    @Test
    public void setUpWebView() {
        presentation.showWebView(TEST_LINK);
        presenter.setUpWebView(TEST_LINK);

        verify(presentation).showWebView(TEST_LINK);
        verifyNoMoreInteractions(presentation);
    }

    @Test
    public void shareLink() {
        presentation.shareLink(TEST_LINK);
        presenter.shareLink(TEST_LINK);

        verify(presentation).shareLink(TEST_LINK);
        verifyNoMoreInteractions(presentation);
    }
}