package com.k2udacity.moviestage2.provider.movie;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.k2udacity.moviestage2.provider.base.AbstractCursor;
import com.k2udacity.moviestage2.utils.ErrorCode;


public class MovieCursor extends AbstractCursor implements IMovieCursorRepo {
    public MovieCursor(Cursor cursor) {
        super(cursor);
    }

    public long getId() {
        Long res = getLong(MovieColumns._ID);
        if (res == null)
            throw new NullPointerException(ErrorCode.PRIMARY_KEY_NULL.toString());
        return res;
    }

    @Nullable
    public Long getMovieId() {
        Long res = getLong(MovieColumns.MOVIE_ID);
        return res;
    }


    @Nullable
    public String getBackdropPath() {
        String res = getString(MovieColumns.BACKDROP_PATH);
        return res;
    }


    @Nullable
    public String getOverview() {
        String res = getString(MovieColumns.OVERVIEW);
        return res;
    }


    @Nullable
    public String getReleaseDate() {
        String res = getString(MovieColumns.RELEASE_DATE);
        return res;
    }


    @Nullable
    public String getPosterPath() {
        String res = getString(MovieColumns.POSTER_PATH);
        return res;
    }


    @Nullable
    public String getTitle() {
        String res = getString(MovieColumns.TITLE);
        return res;
    }


    @Nullable
    public Double getVoteAverage() {
        Double res = getDouble(MovieColumns.VOTE_AVERAGE);
        return res;
    }
}
