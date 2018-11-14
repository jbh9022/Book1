package com.spacemonster.book.book.Network;

import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//좌석 변경
public class Update_seatNum extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {

        String seatNum = strings[0];
        String id = strings[1];
        String date = strings[2];
        String seatNow = strings[3];

        String link = Api.UPDATESEAT_POST;

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("list_seatNum", seatNum)
                .add("ID", id)
                .add("list_date", date)
                .add("list_in_out", seatNow)
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
