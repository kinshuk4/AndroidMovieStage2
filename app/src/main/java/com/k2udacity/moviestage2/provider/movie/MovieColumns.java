package com.k2udacity.moviestage2.provider.movie;

import android.net.Uri;
import android.provider.BaseColumns;

import com.k2udacity.moviestage2.provider.MovieProvider;


public class MovieColumns implements BaseColumns {
    public static final String TABLE_NAME = "movie";
    public static final Uri CONTENT_URI = Uri.parse(MovieProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = BaseColumns._ID;
    public static final String MOVIE_ID = "movie__movie_id";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSTER_PATH = "poster_path";
    public static final String TITLE = "title";
    public static final String VOTE_AVERAGE = "vote_average";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MOVIE_ID,
            BACKDROP_PATH,
            OVERVIEW,
            RELEASE_DATE,
            POSTER_PATH,
            TITLE,
            VOTE_AVERAGE
    };


    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MOVIE_ID) || c.contains("." + MOVIE_ID)) return true;
            if (c.equals(BACKDROP_PATH) || c.contains("." + BACKDROP_PATH)) return true;
            if (c.equals(OVERVIEW) || c.contains("." + OVERVIEW)) return true;
            if (c.equals(RELEASE_DATE) || c.contains("." + RELEASE_DATE)) return true;
            if (c.equals(POSTER_PATH) || c.contains("." + POSTER_PATH)) return true;
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(VOTE_AVERAGE) || c.contains("." + VOTE_AVERAGE)) return true;
        }
        return false;
    }

}
