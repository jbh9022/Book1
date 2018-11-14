package com.spacemonster.book.book.Network;

import android.os.AsyncTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Update_Another extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String seatNow = strings[0];
        String id = strings[1];
        String date = strings[2];
        String seatnumber = strings[3];
//        "외출",userID, userTime, nowSeatNum
        String link = Api.UPDATESEATAN_POST;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("list_in_out", seatNow)
                .add("ID", id)
                .add("list_date", date)
                .add("list_seatNum", seatnumber)
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
