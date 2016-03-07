package com.k2udacity.moviestage2.provider.review;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.k2udacity.moviestage2.provider.base.AbstractSelection;
import com.k2udacity.moviestage2.provider.movie.MovieColumns;


public class ReviewSelection extends AbstractSelection<ReviewSelection> {
    @Override
    protected Uri baseUri() {
        return ReviewColumns.CONTENT_URI;
    }

    public ReviewCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = super.query(contentResolver,projection);
        if (cursor == null)
            return null;
        return new ReviewCursor(cursor);
    }

    @Override
    public ReviewCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    @Override
    public ReviewCursor query(Context context, String[] projection) {
        Cursor cursor = super.query(context, projection);
        if (cursor == null)
            return null;
        return new ReviewCursor(cursor);
    }

    @Override
    public ReviewCursor query(Context context) {
        return query(context, null);
    }


    public ReviewSelection id(long... value) {
        addEquals("review." + ReviewColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection content(String... value) {
        addEquals(ReviewColumns.CONTENT, value);
        return this;
    }

    public ReviewSelection movieId(long... value) {
        addEquals(ReviewColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewSelection movieMovieId(Long... value) {
        addEquals(MovieColumns.MOVIE_ID, value);
        return this;
    }

}
