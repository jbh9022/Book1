package com.spacemonster.book.book;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.spacemonster.book.book.Modle.Post;
import com.spacemonster.book.book.Network.Api;
import com.spacemonster.book.book.Network.Insert;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.spacemonster.book.book.R.*;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(id.user_txt1) TextView info_userID;
    @BindView(id.user_txt2) TextView info_userSpace;
    @BindView(id.user_txt3) TextView info_userSeatNum;
    @BindView(id.user_Layout2) LinearLayout user_seatChange;
    @BindView(id.user_Backlayout) LinearLayout user_Backlayout;

    @BindView(id.user_btn2) Button outingBtn;
    @BindView(id.user_btn3) Button combackBtn;
    @BindView(id.user_Outbtn) Button checkoutBtn;

    @BindView(id.user_Intxt) TextView inText;
    @BindView(id.user_Intxt2) TextView inText2;
    @BindView(id.user_OutTxt) TextView outText;
    @BindView(id.user_OutTxt2) TextView outText2;
    @BindView(id.user_NowTxt) TextView nowText;
    @BindView(id.user_hiddenTxt) TextView hiddenTxt;

    private String userID;
    private String userTime;
    private ArrayList<Post> postlist;
    private String inOutingtime;
    private String inOutingtime_2;
    private String inComebacktime;
    private String inComebacktime_2;
    private String inCheckouttime;
    private String inCheckouttime_2;
    private String nowSeatNum;
    public static Activity userInfo_Activity;

//    String sss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_user_info);

        ButterKnife.bind(this);

        userInfo_Activity = UserInfoActivity.this;

        Intent intent_UserInfo = getIntent();
        userID = intent_UserInfo.getStringExtra("ID");
        userTime = intent_UserInfo.getStringExtra("Time");

        info_userID.setText(userID);

        fetchAsyncPosts();

        user_Backlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    //퇴실
    @OnClick(id.user_Outbtn)
     public void Checkout(){
        String hiddenString = nowText.getText().toString();
        if(hiddenString.equals("입실") || hiddenString == "입실"){
            CustomDialog_Checkout dialog_checkout = new CustomDialog_Checkout();
            dialog_checkout.collDialog();
        }
        else if(hiddenString.equals("외출") || hiddenString == "외출"){
            Toast.makeText(UserInfoActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(UserInfoActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
        }
    }
    //외출
    @OnClick(id.user_btn2)
    public void  Outting(){
        String hiddenString = nowText.getText().toString();
        if(hiddenString.equals("입실") || hiddenString == "입실"){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            inOutingtime = sdf.format(new Date());
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            inOutingtime_2 = sdf2.format(new Date());
            nowText.setText("외출");
            nowText.setTextColor(Color.parseColor("#0000ff"));
            nowSeatNum = info_userSeatNum.getText().toString();
            inOuting();
        }
        else if(hiddenString.equals("외출") || hiddenString == "외출"){
            Toast.makeText(UserInfoActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(UserInfoActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //복귀
    @OnClick(id.user_btn3)
    public void Comback(){
        String hiddenString = nowText.getText().toString();
        if(hiddenString.equals("입실") || hiddenString == "입실"){
            Toast.makeText(UserInfoActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
        }
        else if(hiddenString.equals("외출") || hiddenString == "외출"){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            inComebacktime = sdf.format(new Date());
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            inComebacktime_2 = sdf2.format(new Date());
            nowText.setText("입실");
            nowText.setTextColor(Color.parseColor("#000000"));
            nowSeatNum = info_userSeatNum.getText().toString();
            inComeback();
        }
        else{
            Toast.makeText(UserInfoActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //좌석변경
    @OnClick(id.user_Layout2)
    public void SeatChange(){
        String hiddenString = nowText.getText().toString();
        if(hiddenString.equals("입실") || hiddenString == "입실"){
            nowSeatNum=info_userSeatNum.getText().toString();
            Intent intent_seat = new Intent(UserInfoActivity.this, seatsetActivity.class);
            intent_seat.putExtra("seatNum", nowSeatNum);
            intent_seat.putExtra("ID",userID);
            intent_seat.putExtra("Time", userTime);
            startActivity(intent_seat);
        }
        else if(hiddenString.equals("외출") || hiddenString == "외출"){
            Toast.makeText(UserInfoActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(UserInfoActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void inOuting() {
        Insert outingInsert = new Insert();
        outingInsert.execute(userID, nowSeatNum, inOutingtime, inOutingtime_2, "외출");
    }

    private void inComeback() {
        Insert conbackInsert = new Insert();
        conbackInsert.execute(userID,nowSeatNum, inComebacktime, inComebacktime_2, "복귀");
    }

    private void inCheckout() {
        Insert checkoutInsert = new Insert();
        checkoutInsert.execute(userID, nowSeatNum ,inCheckouttime, inCheckouttime_2, "퇴실");
    }

    public void fetchAsyncPosts() {
        postlist = new ArrayList<>();
        Getjson getjson = new Getjson();
        getjson.execute(Api.GET_POST, userID, userTime);
    }
    public void CustomDialog_Checkout(){

    }
    public class Getjson extends AsyncTask<String, Void, Post[]> {

        @Override
        protected Post[] doInBackground(String... strings) {

            String url = strings[0];
            String id = strings[1];
            String date = strings[2];

            RequestBody formBody = new FormBody.Builder()
                    .add("ID", id)
                    .add("list_date", date)
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
                JsonElement rootObject = parser.parse(responses.body().charStream()).getAsJsonObject().get("seatlist");

                Post[] posts = gson.fromJson(rootObject, Post[].class);

                return posts;
            } catch (IOException e) {
                Log.d("FetchPostsTask", e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(Post[] posts) {
            super.onPostExecute(posts);
            if (posts.length > 0) {
                for (Post post : posts) {
                    postlist.add(post);
                }
                hiddenTxt.setText(postlist.get(0).getList_in_out());
                String sss=hiddenTxt.getText().toString();
                if(sss.equals("퇴실") || sss == "퇴실") {
                    outText.setText(postlist.get(0).getList_date());
                    outText2.setText(postlist.get(0).getList_date2());
                    info_userSpace.setText(postlist.get(0).getList_space());
                    info_userSeatNum.setText(postlist.get(0).getList_seatNum());
                    nowText.setText(postlist.get(0).getList_in_out());
                    nowText.setTextColor(Color.parseColor("#ff0000"));
                    inText.setText(postlist.get(1).getList_date());
                    inText2.setText(postlist.get(1).getList_date2());
                    postlist.clear();

                }
                else {
                    inText.setText(postlist.get(0).getList_date());
                    inText2.setText(postlist.get(0).getList_date2());
                    info_userSpace.setText(postlist.get(0).getList_space());
                    info_userSeatNum.setText(postlist.get(0).getList_seatNum());
                    nowText.setText(postlist.get(0).getList_in_out());
                    postlist.clear();
                }

            } else{
                inText.setText("");
                inText2.setText("");
                outText.setText("");
                outText2.setText("");
                info_userSpace.setText("");
                info_userSeatNum.setText("");
                nowText.setText("");
            }
        }
    }

    public class CustomDialog_Checkout {

        public void collDialog(){
            final Dialog dlg = new Dialog(UserInfoActivity.this);
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dlg.setContentView(layout.custom_checkoutdialog);

            dlg.show();
            final Button okbtn = (Button) dlg.findViewById(id.checkOut_okButton);
            final Button cancelbtn = (Button) dlg.findViewById(R.id.checkOut_cancelButton);

            okbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    inCheckouttime = sdf.format(new Date());
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                    inCheckouttime_2 = sdf2.format(new Date());
                    nowText.setText("퇴실");
                    nowText.setTextColor(Color.parseColor("#FF0000"));
                    nowSeatNum = info_userSeatNum.getText().toString();

                    inCheckout();
                    fetchAsyncPosts();

                    dlg.dismiss();
                }
            });
            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(UserInfoActivity.this, "취소 했습니다.", Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                }
            });
        }
    }
}