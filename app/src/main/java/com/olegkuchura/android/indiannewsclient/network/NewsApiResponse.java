package com.olegkuchura.android.indiannewsclient.network;

import com.google.gson.annotations.SerializedName;
import com.olegkuchura.android.indiannewsclient.model.PieceOfNews;

import java.util.List;

/**
 * Created by Oleg on 10.08.2017.
 */

public class NewsApiResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("source")
    private String source;

    @SerializedName("sortBy")
    private String sortBy;

    @SerializedName("articles")
    private List<PieceOfNews> articles;

    public NewsApiResponse(String status, String source, String sortBy, List<PieceOfNews> articles) {
        this.status = status;
        this.source = source;
        this.sortBy = sortBy;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<PieceOfNews> getArticles() {
        return articles;
    }

    public void setArticles(List<PieceOfNews> articles) {
        this.articles = articles;
    }
}
