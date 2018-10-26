package com.spacemonster.book.book;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText userId;
    TextInputEditText userPass;
    Button userLoginbtn;
    String text1;
    String logindate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = (TextInputEditText)findViewById(R.id.login_ID);
        userPass = (TextInputEditText)findViewById(R.id.login_Pass);

        userLoginbtn = (Button)findViewById(R.id.login_Btn);

        userLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //확인시 시간 정하기
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                logindate = sdf.format(new Date());
                text1 = userId.getText().toString();

                Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
                intent_main.putExtra("ID", text1);
                startActivity(intent_main);
                finish();

                MainActivity mainActivity = (MainActivity)MainActivity.Main_Activity;
                mainActivity.finish();

                insertoToDB();

            }
        });
    }

    private void insertoToDB() {
        InsertData insertData = new InsertData();
        insertData.execute(text1,logindate,"입실");


    }

   public static class InsertData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String id = strings[0];
            String date = strings[1];
            String nowSeat = strings[2];

            String link = "http://jbh9022.cafe24.com/insertList.php";

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("list_ID", id)
                    .add("list_date", date)
                    .add("list_in_out",nowSeat)
                    .build();

            Request request = new Request.Builder()
                    .url(link)
                    .post(formBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
