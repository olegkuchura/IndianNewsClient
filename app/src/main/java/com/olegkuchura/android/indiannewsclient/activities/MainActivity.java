package com.olegkuchura.android.indiannewsclient.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.olegkuchura.android.indiannewsclient.R;
import com.olegkuchura.android.indiannewsclient.fragments.NewsListFragment;
import com.olegkuchura.android.indiannewsclient.fragments.PieceOfNewsFragment;
import com.olegkuchura.android.indiannewsclient.model.PieceOfNews;

public class MainActivity extends AppCompatActivity implements NewsListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MyLog", "MainActivity.onCreate()");
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(NewsListFragment.TAG) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fl_fragment_container, NewsListFragment.newInstance(), NewsListFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onNewsSelected(PieceOfNews pieceOfNews) {
        PieceOfNewsFragment fragment = PieceOfNewsFragment.newInstance(pieceOfNews.getUrl());

        if (findViewById(R.id.fl_detailed_fragment_container) == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_fragment_container, fragment, PieceOfNewsFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_detailed_fragment_container, fragment, PieceOfNewsFragment.TAG)
                    .commit();
        }
    }
}
