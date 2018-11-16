package com.spacemonster.book.book;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.spacemonster.book.book.Dialog.CustomDialog_Notice;
import com.spacemonster.book.book.Modle.Seat;
import com.spacemonster.book.book.Modle.SeatNum;
import com.spacemonster.book.book.Network.Api;
import com.spacemonster.book.book.Network.Update_Another;
import com.spacemonster.book.book.Network.Update_seatNum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class seatsetActivity extends AppCompatActivity {

//    private WebView webView;
    @BindView(R.id.seatset_Web) WebView seatset_Web;
    @BindView(R.id.seatset_Layout) LinearLayout seatset_back;
    @BindView(R.id.seatset_Btn) Button seatset_changeBtn;
    @BindView(R.id.seatset_Spinner1) Spinner seatset_Spinner;
    @BindView(R.id.seatset_txt1) TextView seatset_Txt1;
    @BindView(R.id.seatset_txt2) TextView seatset_Txt2;

//    private String[] seatset={"30","31","32","33","34","35","36","37","38","39",
//                              "40","41","42","43","44","45","46","47","48","49",
//                              "50","51","52","53","54","55","56","57","58","59",
//                              "60","61","62","63","64","65","66","67","68","69",
//                              "70","71","72","73","74","75","76","77","78","79"};
    private String selectseat;
    private ArrayList<Seat> checkArray;

    private String seatNumber;
    private String seat_userID;
    private String seat_time;
    private String shopName;
    private String seatName;
    private String seaturl;

    private WebSettings wSetting;
    private ArrayList<SeatNum> numbers;
    private ArrayList<String> numArray;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatset);
        ButterKnife.bind(this);

        Intent intent_seatset = getIntent();
        seatNumber = intent_seatset.getStringExtra("seatNum");
        seat_userID = intent_seatset.getStringExtra("ID");
        seat_time = intent_seatset.getStringExtra("Time");
        seatName = intent_seatset.getStringExtra("seatname");
        shopName = intent_seatset.getStringExtra("shop");

        numArray = new ArrayList<String>();

        GetSeatInfo();
        seatset_Web.setWebChromeClient(new WebChromeClient());
        wSetting = seatset_Web.getSettings();
        wSetting.setJavaScriptEnabled(true);
        wSetting.setLoadWithOverviewMode(true);
        wSetting.setUseWideViewPort(true);


        wSetting.setBuiltInZoomControls(true);
        wSetting.setSupportZoom(false);
        wSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        //화면전환시 같은 뷰내에서 전환
        seatset_Web.setWebViewClient(new seatsetActivity.NoticeWebViewClient());
        wSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        seatset_Web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        seatset_Txt1.setText(seatNumber);

        //뒤로가기버튼
        seatset_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //자리변경
        seatset_changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectseat = seatset_Spinner.getSelectedItem().toString();
                checkSeat();
            }
        });
    }

    private void GetSeatInfo() {
        numbers = new ArrayList<SeatNum>();
        GetSeatInfomation info=  new GetSeatInfomation();
        info.execute(shopName, seatName);
    }
    public class GetSeatInfomation extends AsyncTask<String, Void, SeatNum[]> {
        @Override
        protected SeatNum[] doInBackground(String... strings) {
            String shop = strings[0];
            String seatname = strings[1];
            String url = Api.GET_POSTSEATNUM;

            RequestBody formBody = new FormBody.Builder()
                    .add("shop", shop)
                    .add("seatname", seatname)
                    .build();

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            try {
                Response responses = client.newCall(request).execute();

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(responses.body().charStream()).getAsJsonObject().get("seatNumber");

                SeatNum[] seatNums = gson.fromJson(rootObject, SeatNum[].class);

                return seatNums;
            } catch (IOException e) {
                Log.d("FetchPostsTask", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(SeatNum[] seatNums) {
            super.onPostExecute(seatNums);
            if (seatNums.length > 0) {
                for (SeatNum seatNum : seatNums) {
                    numbers.add(seatNum);
                }
                seaturl = numbers.get(0).getShopMap();
                for(int i =0; i<numbers.size(); i++){
                    numArray.add(numbers.get(i).getSeatNum());
                }
            }
            adapter = new ArrayAdapter<String>(seatsetActivity.this, R.layout.support_simple_spinner_dropdown_item, numArray);
            seatset_Spinner.setAdapter(adapter);
            seatset_Web.loadUrl(seaturl);
        }
    }
    private void checkSeat(){
        checkArray = new ArrayList<Seat>();
        GetSeatcheck check = new GetSeatcheck();
        check.execute(Api.GETCHECKSEAT_POST, selectseat, seat_time);
    }

   public class GetSeatcheck extends AsyncTask<String, Void, Seat[] > {

       @Override
       protected Seat[] doInBackground(String... strings) {
           String url = strings[0];
           String seatNumber = strings[1];
           String seattime = strings[2];

           RequestBody formBody = new FormBody.Builder()
                   .add("list_seatNum", seatNumber)
                   .add("list_date", seattime)
                   .build();

           OkHttpClient client = new OkHttpClient();
           Request request = new Request.Builder()
                   .url(url)
                   .post(formBody)
                   .build();
           try {
               Response responses = client.newCall(request).execute();

               Gson gson = new Gson();
               JsonParser parser = new JsonParser();
               JsonElement rootObject = parser.parse(responses.body().charStream()).getAsJsonObject().get("checklist");

               Seat[] seats = gson.fromJson(rootObject, Seat[].class);

               return seats;
           } catch (IOException e) {
               Log.d("FetchPostsTask", e.getMessage());
               return null;
           }
       }
       private void UpdateSeat(){
           Update_seatNum update_seat = new Update_seatNum();
           update_seat.execute(selectseat,seat_userID,seat_time, "입실");
       }

       @Override
       protected void onPostExecute(Seat[] seats) {
           super.onPostExecute(seats);
           if (seats.length > 0) {
               for (Seat seat : seats) {
                   checkArray.add(seat);
               }
               seatset_Txt2.setText(checkArray.get(0).getList_in_out());
           }
           else{
               seatset_Txt2.setText("");
           }
           if(seatset_Txt2.getText().toString().equals("입실") || seatset_Txt2.getText().toString() == "입실"){
               Toast.makeText(seatsetActivity.this, "이미 있는 자리 입니다.", Toast.LENGTH_SHORT).show();
           }
            else {
               UpdateSeat();
//               UpdateOutting();
//               UpdateComback();
               Intent in2= new Intent(seatsetActivity.this, UserInfoActivity.class);
               in2.putExtra("ID",seat_userID);
               in2.putExtra("Time", seat_time);
               startActivity(in2);
               UserInfoActivity uA = (UserInfoActivity) UserInfoActivity.userInfo_Activity;
               uA.finish();
//               overridePendingTransition(0, 0);
               finish();
           }
       }
   }
    //화면전환시 같은 뷰내에서 전환
    class  NoticeWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {
//            return super.shouldOverrideUrlLoading(view, request);
            view.loadUrl(request);
            return true;
        }
    }
}
