package com.k2udacity.moviestage2.provider.movie;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.k2udacity.moviestage2.provider.base.AbstractContentValuesWrapper;

public class MovieContentValues extends AbstractContentValuesWrapper {
    @Override
    public Uri getUri() {
        return MovieColumns.CONTENT_URI;
    }


    public int update(ContentResolver contentResolver, @Nullable MovieSelection where) {
        return contentResolver.update(getUri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }


    public int update(Context context, @Nullable MovieSelection where) {
        return context.getContentResolver().update(getUri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MovieContentValues putMovieId(@Nullable Long value) {
        mContentValues.put(MovieColumns.MOVIE_ID, value);
        return this;
    }

    public MovieContentValues putMovieIdNull() {
        mContentValues.putNull(MovieColumns.MOVIE_ID);
        return this;
    }

    public MovieContentValues putBackdropPath(@Nullable String value) {
        mContentValues.put(MovieColumns.BACKDROP_PATH, value);
        return this;
    }

    public MovieContentValues putBackdropPathNull() {
        mContentValues.putNull(MovieColumns.BACKDROP_PATH);
        return this;
    }

    public MovieContentValues putOverview(@Nullable String value) {
        mContentValues.put(MovieColumns.OVERVIEW, value);
        return this;
    }

    public MovieContentValues putOverviewNull() {
        mContentValues.putNull(MovieColumns.OVERVIEW);
        return this;
    }

    public MovieContentValues putReleaseDate(@Nullable String value) {
        mContentValues.put(MovieColumns.RELEASE_DATE, value);
        return this;
    }

    public MovieContentValues putReleaseDateNull() {
        mContentValues.putNull(MovieColumns.RELEASE_DATE);
        return this;
    }

    public MovieContentValues putPosterPath(@Nullable String value) {
        mContentValues.put(MovieColumns.POSTER_PATH, value);
        return this;
    }

    public MovieContentValues putPosterPathNull() {
        mContentValues.putNull(MovieColumns.POSTER_PATH);
        return this;
    }

    public MovieContentValues putTitle(@Nullable String value) {
        mContentValues.put(MovieColumns.TITLE, value);
        return this;
    }

    public MovieContentValues putTitleNull() {
        mContentValues.putNull(MovieColumns.TITLE);
        return this;
    }

    public MovieContentValues putVoteAverage(@Nullable Double value) {
        mContentValues.put(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MovieContentValues putVoteAverageNull() {
        mContentValues.putNull(MovieColumns.VOTE_AVERAGE);
        return this;
    }
}
