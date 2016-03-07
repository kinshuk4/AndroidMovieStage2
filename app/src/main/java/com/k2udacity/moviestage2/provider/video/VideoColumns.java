package com.k2udacity.moviestage2.provider.video;

import android.net.Uri;
import android.provider.BaseColumns;

import com.k2udacity.moviestage2.provider.movie.MovieColumns;
import com.k2udacity.moviestage2.provider.MovieProvider;


public class VideoColumns implements BaseColumns {
    public static final String TABLE_NAME = "video";
    public static final Uri CONTENT_URI = Uri.parse(MovieProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = BaseColumns._ID;
    public static final String VIDEO_ID = "video_id";
    public static final String NAME = "name";
    public static final String KEY = "key";
    public static final String SIZE = "size";
    public static final String TYPE = "type";
    public static final String MOVIE_ID = "video__movie_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            VIDEO_ID,
            NAME,
            KEY,
            SIZE,
            TYPE,
            MOVIE_ID
    };

    public static final String PREFIX_MOVIE = TABLE_NAME + "__" + MovieColumns.TABLE_NAME;
}
