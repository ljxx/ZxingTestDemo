package com.ylx.zxingtestdemo.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mylhyl.crlayout.SwipeRefreshWebView;
import com.ylx.zxingtestdemo.R;
import com.ylx.zxingtestdemo.base.BasicActivity;
import com.ylx.zxingtestdemo.scanner.common.Scanner;
import com.ylx.zxingtestdemo.scanner.result.URIResult;

/**
 * URI显示
 */
public class UriActivity extends BasicActivity {
    private SwipeRefreshWebView swipeRefreshWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri);
        String uri = ((URIResult) getIntent().getSerializableExtra(Scanner.Scan.RESULT)).getUri();
        swipeRefreshWebView = (SwipeRefreshWebView) findViewById(R.id.webView);
        swipeRefreshWebView.getScrollView().loadUrl(uri);
        swipeRefreshWebView.getScrollView().setWebViewClient(new SampleWebViewClient());
    }

    private class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            swipeRefreshWebView.autoRefresh();
            return true;
        }
    }

    public static void gotoActivity(Activity activity, Bundle bundle) {
        activity.startActivity(new Intent(activity, UriActivity.class).putExtras(bundle));
    }
}
