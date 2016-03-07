package com.k2udacity.moviestage2.apis.model;

import com.k2udacity.moviestage2.provider.review.ReviewCursor;

public class ReviewApiModel {
    private String id;
    private String author;
    private String content;
    private String url;

    public ReviewApiModel(ReviewCursor c) {
        id = c.getReviewId();
        author = c.getAuthor();
        content = c.getContent();
        url = c.getUrl();
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
