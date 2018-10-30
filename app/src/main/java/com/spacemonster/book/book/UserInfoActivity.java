package com.spacemonster.book.book;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.spacemonster.book.book.Modle.Post;
import com.spacemonster.book.book.Network.Api;


import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.spacemonster.book.book.R.*;

public class UserInfoActivity extends AppCompatActivity {

    private TextView txt1;
    private TextView txt2;
    private TextView txt3;

    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout user_Backlayout;
    private Button btn2;
    private Button btn3;
    private Button outBtn;
    private TextView nowText;
    private TextView inText;
    private TextView inText2;
    private TextView outText;
    private TextView outText2;
    String userString;
    String userTime;
    ArrayList<Post> postlist;
    String test1time;
    String test1time_2;
    String test2time;
    String test2time_2;
    String test3time;
    String test3time_2;
    String newdate1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_user_info);

        Intent intent_UserInfo = getIntent();
        userString = intent_UserInfo.getStringExtra("ID");
        userTime = intent_UserInfo.getStringExtra("Time");

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date newdate = null;
//        try {
//            newdate = dateFormat.parse(userTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//        newdate1 = dateFormat1.format(newdate);

        fetchAsyncPosts();
//        fetchAsyncPosts2();

        txt1 = (TextView) findViewById(id.user_txt1);
        txt2 = (TextView) findViewById(id.user_txt2);
        txt3 = (TextView) findViewById(id.user_txt3);
        //입실상태
        nowText = (TextView) findViewById(id.user_NowTxt);
        //들어간시간
        inText = (TextView) findViewById(id.user_Intxt);
        outText = (TextView) findViewById(id.user_OutTxt);
        inText2 = (TextView) findViewById(id.user_Intxt2);
        outText2 = (TextView) findViewById(id.user_OutTxt2);

        btn2 = (Button) findViewById(id.user_btn2);
        btn3 = (Button) findViewById(id.user_btn3);
        outBtn = (Button) findViewById(id.user_Outbtn);

        layout1 = (LinearLayout) findViewById(id.user_Layout1);
        layout2 = (LinearLayout) findViewById(id.user_Layout2);
        user_Backlayout = (LinearLayout) findViewById(id.user_Backlayout);

        txt1.setText(userString);

        user_Backlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //외출
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                test1time = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                test1time_2 = sdf2.format(new Date());
                nowText.setText("외출");
                nowText.setTextColor(Color.parseColor("#0000ff"));
                test1();
            }
        });

        //복귀
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                test2time = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                test2time_2 = sdf2.format(new Date());
                nowText.setText("입실");
                nowText.setTextColor(Color.parseColor("#000000"));
                test2();
            }
        });
        //퇴실
        outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                test3time = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                test3time_2 = sdf2.format(new Date());
                nowText.setText("퇴실");
                nowText.setTextColor(Color.parseColor("#FF0000"));
                test3();

                endlist();
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserInfoActivity.this, "좌석변경(예정)", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void test1() {
        LoginActivity.InsertData test1Insert = new LoginActivity.InsertData();
        test1Insert.execute(userString, test1time,test1time_2, "외출");
    }

    private void test2() {
        LoginActivity.InsertData test2Insert = new LoginActivity.InsertData();
        test2Insert.execute(userString, test2time,test2time_2, "복귀");
    }

    private void test3() {
        LoginActivity.InsertData test3Insert = new LoginActivity.InsertData();
        test3Insert.execute(userString, test3time,test3time_2, "퇴실");
    }

    public void fetchAsyncPosts() {
        postlist = new ArrayList<>();
        Getjson getjson = new Getjson();
        getjson.execute(Api.GET_POST, userString, userTime, "입실");
    }

    public void endlist() {
        Getjson getjson = new Getjson();
        getjson.execute(Api.GET_POST, userString, test3time, "퇴실");
    }

    public class Getjson extends AsyncTask<String, Void, Post[]> {


        @Override
        protected Post[] doInBackground(String... strings) {

            String url = strings[0];
            String id = strings[1];
            String date = strings[2];
            String in_out = strings[3];

            RequestBody formBody = new FormBody.Builder()
                    .add("list_ID", id)
                    .add("list_date", date)
                    .add("list_in_out", in_out)
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
            }

            if (postlist.get(0).getList_in_out().equals("입실")) {
                inText.setText(postlist.get(0).getList_date());
                inText2.setText(postlist.get(0).getList_date2());
                txt2.setText(postlist.get(0).getList_space());
                txt3.setText(postlist.get(0).getList_seatNum());
                postlist.clear();
            }
            else{
                outText.setText(postlist.get(0).getList_date());
                outText2.setText(postlist.get(0).getList_date2());
                postlist.clear();
            }
        }

    }
}