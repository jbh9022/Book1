package com.spacemonster.book.book.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.spacemonster.book.book.R;


public class CustomDialog_Notice {
    private Context context;
    private int phoneWidth;
    private int phoneHeigth;

    public CustomDialog_Notice(Context context,int phoneWidth, int phoneHeigth) {
        this.context = context;
        this.phoneWidth = phoneWidth;
        this.phoneHeigth = phoneHeigth;
    }
    public void CallDialog_Notice(){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_noticedialog);
        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width= (int) (phoneWidth *0.9f);
        wm.height= (int)(phoneHeigth *0.9f);
        dialog.getWindow().setAttributes(wm);
        dialog.show();

        final ImageView cancelButton = (ImageView) dialog.findViewById(R.id.notice_cancelButton);
        final ImageView backButton = (ImageView)dialog.findViewById(R.id.notice_Backbtn);

        final WebView webView = (WebView) dialog.findViewById(R.id.notice_WebView);
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setUseWideViewPort(true);

        String url = "http://jbh9022.cafe24.com/notice.html";

        webSetting.setBuiltInZoomControls(true);
        webSetting.setSupportZoom(false);
        webSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        //화면전환시 같은 뷰내에서 전환
        webView.setWebViewClient(new NoticeWebViewClient());
        webView.loadUrl(url);


        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        //닫기버튼
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.goBack();
                webView.goBack();
            }
        });
    }

    //화면전환시 같은 뷰내에서 전환
    class  NoticeWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {
//            return super.shouldOverrideUrlLoading(view, request);
            view.loadUrl(request);
            return true;
        }
    }

}
