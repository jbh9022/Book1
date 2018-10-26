//package com.spacemonster.book.book.Network;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParser;
//import com.spacemonster.book.book.Modle.Post;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//
//public class Getjson extends AsyncTask<String, Void, Post[]> {
//
//    ArrayList<Post> postlist;
//
//    @Override
//    protected Post[] doInBackground(String... strings) {
//        postlist = new ArrayList<>();
//
//        String url = strings[0];
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//
//        try {
//           Response responses = client.newCall(request).execute();
//
//            Gson gson = new Gson();
//            JsonParser parser = new JsonParser();
//            JsonElement rootObject = parser.parse(responses.body().charStream()).getAsJsonObject().get("seatlist");
//
//            Post[] posts = gson.fromJson(rootObject, Post[].class);
//            return posts;
//        }
//        catch (IOException e){
//            Log.d("FetchPostsTask",e.getMessage());
//            return null;
//        }
//
//    }
//
//    @Override
//    protected void onPostExecute(Post[] posts) {
//        super.onPostExecute(posts);
//
//        for(Post post : posts) {
//            postlist.add(post);
//        }
//
//    }
//
//
//}
