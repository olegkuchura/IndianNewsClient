package com.olegkuchura.android.indiannewsclient.network;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;
import java.util.Date;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Oleg on 09.08.2017.
 */

public class RestClient {
    private final static String API_URL = "https://newsapi.org";

    private static RestClient restClient = null;

    private ApiService service;
    private RestAdapter restAdapter;
    private Gson gson;

    private RestClient() {

        gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriDeserializer())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(ApiService.class);
    }

    public static RestClient getInstance() {
        if (restClient == null) {
            restClient = new RestClient();
        }
        return restClient;
    }

    public ApiService getService() {
        return service;
    }
}
