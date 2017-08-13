package com.olegkuchura.android.indiannewsclient.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oleg on 11.08.2017.
 */

public class DateDeserializer implements JsonDeserializer<Date> {
    private static final String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        String dateAsString = json.getAsString();
        Date date = null;
        try {
            date = dateFormat.parse(dateAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
