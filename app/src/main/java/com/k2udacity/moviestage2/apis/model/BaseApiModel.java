package com.k2udacity.moviestage2.apis.model;

import java.util.List;

public class BaseApiModel<T> {
    private int page;
    private int total_pages;
    private long total_results;
    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    public long getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getPage() {
        return page;
    }


}
