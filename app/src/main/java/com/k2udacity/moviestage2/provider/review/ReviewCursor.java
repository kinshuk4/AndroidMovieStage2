package com.k2udacity.moviestage2.provider.review;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.k2udacity.moviestage2.provider.movie.MovieColumns;
import com.k2udacity.moviestage2.provider.base.AbstractCursor;
import com.k2udacity.moviestage2.utils.ErrorCode;

public class ReviewCursor extends AbstractCursor implements IReviewCursorRepo {
    public ReviewCursor(Cursor cursor) {
        super(cursor);
    }

    public long getId() {
        Long res = getLong(ReviewColumns._ID);
        if (res == null)
            throw new NullPointerException(ErrorCode.PRIMARY_KEY_NULL.toString());
        return res;
    }


    @Nullable
    public String getReviewId() {
        String res = getString(ReviewColumns.REVIEW_ID);
        return res;
    }


    @Nullable
    public String getAuthor() {
        String res = getString(ReviewColumns.AUTHOR);
        return res;
    }


    @Nullable
    public String getContent() {
        String res = getString(ReviewColumns.CONTENT);
        return res;
    }


    @Nullable
    public String getUrl() {
        String res = getString(ReviewColumns.URL);
        return res;
    }


    public long getMovieId() {
        Long res = getLong(ReviewColumns.MOVIE_ID);
        if (res == null)
            throw new NullPointerException(ErrorCode.PRIMARY_KEY_NULL.toString());
        return res;
    }


    @Nullable
    public Long getMovieMovieId() {
        Long res = getLong(MovieColumns.MOVIE_ID);
        return res;
    }


    @Nullable
    public String getMovieBackdropPath() {
        String res = getString(MovieColumns.BACKDROP_PATH);
        return res;
    }


    @Nullable
    public String getMovieOverview() {
        String res = getString(MovieColumns.OVERVIEW);
        return res;
    }


    @Nullable
    public String getMovieReleaseDate() {
        String res = getString(MovieColumns.RELEASE_DATE);
        return res;
    }


    @Nullable
    public String getMoviePosterPath() {
        String res = getString(MovieColumns.POSTER_PATH);
        return res;
    }


    @Nullable
    public String getMovieTitle() {
        String res = getString(MovieColumns.TITLE);
        return res;
    }


    @Nullable
    public Double getMovieVoteAverage() {
        Double res = getDouble(MovieColumns.VOTE_AVERAGE);
        return res;
    }
}
