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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.spacemonster.book.book.Modle.Seat;
import com.spacemonster.book.book.Network.Api;
import com.spacemonster.book.book.Network.Update_seatNum;

import java.io.IOException;
import java.util.ArrayList;

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

    private String[] seatset={"1","2","3","4","5","6","7","8","9","10",
                                "11","12","13","14","15","16","17","18","19","20",
                                "21","22","23","24","25","26","27","28","29","30",
                                "31","32","33","34","35","36","37","38","39","40",
                                "41","42","43","44","45","46","47","48","49","50"};
    private String selectseat;
    private ArrayList<Seat> checkArray;

    private String seatNumber;
    private String seat_userID;
    private String seat_time;

    private WebSettings wSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatset);
        ButterKnife.bind(this);

        Intent intent_seatset = getIntent();
        seatNumber = intent_seatset.getStringExtra("seatNum");
        seat_userID = intent_seatset.getStringExtra("ID");
        seat_time = intent_seatset.getStringExtra("Time");

        seatset_Web.setWebChromeClient(new WebChromeClient());
        wSetting = seatset_Web.getSettings();
        wSetting.setJavaScriptEnabled(true);

        seatset_Web.loadUrl("http://jbh9022.cafe24.com/seatlist2.php");
        wSetting.setBuiltInZoomControls(true);
        wSetting.setSupportZoom(true);

        wSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wSetting.setLoadWithOverviewMode(true);
        seatset_Txt1.setText(seatNumber);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(seatsetActivity.this, R.layout.support_simple_spinner_dropdown_item, seatset);
        seatset_Spinner.setAdapter(adapter);

        seatset_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        seatset_changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectseat = seatset_Spinner.getSelectedItem().toString();
                checkSeat();
            }
        });
    }

    private void checkSeat(){
        checkArray = new ArrayList<Seat>();
        GetSeatcheck check = new GetSeatcheck();
        check.execute(Api.GETCHECKSEAT_POST, selectseat);
    }
   public class GetSeatcheck extends AsyncTask<String, Void, Seat[] > {

       @Override
       protected Seat[] doInBackground(String... strings) {
           String url = strings[0];
           String seatNumber = strings[1];

           RequestBody formBody = new FormBody.Builder()
                   .add("list_seatNum", seatNumber)
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
           update_seat.execute(selectseat,seat_userID);
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

}
