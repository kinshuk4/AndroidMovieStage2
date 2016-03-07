package com.k2udacity.moviestage2.provider.movie;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.k2udacity.moviestage2.provider.base.AbstractSelection;

public class MovieSelection extends AbstractSelection<MovieSelection> {
    @Override
    protected Uri baseUri() {
        return MovieColumns.CONTENT_URI;
    }

    @Override
    public MovieCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = super.query(contentResolver,projection);
        if (cursor == null)
            return null;
        return new MovieCursor(cursor);
    }

    @Override
    public MovieCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    @Override
    public MovieCursor query(Context context, String[] projection) {
        Cursor cursor = super.query(context,projection);
        if (cursor == null) return null;
        return new MovieCursor(cursor);
    }

   @Override
    public MovieCursor query(Context context) {
        return query(context, null);
    }


    public MovieSelection id(long... value) {
        addEquals("movie." + MovieColumns._ID, toObjectArray(value));
        return this;
    }


    public MovieSelection movieId(Long... value) {
        addEquals(MovieColumns.MOVIE_ID, value);
        return this;
    }


    public MovieSelection title(String... value) {
        addEquals(MovieColumns.TITLE, value);
        return this;
    }


}
