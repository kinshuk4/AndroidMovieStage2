package com.k2udacity.moviestage2.provider.movie;

import android.support.annotation.Nullable;

public interface IMovieCursorRepo {


    @Nullable
    Long getMovieId();

    @Nullable
    String getBackdropPath();

    @Nullable
    String getOverview();

    @Nullable
    String getReleaseDate();

    @Nullable
    String getPosterPath();

    @Nullable
    String getTitle();

    @Nullable
    Double getVoteAverage();
}
