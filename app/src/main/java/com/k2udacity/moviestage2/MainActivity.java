package com.k2udacity.moviestage2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.k2udacity.moviestage2.apis.model.MovieApiModel;
import com.k2udacity.moviestage2.utils.Constant;


public class MainActivity extends AppCompatActivity
        implements MovieListFragment.Callbacks {
    public static float ITEM_WIDTH;
    private boolean isForTablet;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        //bigger screen
        if (findViewById(R.id.movie_detail_container) != null) {
            isForTablet = true;
        }

        ITEM_WIDTH = ((isForTablet) ? Constant.TABLET_COL_WIDTH : Constant.PHONE_COL_WIDTH)
                * getResources().getDisplayMetrics().density;
    }

    @Override
    public void onItemSelected(MovieApiModel movie) {
        if (isForTablet) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(MovieDetailFragment.MOVIE_INTENT_KEY, movie);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, MovieDetailActivity.class);
            detailIntent.putExtra(MovieDetailFragment.MOVIE_INTENT_KEY, movie);
            startActivity(detailIntent);
        }
    }
}
