package com.olegkuchura.android.indiannewsclient.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.olegkuchura.android.indiannewsclient.R;

public class PieceOfNewsFragment extends Fragment {
    public static final String TAG = PieceOfNewsFragment.class.getSimpleName();
    private static final String ARG_URL = "url";

    private String url;

    private ProgressBar progressBar;

    public static PieceOfNewsFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        PieceOfNewsFragment fragment = new PieceOfNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            url = getArguments().getString(ARG_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_piece_of_news, container, false);

        progressBar = v.findViewById(R.id.pb_web_progress);
        progressBar.setMax(100);

        WebView webViewNews = v.findViewById(R.id.wv_web_page);
        webViewNews.getSettings().setJavaScriptEnabled(true);
        webViewNews.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
        webViewNews.setWebViewClient(new SimpleWebViewClient());
        webViewNews.loadUrl(url);

        return v;
    }



    private class SimpleWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    }

}
