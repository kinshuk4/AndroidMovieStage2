package com.k2udacity.moviestage2.provider.base;

import java.util.HashMap;

import android.database.Cursor;
import android.database.CursorWrapper;

public abstract class AbstractCursor extends CursorWrapper {
    //colName to colNum Map
    private final HashMap<String, Integer> mColumnIndexes;

    public AbstractCursor(Cursor cursor) {
        super(cursor);
        mColumnIndexes = new HashMap<String, Integer>();
    }

    public abstract long getId();

    protected int getColIndex(String colName) {
        Integer index = mColumnIndexes.get(colName);
        if (index == null) {
            index = getColumnIndexOrThrow(colName);
            mColumnIndexes.put(colName, index);
        }
        return index;
    }

    public String getString(String colName) {
        int index = getColIndex(colName);
        if (isNull(index))
            return null;
        return getString(index);
    }

    public Integer getInteger(String colName) {
        int index = getColIndex(colName);
        if (isNull(index)) return null;
        return getInt(index);
    }

    public Long getLong(String colName) {
        int index = getColIndex(colName);
        if (isNull(index))
            return null;
        return getLong(index);
    }


    public Double getDouble(String colName) {
        int index = getColIndex(colName);
        if (isNull(index))
            return null;
        return getDouble(index);
    }

}
