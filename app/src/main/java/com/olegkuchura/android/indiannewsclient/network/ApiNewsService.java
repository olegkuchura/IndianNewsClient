package com.olegkuchura.android.indiannewsclient.network;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Oleg on 09.08.2017.
 */

public interface ApiNewsService {
    String API_KEY = "48e396c41a5d42f58bef4bdae56adf4f";
    String SORT_TYPE = "latest";

    @GET("/v1/articles?source=the-times-of-india&sortBy=" + SORT_TYPE + "&apiKey=" + API_KEY)
    Call<NewsResponse> getNews();

}
