package com.k2udacity.moviestage2.provider.review;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.k2udacity.moviestage2.provider.base.AbstractContentValuesWrapper;


public class ReviewContentValues extends AbstractContentValuesWrapper {
    @Override
    public Uri getUri() {
        return ReviewColumns.CONTENT_URI;
    }


    public int update(ContentResolver contentResolver, @Nullable ReviewSelection where) {
        return contentResolver.update(getUri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }


    public int update(Context context, @Nullable ReviewSelection where) {
        return context.getContentResolver().update(getUri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ReviewContentValues putReviewId(@Nullable String value) {
        mContentValues.put(ReviewColumns.REVIEW_ID, value);
        return this;
    }

    public ReviewContentValues putReviewIdNull() {
        mContentValues.putNull(ReviewColumns.REVIEW_ID);
        return this;
    }

    public ReviewContentValues putAuthor(@Nullable String value) {
        mContentValues.put(ReviewColumns.AUTHOR, value);
        return this;
    }

    public ReviewContentValues putAuthorNull() {
        mContentValues.putNull(ReviewColumns.AUTHOR);
        return this;
    }

    public ReviewContentValues putContent(@Nullable String value) {
        mContentValues.put(ReviewColumns.CONTENT, value);
        return this;
    }

    public ReviewContentValues putContentNull() {
        mContentValues.putNull(ReviewColumns.CONTENT);
        return this;
    }

    public ReviewContentValues putUrl(@Nullable String value) {
        mContentValues.put(ReviewColumns.URL, value);
        return this;
    }

    public ReviewContentValues putUrlNull() {
        mContentValues.putNull(ReviewColumns.URL);
        return this;
    }

    public ReviewContentValues putMovieId(long value) {
        mContentValues.put(ReviewColumns.MOVIE_ID, value);
        return this;
    }

}
