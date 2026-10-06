package com.universidad.torneosesports;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebFragment extends Fragment {
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View root = inflater.inflate(R.layout.fragment_web, container, false);
        EditText input = root.findViewById(R.id.web_url);
        Button go = root.findViewById(R.id.web_go);
        ProgressBar progress = root.findViewById(R.id.web_progress);
        webView = root.findViewById(R.id.web_view);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        go.setOnClickListener(v -> {
            String url = UrlValidator.normalize(input.getText().toString());
            if (url == null) {
                input.setError(getString(R.string.web_invalid));
                Toast.makeText(getActivity(), R.string.web_invalid, Toast.LENGTH_SHORT).show();
            } else {
                input.setError(null);
                webView.loadUrl(url);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
            webView = null;
        }
        super.onDestroyView();
    }
}
