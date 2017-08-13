package com.olegkuchura.android.indiannewsclient.network;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Oleg on 10.08.2017.
 */

public class UriDeserializer implements JsonDeserializer<Uri> {
    @Override
    public Uri deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.d("MyLog", "UriDeserializer.deserialize()");
        String urlString = json.getAsString();

        Uri uri = Uri.parse(urlString);

        return uri;
    }
}
