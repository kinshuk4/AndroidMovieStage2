package com.k2udacity.moviestage2.utils;

public enum ErrorCode {

    PRIMARY_KEY_NULL(0, "Primary Key cannot be Null."+Constant.CONTACT_DEV),
    DUPLICATE_FAV_MOVIE(1, "This Movie already favorited."),
    URI_NOT_SUPPORTED(2,"Following Uri is not supported : "),
    CALLBACK_NOT_IMPLEMENTED(3,"Activity must implement Fragment callback.")
    ;

    private final int code;
    private final String description;

    private ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
