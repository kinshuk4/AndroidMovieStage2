package com.k2udacity.moviestage2.provider.review;

import android.support.annotation.Nullable;

public interface IReviewCursorRepo {


    @Nullable
    String getReviewId();


    @Nullable
    String getAuthor();


    @Nullable
    String getContent();


    @Nullable
    String getUrl();

    long getMovieId();
}
