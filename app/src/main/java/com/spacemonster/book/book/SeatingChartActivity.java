package com.spacemonster.book.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.spacemonster.book.book.Dialog.CustomDialog_Notice;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatingChartActivity extends AppCompatActivity {

    private String text1;
    private String seatingList[] = {"좌석선택","자유석"};

    @BindView(R.id.seat_btn2) Button seat_ok;
    @BindView(R.id.seat_spinner)Spinner seat_Spinner;
    @BindView(R.id.seat_Web1) WebView seat_Webview;
    @BindView(R.id.seat_Laout) LinearLayout seat_Layout;

    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seating_chart);

        ButterKnife.bind(this);

        final Intent intent_Seat = getIntent();
        text1 = intent_Seat.getStringExtra("ID");
        seat_Webview = (WebView)findViewById(R.id.seat_Web1);
        seat_Webview.setWebChromeClient(new WebChromeClient());
        settings = seat_Webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(false);
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        seat_Webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        //뒤로가기 버튼
        seat_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(SeatingChartActivity.this, R.layout.support_simple_spinner_dropdown_item, seatingList);
        seat_Spinner.setAdapter(adapter);
        //스피너 선택
        seat_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        seat_Webview.loadUrl("");
                        settings.setLoadWithOverviewMode(true);
                        settings.setUseWideViewPort(true);
                        settings.setBuiltInZoomControls(true);
                        settings.setSupportZoom(false);
                        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
                        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                        seat_Webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                        seat_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(SeatingChartActivity.this, "좌석을 선택하세요", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case 1:
                        seat_Webview.loadUrl("http://jbh9022.cafe24.com/seatlist2.php");
                        //화면전환시 같은 뷰내에서 전환
                        seat_Webview.setWebViewClient(new SeatingChartActivity.NoticeWebViewClient());
                        settings.setLoadWithOverviewMode(true);
                        settings.setUseWideViewPort(true);
                        settings.setBuiltInZoomControls(true);
                        settings.setSupportZoom(false);
                        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
                        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                        seat_Webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                        //버튼 클릭시
                        seat_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                Toast.makeText(SeatingChartActivity.this, "자유석 선택", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    //같은뷰에서 화면전환(새로고침시)
    class  NoticeWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {
//            return super.shouldOverrideUrlLoading(view, request);
            view.loadUrl(request);
            return true;
        }
    }
}
