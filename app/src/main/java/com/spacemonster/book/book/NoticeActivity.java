package com.spacemonster.book.book;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.spacemonster.book.book.Dialog.CustomDialog_Notice;
import com.spacemonster.book.book.databinding.ActivityNoticeBinding;

public class NoticeActivity extends AppCompatActivity {

    private ActivityNoticeBinding binding;
    private WebSettings noticeSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice);
        //닫기
        Cancel();
        //웹뷰연결
        WebNotice();

    }
    //닫기
    public void Cancel(){
        binding.noticeCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //웹뷰 연결
    public void WebNotice(){
        noticeSetting = binding.noticeWeb.getSettings();
        noticeSetting.setJavaScriptEnabled(true);
        noticeSetting.setLoadWithOverviewMode(true);
        noticeSetting.setUseWideViewPort(true);

        String url = "http://jbh9022.cafe24.com/notice.html";

        noticeSetting.setBuiltInZoomControls(true);
        noticeSetting.setSupportZoom(false);
        noticeSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        //화면전환시 같은 뷰내에서 전환
        binding.noticeWeb.setWebViewClient(new NoticeWebClient());
        binding.noticeWeb.loadUrl(url);

        noticeSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        binding.noticeWeb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    }
    //화면전환시 같은 뷰내에서 전환
    class  NoticeWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {
//            return super.shouldOverrideUrlLoading(view, request);
            view.loadUrl(request);
            return true;
        }
    }
}
