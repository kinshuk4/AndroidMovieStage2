package com.k2udacity.moviestage2.provider.base;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

public abstract class AbstractContentValuesWrapper {
    protected final ContentValues mContentValues = new ContentValues();

    public abstract Uri getUri();

    public ContentValues values() {
        return mContentValues;
    }

    public Uri insertCV(ContentResolver contentResolver) {
        return contentResolver.insert(getUri(), values());
    }
}