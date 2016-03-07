package com.k2udacity.moviestage2.provider.video;

import android.support.annotation.Nullable;

public interface IVideoCursorRepo {

    @Nullable
    String getVideoId();

    @Nullable
    String getName();


    @Nullable
    String getKey();


    @Nullable
    Integer getSize();


    @Nullable
    String getType();


    long getMovieId();
}
