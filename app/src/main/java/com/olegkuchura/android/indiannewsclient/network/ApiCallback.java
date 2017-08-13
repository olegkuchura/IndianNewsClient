package com.olegkuchura.android.indiannewsclient.network;

import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Oleg on 09.08.2017.
 */

public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void failure(NewsErrorItem newsError);

    @Override
    public void failure(RetrofitError error) {
        Log.d("MyLog", "ApiCallback.failure() " + error.getBody() + error.getCause());

        NewsErrorItem newsError = null;

        try {
            newsError = (NewsErrorItem) error.getBodyAs(NewsErrorItem.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
            Log.d("MyLog", "ApiCallback.failure()" );
        }

        if (newsError != null) {
            failure(newsError);
        } else {
            Response errorResponse = error.getResponse();
            if (errorResponse == null) {
                failure(new NewsErrorItem("message", "code"));
                return;
            }
            if (error.getKind() == RetrofitError.Kind.NETWORK) {
                failure(new NewsErrorItem("Cant connect to network", "http://internet.com"));
            } else {
                failure(new NewsErrorItem("Error code: " + String.valueOf(errorResponse.getStatus()), "http://internet.com"));
            }
        }
    }
}
