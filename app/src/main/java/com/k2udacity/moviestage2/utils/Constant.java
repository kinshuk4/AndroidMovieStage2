package com.k2udacity.moviestage2.utils;

public class Constant {
    public final static String SORT_POPULAR = "popularity.desc";
    public final static String SORT_HIGHEST_RATED = "vote_average.desc";
    public final static String SORT_FAVORITES = "favorites";
    public final static String[] sortArray = {SORT_POPULAR,SORT_HIGHEST_RATED,SORT_FAVORITES};

    public final static String BASE_API_URL = "http://api.themoviedb.org/3/";
    public final static String ROOT_POSTER_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    public final static String ROOT_BACKDROP_IMAGE_URL = "http://image.tmdb.org/t/p/w500";


    public final static String RATING_TOTAL = "/10";

    public final static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    public final static String YOUTUBE_IMG_URL = "http://img.youtube.com/vi/";
    public final static String MOVIEDB_DATE_FORMAT = "yyyy-MM-dd";
    public final static String UI_DATE_FORMAT_COMPLETE = "dd MMM yyyy";
    public final static String UI_DATE_FORMAT = "yyyy";

    public final static int PHONE_COL_WIDTH = 180;
    public final static int TABLET_COL_WIDTH=80;

    public static final  String CONTACT_DEV = "Please contact the developer.";
}
