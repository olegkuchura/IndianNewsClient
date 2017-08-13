package com.olegkuchura.android.indiannewsclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.olegkuchura.android.indiannewsclient.R;
import com.olegkuchura.android.indiannewsclient.listeners.OnNewsRecyclerItemClickListener;
import com.olegkuchura.android.indiannewsclient.model.PieceOfNews;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Oleg on 09.08.2017.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>{

    List<PieceOfNews> news;
    Context context;
    OnNewsRecyclerItemClickListener listener;

    public NewsRecyclerAdapter(List<PieceOfNews> n, Context context, OnNewsRecyclerItemClickListener listener) {
        news = n;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                listener.onItemClick(view, position, news.get(position));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(news.get(position).getTitle());
        holder.author.setText(news.get(position).getAuthor());
        holder.date.setText(news.get(position).getDateAsString());
        holder.description.setText(news.get(position).getDescription());
        Picasso.with(context)
                .load(news.get(position).getUrlToImage())
                .placeholder(R.drawable.ic_action_name)
                .error(R.drawable.ic_action_name)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setNews(List<PieceOfNews> news) {
        this.news = news;
    }

    public boolean isListEmpty() {
        if (news != null) {
            return news.isEmpty();
        }
        return false;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView author;
        TextView date;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_news_image);
            title = itemView.findViewById(R.id.tv_title);
            author = itemView.findViewById(R.id.tv_author);
            date = itemView.findViewById(R.id.tv_date);
            description = itemView.findViewById(R.id.tv_description);
        }

    }
}
