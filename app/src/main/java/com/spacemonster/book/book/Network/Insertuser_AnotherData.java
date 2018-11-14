package com.spacemonster.book.book.Network;

import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Insertuser_AnotherData extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {

        String id = strings[0];
        String seatnum = strings[1];
        String date = strings[2];
        String date2 = strings[3];
        String nowSeat = strings[4];

        String link = Api.INSERT2_POST;

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("ID", id)
                .add("list2_seatNum", seatnum)
                .add("list2_date", date)
                .add("list2_date2", date2)
                .add("list2_in_out", nowSeat)
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
