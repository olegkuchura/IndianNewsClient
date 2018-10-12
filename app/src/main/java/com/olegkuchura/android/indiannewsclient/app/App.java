package com.olegkuchura.android.indiannewsclient.app;

import android.app.Application;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.olegkuchura.android.indiannewsclient.network.ApiNewsService;
import com.olegkuchura.android.indiannewsclient.network.DateDeserializer;
import com.olegkuchura.android.indiannewsclient.network.UriDeserializer;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static ApiNewsService newsService = null;

    private final static String API_URL = "https://newsapi.org";

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriDeserializer())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        newsService = retrofit.create(ApiNewsService.class);
    }

    public static ApiNewsService getNewsService() {
        return newsService;
    }
}
