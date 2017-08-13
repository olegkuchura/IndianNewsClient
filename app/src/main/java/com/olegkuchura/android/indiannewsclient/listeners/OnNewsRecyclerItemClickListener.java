package com.olegkuchura.android.indiannewsclient.listeners;

import android.view.View;

import com.olegkuchura.android.indiannewsclient.model.PieceOfNews;

/**
 * Created by Oleg on 12.08.2017.
 */

public interface OnNewsRecyclerItemClickListener {

    public void onItemClick(View v, int position, PieceOfNews pieceOfNews);

}
