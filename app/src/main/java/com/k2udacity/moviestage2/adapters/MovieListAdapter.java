package com.k2udacity.moviestage2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.k2udacity.moviestage2.apis.model.MovieApiModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.k2udacity.moviestage2.MovieListFragment;
import com.k2udacity.moviestage2.R;

import com.k2udacity.moviestage2.utils.Constant;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private List<MovieApiModel> mDataset = new ArrayList<>();
    private MovieListFragment.Callbacks mCallbacks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_movie_poster) ImageView mIvPoster;
        @Bind(R.id.tv_title)  TextView mTvTitle;
        public View mRoot;

        public ViewHolder(View v) {
            super(v);
            mRoot = v;
            ButterKnife.bind(this, v);
        }
    }

    public MovieListAdapter(List<MovieApiModel> myDataset, MovieListFragment.Callbacks callbacks) {
        mDataset = myDataset;
        mCallbacks = callbacks;
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MovieApiModel item = mDataset.get(position);

        final Context context = holder.mRoot.getContext();

        holder.mTvTitle.setText(item.getTitle());

        String imagePath = Constant.ROOT_POSTER_IMAGE_URL + item.getPoster_path();

        Picasso.with(context)
                .load(imagePath)
                .placeholder(R.color.grey)
                .error(android.R.color.transparent)
                .into(holder.mIvPoster);


        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallbacks != null)
                    mCallbacks.onItemSelected(item);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}