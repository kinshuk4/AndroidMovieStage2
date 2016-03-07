package com.k2udacity.moviestage2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.k2udacity.moviestage2.adapters.MovieListAdapter;
import com.k2udacity.moviestage2.apis.RetrofitHelper;
import com.k2udacity.moviestage2.apis.model.BaseApiModel;
import com.k2udacity.moviestage2.apis.model.MovieApiModel;
import com.k2udacity.moviestage2.provider.movie.MovieCursor;
import com.k2udacity.moviestage2.provider.movie.MovieSelection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.k2udacity.moviestage2.utils.Constant;
import com.k2udacity.moviestage2.utils.ErrorCode;

import retrofit.Callback;
import retrofit.Response;

public class MovieListFragment extends Fragment {

    private final static String TAG = MovieListFragment.class.getSimpleName();
    //Current Sort By Column
    private final static String SORT_BY_INSTANCE_KEY = "sortBy";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress_view)
    ProgressBar mProgressView;

    //Adapter
    private StaggeredGridLayoutManager mLayoutManager;
    private MovieListAdapter mAdapter;
    private List<MovieApiModel> mDataset = new ArrayList<>();

    private String[] mSortArr;
    private int mSort = 0;


    private Callbacks mCallbacks = mDummyCallbacks;


    public interface Callbacks {
        public void onItemSelected(MovieApiModel movie);
    }


    private static Callbacks mDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(MovieApiModel movie) {
        }
    };


    public MovieListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mSortArr = getResources().getStringArray(R.array.sortArray);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_sort);

        View view = MenuItemCompat.getActionView(item);
        if(view instanceof Spinner) {
            final Spinner spinner = (Spinner) view;

            updateSpinner(spinner);
        }else{
            Log.e(TAG, "Unable to find the spinner." + view.getClass().getSimpleName());
        }
    }

    public void updateSpinner(Spinner spinner){
        if(spinner==null)
            return;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sortArray, R.layout.layout_spinner);//android.R.layout.simple_spinner_item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(mSort);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) parent.getSelectedItem();
                int which = (int) parent.getSelectedItemId();
                if (mSort == which)
                    return;
                mSort = which;
                if (which == 2) {
                    //if favorites
                    getFavoriteMovies();
                } else {
                    getAllMovies();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the saved sort key
        if (savedInstanceState != null
                && savedInstanceState.containsKey(SORT_BY_INSTANCE_KEY)) {
            mSort = savedInstanceState.getInt(SORT_BY_INSTANCE_KEY);
        }

        mRecyclerView.setVisibility(View.GONE);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MovieListAdapter(mDataset, mCallbacks);
        mRecyclerView.setAdapter(mAdapter);


        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int viewWidth = mRecyclerView.getMeasuredWidth();
                        int newSpanCount = Math.max(2, (int) Math.floor(viewWidth / MainActivity.ITEM_WIDTH));
                        mLayoutManager.setSpanCount(newSpanCount);
                        mLayoutManager.requestLayout();
                    }
                });
            }
        });

        getAllMovies();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSort == 2) {
            getFavoriteMovies();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(ErrorCode.CALLBACK_NOT_IMPLEMENTED.toString());
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = mDummyCallbacks;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SORT_BY_INSTANCE_KEY, mSort);
    }

    private void getFavoriteMovies() {
        mDataset.clear();
        MovieSelection productSelection = new MovieSelection();
        MovieCursor c = productSelection.query(getActivity().getContentResolver());
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                mDataset.add(new MovieApiModel(c));
            }
            c.close();
            mAdapter.notifyDataSetChanged();
            mRecyclerView.setVisibility(View.VISIBLE);
            stopProgress();
        } else {
            mRecyclerView.setVisibility(View.GONE);
            stopWithError(getString(R.string.main_no_favorites));
        }

    }

    private void getAllMovies() {
        String sortMode = null;
        if (mSortArr[mSort].equals(getString(R.string.main_sort_most_popular))) {
            sortMode = Constant.SORT_POPULAR;
        } else if (mSortArr[mSort].equals(getString(R.string.main_sort_highest_rated))) {
            sortMode = Constant.SORT_HIGHEST_RATED;
        }
        startProgress();
        RetrofitHelper.getInstance().getService()
                .getAllMovies(getResources().getString(R.string.movie_db_api_key), sortMode)
                .enqueue(new Callback<BaseApiModel<MovieApiModel>>() {
                    @Override
                    public void onResponse(Response<BaseApiModel<MovieApiModel>> response) {
                        if (response.body() != null) {
                            mDataset.clear();
                            mDataset.addAll(response.body().getResults());
                            mAdapter.notifyDataSetChanged();

                            mRecyclerView.setVisibility(View.VISIBLE);
                            stopProgress();
                        } else {
                            try {
                                stopWithError(response.errorBody().string().toString());
                            } catch (IOException e) {
                                stopWithError(e.getMessage());
                                Log.e(TAG,e.getStackTrace().toString());

                            }

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        stopWithError(t.getMessage());
                        Log.e(TAG, t.toString());
                    }
                });
    }

    private void startProgress(){
        mProgressView.setVisibility(View.VISIBLE);
    }

    private void stopProgress(){
        mProgressView.setVisibility(View.GONE);
    }
    private void stopWithError(String message){
        mProgressView.setVisibility(View.GONE);
        Toast.makeText(this.getActivity(),message,Toast.LENGTH_LONG);
    }
}
