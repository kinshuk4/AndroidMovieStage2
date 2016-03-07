package com.k2udacity.moviestage2.provider.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.k2udacity.moviestage2.provider.MovieProvider;

public abstract class AbstractSelection<T extends AbstractSelection<?>> {
    private static final String EQ = "=?";
    private static final String PAREN_OPEN = "(";
    private static final String PAREN_CLOSE = ")";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String IS_NULL = " IS NULL";
    private static final String IN = " IN (";
    private static final String COMMA = ",";
    private static final String DESC = " DESC";

    private final StringBuilder mSelection = new StringBuilder();
    private final List<String> mSelectionArgs = new ArrayList<String>(5);

    private final StringBuilder mOrderBy = new StringBuilder();

    Boolean mNotify;
    String mGroupBy;
    String mHaving;
    Integer mLimit;

    protected void addEquals(String column, Object[] value) {
        mSelection.append(column);
        // Null Array = Single Null Value OR Null Single Value = Null
        if (value == null || (value.length==1 && value[0]==null)) {
            mSelection.append(IS_NULL);
        } else if (value.length > 1) {
            // Multiple Values = Use 'in' clause)
            mSelection.append(IN);
            for (int i = 0; i < value.length; i++) {
                mSelection.append("?");
                if (i < value.length - 1) {
                    mSelection.append(COMMA);
                }
                mSelectionArgs.add(valueOf(value[i]));
            }
            mSelection.append(PAREN_CLOSE);
        } else {
                // Single non null Value = 'equals'
                mSelection.append(EQ);
                mSelectionArgs.add(valueOf(value[0]));

        }
    }

    protected Cursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        return cursor;
    }

    protected Cursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }
    protected Cursor query(Context context, String[] projection){
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        return cursor;
    }

    protected Cursor query(Context context){
        return query(context,null);
    }

    private String valueOf(Object obj) {
        if (obj instanceof Date) {
            return String.valueOf(((Date) obj).getTime());
        } else if (obj instanceof Boolean) {
            return (Boolean) obj ? "1" : "0";
        } else if (obj instanceof Enum) {
            return String.valueOf(((Enum<?>) obj).ordinal());
        }
        return String.valueOf(obj);
    }

    @SuppressWarnings("unchecked")
    public T and() {
        mSelection.append(AND);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T or() {
        mSelection.append(OR);
        return (T) this;
    }



    protected Object[] toObjectArray(long... array) {
        Object[] res = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = array[i];
        }
        return res;
    }


    public String sel() {
        return mSelection.toString();
    }


    public String[] args() {
        int size = mSelectionArgs.size();
        if (size == 0) return null;
        return mSelectionArgs.toArray(new String[size]);
    }


    public String order() {
        return mOrderBy.length() > 0 ? mOrderBy.toString() : null;
    }


    public Uri uri() {
        Uri uri = baseUri();
        if (mNotify != null) uri = MovieProvider.notify(uri, mNotify);
        if (mGroupBy != null) uri = MovieProvider.groupBy(uri, mGroupBy);
        if (mHaving != null) uri = MovieProvider.having(uri, mHaving);
        if (mLimit != null) uri = MovieProvider.limit(uri, String.valueOf(mLimit));
        return uri;
    }

    protected abstract Uri baseUri();


    public int delete(ContentResolver contentResolver) {
        return contentResolver.delete(uri(), sel(), args());
    }



    @SuppressWarnings("unchecked")
    public T orderBy(String order, boolean desc) {
        if (mOrderBy.length() > 0) mOrderBy.append(COMMA);
        mOrderBy.append(order);
        if (desc) mOrderBy.append(DESC);
        return (T) this;
    }

    public T orderBy(String order) {
        return orderBy(order, false);
    }

    @SuppressWarnings("unchecked")
    public T orderBy(String... orders) {
        for (String order : orders) {
            orderBy(order, false);
        }
        return (T) this;
    }


}
