package com.olegkuchura.android.indiannewsclient.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.olegkuchura.android.indiannewsclient.R;
import com.olegkuchura.android.indiannewsclient.adapters.NewsRecyclerAdapter;
import com.olegkuchura.android.indiannewsclient.listeners.OnNewsRecyclerItemClickListener;
import com.olegkuchura.android.indiannewsclient.network.NewsApiResponse;
import com.olegkuchura.android.indiannewsclient.network.NewsErrorItem;
import com.olegkuchura.android.indiannewsclient.model.PieceOfNews;
import com.olegkuchura.android.indiannewsclient.network.ApiCallback;
import com.olegkuchura.android.indiannewsclient.network.RestClient;
import com.olegkuchura.android.indiannewsclient.storages.Database;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;

public class NewsListFragment extends Fragment implements OnNewsRecyclerItemClickListener {
    public static final String TAG = NewsListFragment.class.getSimpleName();

    private NewsRecyclerAdapter adapter;
    private Callbacks callbacks;

    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database.newInstance(getActivity().getApplicationContext()).open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MyLog", "NewsListFragment.onCreateView()");
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.rv_news_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsRecyclerAdapter(new ArrayList<PieceOfNews>(), getActivity().getApplicationContext(), this);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.my_divider));
        recyclerView.addItemDecoration(divider);

        refreshLayout = v.findViewById(R.id.srl_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews();
            }
        });

        progressBar = v.findViewById(R.id.pb_progress);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayNewsFromDatabase();
        loadNews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Database.newInstance(getActivity().getApplicationContext()).close();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    private void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    private void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void displayNewsFromDatabase() {
        List<PieceOfNews> news = Database.newInstance(getActivity().getApplicationContext()).getAllNews();
        adapter.setNews(news);
        adapter.notifyDataSetChanged();
    }

    private void loadNews() {
        if (adapter.isListEmpty()) {
            showProgressBar();
        }
        RestClient.getInstance().getService().getNews(new ApiCallback<NewsApiResponse>() {
            @Override
            public void success(NewsApiResponse newsApiResponse, Response response) {
                Log.d("MyLog", "loadNews().success()");
                List<PieceOfNews> news = newsApiResponse.getArticles();
                for (int i = 0; i < news.size(); i++) {
                    if (news.get(i).getDate() == null) {
                        news.remove(i);
                        i--;
                    }
                }
                Database.newInstance(getActivity().getApplicationContext()).addNews(news);
                displayNewsFromDatabase();
                hideProgressBar();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(NewsErrorItem newsError) {
                Log.d("MyLog", "loadNews().failure() custom " + newsError.getMessage() + "   " + newsError.getCode());
                Toast.makeText(getActivity(), "network error", Toast.LENGTH_LONG).show();
                hideProgressBar();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemClick(View v, int position, PieceOfNews pieceOfNews) {
        callbacks.onNewsSelected(pieceOfNews);
    }


    /**
     * Required interface for host activity.
     */
    public interface Callbacks {
        void onNewsSelected(PieceOfNews pieceOfNews);
    }
}
