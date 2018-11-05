package com.spacemonster.book.book;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.spacemonster.book.book.Modle.User;
import com.spacemonster.book.book.Network.Api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_ID) TextInputEditText userId;
    @BindView(R.id.login_Pass) TextInputEditText userPass;

    @BindView(R.id.login_Btn) Button userLoginbtn;
    @BindView(R.id.login_Txt1) TextView hiddenTxt1;
    @BindView(R.id.login_Txt2) TextView hiddenTxt2;

    String userID_txt;
    String userPass_txt;
    String logindate;
    String logindate2;
    String checkUserID;
    String checkUserPass;
    private ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        userArrayList = new ArrayList<>();

        userLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //확인시 시간 정하기
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                logindate = sdf.format(new Date());
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                logindate2 = sdf2.format(new Date());
                userID_txt = userId.getText().toString();
                userPass_txt = userPass.getText().toString();

                if(userID_txt.equals("") || userID_txt == null){
                    Toast.makeText(LoginActivity.this,"잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkID();
                }
            }
        });
    }

    public void checkID(){
        GetUser user = new GetUser();
        user.execute(userID_txt,userPass_txt);
    }
    public class GetUser extends AsyncTask<String, Void, User[] >{
        @Override
        protected User[] doInBackground(String... strings) {
            String id = strings[0];
            String pass = strings[1];

            String url = Api.CHECKUSER_POST;

            RequestBody formBody = new FormBody.Builder()
                    .add("ID", id)
                    .add("PASSWORD", pass)
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
                JsonElement rootObject = parser.parse(responses.body().charStream()).getAsJsonObject().get("checkUser");

                User[] users = gson.fromJson(rootObject, User[].class);

                return users;
            } catch (IOException e) {
                Log.d("FetchPostsTask", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(User[] users) {
            super.onPostExecute(users);
            if (users.length > 0) {
                for (User user : users) {
                    userArrayList.add(user);
                }
                hiddenTxt1.setText(userArrayList.get(0).getID());
            }
            else{
                hiddenTxt1.setText("");
            }
            checkUserID=hiddenTxt1.getText().toString();
            checkUserPass=hiddenTxt2.getText().toString();
            if(checkUserID == userID_txt  || checkUserID.equals(userID_txt) ){
                Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
                intent_main.putExtra("ID", userID_txt);
                startActivity(intent_main);
                finish();

                MainActivity mainActivity = (MainActivity)MainActivity.Main_Activity;
                mainActivity.finish();
            }
            else{
                Toast.makeText(LoginActivity.this, "아이디, 비밀번호를 확인해주시기바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
