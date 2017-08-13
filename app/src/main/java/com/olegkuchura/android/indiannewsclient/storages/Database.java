package com.olegkuchura.android.indiannewsclient.storages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.olegkuchura.android.indiannewsclient.model.PieceOfNews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Oleg on 11.08.2017.
 */

public class Database {

    private static Database database = null;

    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper helper;

    private Database(Context context) {
        this.context = context;
        sqLiteDatabase = null;
        helper = null;
    }

    public static Database newInstance(Context context) {
        if (database == null) {
            database = new Database(context);
        }
        return database;
    }

    public void open() {
        if (context != null) {
            DBHelper helper = new DBHelper(context);
            sqLiteDatabase = helper.getWritableDatabase();
        }
    }

    public void close() {
        if (helper != null) {
            helper.close();
            helper = null;
            sqLiteDatabase = null;
        }
    }

    public void addNews(List<PieceOfNews> news) {
        if (sqLiteDatabase == null){
            return;
        }
        ContentValues cv;
        for(PieceOfNews n: news) {
            cv = new ContentValues();
            cv.put(DBHelper.COL_TITLE, n.getTitle());
            cv.put(DBHelper.COL_AUTHOR, n.getAuthor());
            cv.put(DBHelper.COL_DESCRIPTION, n.getDescription());
            if (n.getDate() != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cv.put(DBHelper.COL_DATE, format.format(n.getDate()));
            }
            cv.put(DBHelper.COL_URL, n.getUrl());
            cv.put(DBHelper.COL_URL_TO_IMAGE, n.getUrlToImage());
            sqLiteDatabase.insert(DBHelper.TABLE_NEWS, null, cv);
        }
    }

    public List<PieceOfNews> getAllNews() {
        if (sqLiteDatabase == null){
            return null;
        }
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NEWS, null, null, null, null, null, DBHelper.COL_DATE + " DESC");
        return turnCursorIntoList(cursor);
    }

    private List<PieceOfNews> turnCursorIntoList(Cursor cursor) {
        List<PieceOfNews> news = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                String dateAsString = cursor.getString(cursor.getColumnIndex(DBHelper.COL_DATE));
                Date date = null;
                if (dateAsString != null){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date = format.parse(dateAsString);
                }
                PieceOfNews pieceOfNews = new PieceOfNews(
                        cursor.getString(cursor.getColumnIndex(DBHelper.COL_TITLE)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.COL_AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.COL_DESCRIPTION)),
                        date,
                        cursor.getString(cursor.getColumnIndex(DBHelper.COL_URL)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.COL_URL_TO_IMAGE)) );
                news.add(pieceOfNews);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        return news;
    }

    private class DBHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "news_db";
        private static final int DB_VERSION = 1;

        private static final String TABLE_NEWS = "news";
        private static final String COL_ID = "_id";
        private static final String COL_TITLE = "title";
        private static final String COL_AUTHOR = "author";
        private static final String COL_DESCRIPTION = "description";
        private static final String COL_DATE = "date_of_news";
        private static final String COL_URL = "url";
        private static final String COL_URL_TO_IMAGE = "urlToImage";

        private static final String TRIG_NAME = "limit_trig";
        private static final int MAX_LINE = 30;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d("MyLog", "Database.OnCreate()");
            sqLiteDatabase.execSQL("create table " + TABLE_NEWS + " ( " +
                    COL_ID + " integer primary key autoincrement, " +
                    COL_TITLE + " texteger, " +
                    COL_AUTHOR + " text, " +
                    COL_DESCRIPTION + " text, " +
                    COL_DATE + " text, " +
                    COL_URL + " text, " +
                    COL_URL_TO_IMAGE + " text, " +
                    " UNIQUE ( " + COL_TITLE + " ) ON CONFLICT IGNORE" +
                    ")");

            sqLiteDatabase.execSQL("CREATE TRIGGER [" + TRIG_NAME + "]" +
                    " AFTER INSERT ON [" + TABLE_NEWS + "]" +
                    " WHEN (SELECT count(*) FROM " + TABLE_NEWS + ") > " + MAX_LINE +
                    " BEGIN" +
                    " delete from " + TABLE_NEWS +
                    " where " + COL_DATE + " = (select min(" + COL_DATE + ") from " + TABLE_NEWS + ");" +
                    //" where " + COL_DATE + " = (select " + COL_DATE + " from " + TABLE_NEWS + " order by " + COL_DATE + " limit 1);" +
                    " END;");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
