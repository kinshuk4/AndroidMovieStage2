package com.k2udacity.moviestage2.apis;

import com.k2udacity.moviestage2.utils.Constant;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RetrofitHelper {
    private static class SingletonHelper{
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    private Retrofit mRetrofit;
    private IMovieService mService;

    private RetrofitHelper() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(IMovieService.class);
    }

    public static RetrofitHelper getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public IMovieService getService() {
        return mService;
    }

}
