package com.k2udacity.moviestage2.provider.video;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.k2udacity.moviestage2.provider.base.AbstractSelection;
import com.k2udacity.moviestage2.provider.movie.MovieColumns;

public class VideoSelection extends AbstractSelection<VideoSelection> {
    @Override
    protected Uri baseUri() {
        return VideoColumns.CONTENT_URI;
    }


    public VideoCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new VideoCursor(cursor);
    }


    public VideoCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    @Override
    public VideoCursor query(Context context, String[] projection) {
        Cursor cursor = super.query(context, projection);
        if (cursor == null) return null;
        return new VideoCursor(cursor);
    }

    @Override
    public VideoCursor query(Context context) {
        return query(context, null);
    }


    public VideoSelection id(long... value) {
        addEquals("video." + VideoColumns._ID, toObjectArray(value));
        return this;
    }

    public VideoSelection name(String... value) {
        addEquals(VideoColumns.NAME, value);
        return this;
    }


    public VideoSelection key(String... value) {
        addEquals(VideoColumns.KEY, value);
        return this;
    }

    public VideoSelection size(Integer... value) {
        addEquals(VideoColumns.SIZE, value);
        return this;
    }



    public VideoSelection movieId(long... value) {
        addEquals(VideoColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }


    public VideoSelection movieMovieId(Long... value) {
        addEquals(MovieColumns.MOVIE_ID, value);
        return this;
    }
}
