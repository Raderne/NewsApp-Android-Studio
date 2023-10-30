package com.red.newsapp.api_response;

import java.util.ArrayList;

public class Articles {
    private ArrayList<GONDER> articles;
    private String status;
    private int totalResults;

    public Articles(ArrayList<GONDER> articles, String status, int totalResults) {
        this.articles = articles;
        this.status = status;
        this.totalResults = totalResults;
    }

    public ArrayList<GONDER> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<GONDER> articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}