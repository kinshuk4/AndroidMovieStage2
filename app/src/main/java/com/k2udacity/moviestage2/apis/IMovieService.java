package com.k2udacity.moviestage2.apis;

import com.k2udacity.moviestage2.apis.model.BaseApiModel;
import com.k2udacity.moviestage2.apis.model.MovieApiModel;
import com.k2udacity.moviestage2.apis.model.ReviewApiModel;
import com.k2udacity.moviestage2.apis.model.VideoApiModel;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface IMovieService {
    //BASE_API_URL/discover/movie?api_key=apiKey&sort_by=sortBy
    @GET("discover/movie")
    Call<BaseApiModel<MovieApiModel>> getAllMovies(@Query("api_key") String apiKey, @Query("sort_by") String sortBy);

    //BASE_API_URL/movie/MovieId/reviews?api_key=apiKey
    @GET("movie/{movie_id}/reviews")
    Call<BaseApiModel<ReviewApiModel>> getMovieReviews(
            @Path("movie_id") long movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<BaseApiModel<VideoApiModel>> getMovieVideos(
            @Path("movie_id") long movieId, @Query("api_key") String apiKey);
}