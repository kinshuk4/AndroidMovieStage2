package com.k2udacity.moviestage2.provider.review;

import android.net.Uri;
import android.provider.BaseColumns;

import com.k2udacity.moviestage2.provider.MovieProvider;
import com.k2udacity.moviestage2.provider.movie.MovieColumns;


public class ReviewColumns implements BaseColumns {
    public static final String TABLE_NAME = "review";
    public static final Uri CONTENT_URI = Uri.parse(MovieProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = BaseColumns._ID;
    public static final String REVIEW_ID = "review_id";
    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";
    public static final String URL = "url";
    public static final String MOVIE_ID = "review__movie_id";

    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;


    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            REVIEW_ID,
            AUTHOR,
            CONTENT,
            URL,
            MOVIE_ID
    };


    public static final String PREFIX_MOVIE = TABLE_NAME + "__" + MovieColumns.TABLE_NAME;
}
