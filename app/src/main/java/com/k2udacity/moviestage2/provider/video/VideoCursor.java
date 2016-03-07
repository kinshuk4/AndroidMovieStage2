package com.k2udacity.moviestage2.provider.video;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.k2udacity.moviestage2.provider.base.AbstractCursor;
import com.k2udacity.moviestage2.utils.ErrorCode;


public class VideoCursor extends AbstractCursor implements IVideoCursorRepo {
    public VideoCursor(Cursor cursor) {
        super(cursor);
    }

    public long getId() {
        Long res = getLong(VideoColumns._ID);
        if (res == null)
            throw new NullPointerException(ErrorCode.PRIMARY_KEY_NULL.toString());
        return res;
    }


    @Nullable
    public String getVideoId() {
        String res = getString(VideoColumns.VIDEO_ID);
        return res;
    }


    @Nullable
    public String getName() {
        String res = getString(VideoColumns.NAME);
        return res;
    }


    @Nullable
    public String getKey() {
        String res = getString(VideoColumns.KEY);
        return res;
    }


    @Nullable
    public Integer getSize() {
        Integer res = getInteger(VideoColumns.SIZE);
        return res;
    }


    @Nullable
    public String getType() {
        String res = getString(VideoColumns.TYPE);
        return res;
    }


    public long getMovieId() {
        Long res = getLong(VideoColumns.MOVIE_ID);
        if (res == null)
            throw new NullPointerException(ErrorCode.PRIMARY_KEY_NULL.toString());
        return res;
    }
}
