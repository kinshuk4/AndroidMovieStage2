package com.k2udacity.moviestage2.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.k2udacity.moviestage2.provider.movie.MovieColumns;
import com.k2udacity.moviestage2.provider.review.ReviewColumns;
import com.k2udacity.moviestage2.provider.video.VideoColumns;


public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String TAG = SQLiteDBHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDBHelper mSQLiteDBHelperInstance;
    private final Context mContext;

    public static final String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE IF NOT EXISTS "
            + MovieColumns.TABLE_NAME + " ( "
            + MovieColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MovieColumns.MOVIE_ID + " INTEGER, "
            + MovieColumns.BACKDROP_PATH + " TEXT, "
            + MovieColumns.OVERVIEW + " TEXT, "
            + MovieColumns.RELEASE_DATE + " TEXT, "
            + MovieColumns.POSTER_PATH + " TEXT, "
            + MovieColumns.TITLE + " TEXT, "
            + MovieColumns.VOTE_AVERAGE + " REAL "
            + ", CONSTRAINT unique_id UNIQUE (movie__movie_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_TABLE_REVIEW = "CREATE TABLE IF NOT EXISTS "
            + ReviewColumns.TABLE_NAME + " ( "
            + ReviewColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ReviewColumns.REVIEW_ID + " TEXT, "
            + ReviewColumns.AUTHOR + " TEXT, "
            + ReviewColumns.CONTENT + " TEXT, "
            + ReviewColumns.URL + " TEXT, "
            + ReviewColumns.MOVIE_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_movie_id FOREIGN KEY (" + ReviewColumns.MOVIE_ID + ") REFERENCES movie (_id) ON DELETE CASCADE"
            + ", CONSTRAINT unique_id UNIQUE (review_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_TABLE_VIDEO = "CREATE TABLE IF NOT EXISTS "
            + VideoColumns.TABLE_NAME + " ( "
            + VideoColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VideoColumns.VIDEO_ID + " TEXT, "
            + VideoColumns.NAME + " TEXT, "
            + VideoColumns.KEY + " TEXT, "
            + VideoColumns.SIZE + " INTEGER, "
            + VideoColumns.TYPE + " TEXT, "
            + VideoColumns.MOVIE_ID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_movie_id FOREIGN KEY (" + VideoColumns.MOVIE_ID + ") REFERENCES movie (_id) ON DELETE CASCADE"
            + ", CONSTRAINT unique_id UNIQUE (video_id) ON CONFLICT REPLACE"
            + " );";

    public static SQLiteDBHelper getInstance(Context context) {
        if (mSQLiteDBHelperInstance == null) {
            mSQLiteDBHelperInstance = newInstance(context.getApplicationContext());
        }
        return mSQLiteDBHelperInstance;
    }

    private static SQLiteDBHelper newInstance(Context context) {
        return new SQLiteDBHelper(context, new DefaultDatabaseErrorHandler());
    }


    private SQLiteDBHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating the tables");
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_REVIEW);
        db.execSQL(SQL_CREATE_TABLE_VIDEO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            enableForeignKeyConstraints(db);
        }
    }

    private void enableForeignKeyConstraints(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        } else {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }



}
