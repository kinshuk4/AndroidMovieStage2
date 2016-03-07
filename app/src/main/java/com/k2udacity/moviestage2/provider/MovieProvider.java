package com.k2udacity.moviestage2.provider;

import java.util.Arrays;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.k2udacity.moviestage2.provider.movie.MovieColumns;
import com.k2udacity.moviestage2.provider.review.ReviewColumns;
import com.k2udacity.moviestage2.provider.video.VideoColumns;
import com.k2udacity.moviestage2.utils.ErrorCode;


public class MovieProvider extends ContentProvider {
    private static final String TAG = MovieProvider.class.getSimpleName();


    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.k2udacity.moviestage2.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_MOVIE = 0;
    private static final int URI_TYPE_MOVIE_ID = 1;

    private static final int URI_TYPE_REVIEW = 2;
    private static final int URI_TYPE_REVIEW_ID = 3;

    private static final int URI_TYPE_VIDEO = 4;
    private static final int URI_TYPE_VIDEO_ID = 5;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, MovieColumns.TABLE_NAME, URI_TYPE_MOVIE);
        URI_MATCHER.addURI(AUTHORITY, MovieColumns.TABLE_NAME + "/#", URI_TYPE_MOVIE_ID);
        URI_MATCHER.addURI(AUTHORITY, ReviewColumns.TABLE_NAME, URI_TYPE_REVIEW);
        URI_MATCHER.addURI(AUTHORITY, ReviewColumns.TABLE_NAME + "/#", URI_TYPE_REVIEW_ID);
        URI_MATCHER.addURI(AUTHORITY, VideoColumns.TABLE_NAME, URI_TYPE_VIDEO);
        URI_MATCHER.addURI(AUTHORITY, VideoColumns.TABLE_NAME + "/#", URI_TYPE_VIDEO_ID);
    }

    public static final String QUERY_NOTIFY = "QUERY_NOTIFY";
    public static final String QUERY_GROUP_BY = "QUERY_GROUP_BY";
    public static final String QUERY_HAVING = "QUERY_HAVING";
    public static final String QUERY_LIMIT = "QUERY_LIMIT";

    public static class QueryParams {
        public String table;
        public String tablesWithJoins;
        public String idColumn;
        public String selection;
        public String orderBy;
    }


    protected SQLiteOpenHelper mSqLiteOpenHelper;


    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_MOVIE:
                return TYPE_CURSOR_DIR + MovieColumns.TABLE_NAME;//dir
            case URI_TYPE_MOVIE_ID:
                return TYPE_CURSOR_ITEM + MovieColumns.TABLE_NAME;//item

            case URI_TYPE_REVIEW:
                return TYPE_CURSOR_DIR + ReviewColumns.TABLE_NAME;
            case URI_TYPE_REVIEW_ID:
                return TYPE_CURSOR_ITEM + ReviewColumns.TABLE_NAME;

            case URI_TYPE_VIDEO:
                return TYPE_CURSOR_DIR + VideoColumns.TABLE_NAME;
            case URI_TYPE_VIDEO_ID:
                return TYPE_CURSOR_ITEM + VideoColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = uri.getLastPathSegment();
        long rowId = mSqLiteOpenHelper.getWritableDatabase().insertOrThrow(table, null, values);
        if (rowId == -1) return null;
        String notify;
        if (((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri.buildUpon().appendEncodedPath(String.valueOf(rowId)).build();
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        String table = uri.getLastPathSegment();
        SQLiteDatabase db = mSqLiteOpenHelper.getWritableDatabase();
        int res = 0;
        db.beginTransaction();
        try {
            for (ContentValues v : values) {
                long id = db.insert(table, null, v);
                db.yieldIfContendedSafely();
                if (id != -1) {
                    res++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return res;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        QueryParams queryParams = getQueryParams(uri, selection, null);
        int res = mSqLiteOpenHelper.getWritableDatabase().update(queryParams.table, values, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        QueryParams queryParams = getQueryParams(uri, selection, null);
        int res = mSqLiteOpenHelper.getWritableDatabase().delete(queryParams.table, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public boolean onCreate() {
        mSqLiteOpenHelper = SQLiteDBHelper.getInstance(getContext());

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String groupBy = uri.getQueryParameter(QUERY_GROUP_BY);
        String having = uri.getQueryParameter(QUERY_HAVING);
        String limit = uri.getQueryParameter(QUERY_LIMIT);
        QueryParams queryParams = getQueryParams(uri, selection, projection);
        projection = ensureIdIsFullyQualified(projection, queryParams.table, queryParams.idColumn);
        Cursor res = mSqLiteOpenHelper.getReadableDatabase().query(queryParams.tablesWithJoins, projection, queryParams.selection, selectionArgs, groupBy,
                having, sortOrder == null ? queryParams.orderBy : sortOrder, limit);
        res.setNotificationUri(getContext().getContentResolver(), uri);
        return res;
    }

    private String[] ensureIdIsFullyQualified(String[] projection, String tableName, String idColumn) {
        if (projection == null) return null;
        String[] res = new String[projection.length];
        for (int i = 0; i < projection.length; i++) {
            if (projection[i].equals(idColumn)) {
                res[i] = tableName + "." + idColumn + " AS " + BaseColumns._ID;
            } else {
                res[i] = projection[i];
            }
        }
        return res;
    }

    public static Uri notify(Uri uri, boolean notify) {
        return uri.buildUpon().appendQueryParameter(QUERY_NOTIFY, String.valueOf(notify)).build();
    }

    public static Uri groupBy(Uri uri, String groupBy) {
        return uri.buildUpon().appendQueryParameter(QUERY_GROUP_BY, groupBy).build();
    }

    public static Uri having(Uri uri, String having) {
        return uri.buildUpon().appendQueryParameter(QUERY_HAVING, having).build();
    }

    public static Uri limit(Uri uri, String limit) {
        return uri.buildUpon().appendQueryParameter(QUERY_LIMIT, limit).build();
    }

    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_MOVIE:
            case URI_TYPE_MOVIE_ID:
                res.table = MovieColumns.TABLE_NAME;
                res.idColumn = MovieColumns._ID;
                res.tablesWithJoins = MovieColumns.TABLE_NAME;
                res.orderBy = MovieColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_REVIEW:
            case URI_TYPE_REVIEW_ID:
                res.table = ReviewColumns.TABLE_NAME;
                res.idColumn = ReviewColumns._ID;
                res.tablesWithJoins = ReviewColumns.TABLE_NAME;
                if (MovieColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + MovieColumns.TABLE_NAME + " AS " + ReviewColumns.PREFIX_MOVIE + " ON " + ReviewColumns.TABLE_NAME + "." + ReviewColumns.MOVIE_ID + "=" + ReviewColumns.PREFIX_MOVIE + "." + MovieColumns._ID;
                }
                res.orderBy = ReviewColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_VIDEO:
            case URI_TYPE_VIDEO_ID:
                res.table = VideoColumns.TABLE_NAME;
                res.idColumn = VideoColumns._ID;
                res.tablesWithJoins = VideoColumns.TABLE_NAME;
                if (MovieColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + MovieColumns.TABLE_NAME + " AS " + VideoColumns.PREFIX_MOVIE + " ON " + VideoColumns.TABLE_NAME + "." + VideoColumns.MOVIE_ID + "=" + VideoColumns.PREFIX_MOVIE + "." + MovieColumns._ID;
                }
                res.orderBy = VideoColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException(ErrorCode.URI_NOT_SUPPORTED.toString()+uri);
        }

        switch (matchedId) {
            case URI_TYPE_MOVIE_ID:
            case URI_TYPE_REVIEW_ID:
            case URI_TYPE_VIDEO_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
