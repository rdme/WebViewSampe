package com.websarva.wings.android.webviewsample;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static java.net.URLEncoder.encode;

public class MainActivity extends AppCompatActivity {
    private SearchView mSearchView;
    private Toolbar mToolBar;
    private WebView mWebView;
    private EditText mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        setTitle("");

        // search view
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new MySearchViewTextListener());

        // webView
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl("https://www.yahoo.co.jp");
        mWebView.getSettings().setJavaScriptEnabled(true);

        mTextView = (EditText) findViewById(R.id.edit_text);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * WebViewClient
     */
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mTextView.setText(url);
            mTextView.clearFocus();
            mSearchView.clearFocus();
            mWebView.requestFocus();
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

    /**
     * SearchViewのリスナ
     */
    private class MySearchViewTextListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            searchTextInWebView(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    private void searchTextInWebView(String text) {
        String p = "";
        try {
            p = URLEncoder.encode(text,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "https://kids.yahoo.co.jp/search/bin/search?ei=UTF-8&fr=ush&p="+p;
        mWebView.loadUrl(url);

        mSearchView.clearFocus();
        mWebView.requestFocus();
    }

}
