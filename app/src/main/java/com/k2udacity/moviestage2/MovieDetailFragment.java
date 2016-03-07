package com.k2udacity.moviestage2;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.k2udacity.moviestage2.apis.RetrofitHelper;
import com.k2udacity.moviestage2.apis.model.BaseApiModel;
import com.k2udacity.moviestage2.apis.model.MovieApiModel;
import com.k2udacity.moviestage2.apis.model.ReviewApiModel;
import com.k2udacity.moviestage2.apis.model.VideoApiModel;
import com.k2udacity.moviestage2.provider.movie.MovieContentValues;
import com.k2udacity.moviestage2.provider.movie.MovieSelection;
import com.k2udacity.moviestage2.provider.review.ReviewContentValues;
import com.k2udacity.moviestage2.provider.review.ReviewCursor;
import com.k2udacity.moviestage2.provider.review.ReviewSelection;
import com.k2udacity.moviestage2.provider.video.VideoContentValues;
import com.k2udacity.moviestage2.provider.video.VideoCursor;
import com.k2udacity.moviestage2.provider.video.VideoSelection;
import com.k2udacity.moviestage2.utils.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.k2udacity.moviestage2.provider.movie.MovieCursor;
import com.k2udacity.moviestage2.utils.Constant;
import retrofit.Callback;
import retrofit.Response;


public class MovieDetailFragment extends Fragment {

    public static final String MOVIE_INTENT_KEY = "movie";

    @Bind(R.id.tv_title)    TextView mTvTitle;
    @Bind(R.id.tv_release_yr)    TextView mTvReleaseYr;
    @Bind(R.id.tv_overview)    TextView mTvOverview;
    @Bind(R.id.tv_vote_avg)    TextView mTvVoteAvg;
    @Bind(R.id.iv_movie_poster)    ImageView mIvMoviePoster;
    @Bind(R.id.linear_videos)    LinearLayout mLinearVideos;
    @Bind(R.id.linear_reviews)    LinearLayout mLinearReviews;
    @Bind(R.id.tv_video_label)    TextView mTvVideoLabel;
    @Bind(R.id.tv_review_label)    TextView mTvReviewLabel;
    @Bind(R.id.btn_movie_fav)    Button mBtnAddFavMovie;

    Toolbar mToolbar;

    private MovieApiModel mMovie;
    private List<VideoApiModel> mVideo = new ArrayList<>();
    private List<ReviewApiModel> mReview = new ArrayList<>();
    private String mFirstVideoUrl;

    Activity mActivity ;

    private boolean isFavorited = false;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments().containsKey(MOVIE_INTENT_KEY)) {
            mMovie = getArguments().getParcelable(MOVIE_INTENT_KEY);

            Activity activity = this.getActivity();
            mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);

            //Set the detail title
            if (mToolbar != null) {
                Activity parentActivity = getActivity();
                if(parentActivity instanceof MovieDetailActivity)
                        ((MovieDetailActivity) getActivity()).getSupportActionBar().setTitle("MovieDetail");
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, mMovie.getTitle());
            share.putExtra(Intent.EXTRA_TEXT, mMovie.getTitle() + ":");
            if (!TextUtils.isEmpty(mFirstVideoUrl)) {
                share.putExtra(Intent.EXTRA_TEXT, mFirstVideoUrl);
                startActivity(Intent.createChooser(share, "Share video!"));
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        ButterKnife.bind(this, rootView);

        if (mMovie != null) {
            initLayout();

            callApis(mMovie.getId());
        }

        //check if favorite
        MovieSelection movieSelection = new MovieSelection();
        movieSelection.movieId(mMovie.getId());
        MovieCursor c = movieSelection.query(getActivity().getContentResolver());
        toggleButtonFav(c.getCount() > 0);

        return rootView;
    }

    private void callApis(long id) {
        getReviewForMovie(id);
        getVideoForMovie(id);
    }

    private void getReviewForMovie(long id) {
        ReviewSelection reviewSelection = new ReviewSelection();
        reviewSelection.movieMovieId(mMovie.getId());
        ReviewCursor c = reviewSelection.query(getActivity().getContentResolver());
        if (c.getCount() > 0) {
            List<ReviewApiModel> reviews = new ArrayList<>();
            while (c.moveToNext()) {
                reviews.add(new ReviewApiModel(c));
            }
            initReviewsLayout(reviews);
            c.close();
        } else {
            RetrofitHelper.getInstance().getService().getMovieReviews(id, getResources().getString(R.string.movie_db_api_key))
                    .enqueue(new Callback<BaseApiModel<ReviewApiModel>>() {
                                 @Override
                                 public void onResponse(Response<BaseApiModel<ReviewApiModel>> response) {
                                     if (response.body() != null) {
                                         mReview.addAll(response.body().getResults());
                                         if (mReview.size() > 0) {
                                             initReviewsLayout(mReview);
                                         } else {
                                             mTvReviewLabel.setText(R.string.detail_no_reviews);
                                         }
                                     }
                                 }

                                 @Override
                                 public void onFailure(Throwable t) {

                                 }
                             }

                    );
        }
    }

    private void getVideoForMovie(long id) {
        VideoSelection videoSelection = new VideoSelection();
        videoSelection.movieMovieId(mMovie.getId());
        VideoCursor videoCursor = videoSelection.query(getActivity().getContentResolver());
        if (videoCursor.getCount() > 0) {
            List<VideoApiModel> videos = new ArrayList<>();
            while (videoCursor.moveToNext()) {
                videos.add(new VideoApiModel(videoCursor));
            }
            initVideosLayout(videos);
            videoCursor.close();
        } else {
            RetrofitHelper.getInstance()
                    .getService()
                    .getMovieVideos(id, getResources().getString(R.string.movie_db_api_key))
                    .enqueue(new Callback<BaseApiModel<VideoApiModel>>() {
                                 @Override
                                 public void onResponse(Response<BaseApiModel<VideoApiModel>> response) {
                                     if (response.body() != null) {
                                         mVideo.addAll(response.body().getResults());
                                         if (mVideo.size() > 0) {
                                             mFirstVideoUrl = getYoutubeUrl(mVideo.get(0).getKey());
                                             initVideosLayout(mVideo);
                                         } else {
                                             mTvVideoLabel.setText(R.string.detail_no_videos);
                                         }
                                     }
                                 }
                                 @Override
                                 public void onFailure(Throwable t) {

                                 }
                             }

                    );
        }
    }

    private String getYoutubeUrl(String key) {
        return Constant.YOUTUBE_BASE_URL + key;
    }

    private void initLayout() {
        mTvTitle.setText(mMovie.getTitle());

        String formattedDate = DateUtil.convertDateFormat(mMovie.getRelease_date(),Constant.MOVIEDB_DATE_FORMAT,Constant.UI_DATE_FORMAT);
        mTvReleaseYr.setText(formattedDate);

        mTvVoteAvg.setText(mMovie.getVote_average() + Constant.RATING_TOTAL);
        if (!TextUtils.isEmpty(mMovie.getOverview())) {
            mTvOverview.setText(Html.fromHtml(mMovie.getOverview()));
        } else {
            mTvOverview.setText("No description");
        }

        Picasso.with(getActivity()).load(Constant.ROOT_POSTER_IMAGE_URL + mMovie.getPoster_path())
                .error(R.color.grey).placeholder(R.color.grey).into(mIvMoviePoster);
    }

    private void initVideosLayout(List<VideoApiModel> videos) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        for (int i = 0; i < videos.size(); i++) {
            final VideoApiModel video = videos.get(i);

            View v = inflater.inflate(R.layout.item_video, mLinearVideos, false);
            ImageView ivThumb = (ImageView) v.findViewById(R.id.iv_video_thumbnail);
            TextView tvTitle = (TextView) v.findViewById(R.id.tv_video_title);

            tvTitle.setText(video.getName());
            Picasso.with(getActivity()).load( Constant.YOUTUBE_IMG_URL+ video.getKey() + "/0.jpg")
                    .error(R.color.colorPrimary).placeholder(R.color.grey).into(ivThumb);

            //set intent for opening youtube link
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getYoutubeUrl(video.getKey())));
                    startActivity(intent);
                }
            });
            mLinearVideos.addView(v);
        }
    }

    private void initReviewsLayout(List<ReviewApiModel> reviews) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);


        for (int i = 0; i < reviews.size(); i++) {
            ReviewApiModel review = reviews.get(i);

            View v = inflater.inflate(R.layout.item_review, mLinearReviews, false);
            TextView tvContent = (TextView) v.findViewById(R.id.tv_review_content);
            TextView tvAuthor = (TextView) v.findViewById(R.id.tv_review_author);

            tvContent.setText("\"" + review.getContent() + "\"");
            tvAuthor.setText(review.getAuthor());

            mLinearReviews.addView(v);
        }
    }

    @OnClick(R.id.btn_movie_fav)
    public void onClickAddFav(View v) {
        if (isFavorited) {
            MovieSelection movieSelection = new MovieSelection();
            movieSelection.movieId(mMovie.getId());
            movieSelection.delete(getActivity().getContentResolver());

            VideoSelection videoSelection = new VideoSelection();
            videoSelection.movieId(mMovie.getId());
            videoSelection.delete(getActivity().getContentResolver());

            ReviewSelection reviewSelection = new ReviewSelection();
            reviewSelection.movieId(mMovie.getId());
            reviewSelection.delete(getActivity().getContentResolver());


            toggleButtonFav(false);
        } else {
            //Save Movie to DB
            saveMovieToDB();

            toggleButtonFav(true);
        }
    }

    public void saveMovieToDB(){
        MovieContentValues values = new MovieContentValues();
        values.putBackdropPath(mMovie.getBackdrop_path());
        values.putMovieId(mMovie.getId());
        values.putOverview(mMovie.getOverview());
        values.putPosterPath(mMovie.getPoster_path());
        values.putReleaseDate(mMovie.getRelease_date());
        values.putTitle(mMovie.getTitle());
        values.putVoteAverage(mMovie.getVote_average());

        Uri uri = values.insertCV(getActivity().getContentResolver());
        saveVideo(uri);
        saveReview(uri);
    }

    public void saveVideo(Uri uri){
        for (int i = 0; i < mVideo.size(); i++) {
            VideoApiModel video = mVideo.get(i);
            VideoContentValues cv = new VideoContentValues();
            cv.putKey(video.getKey());
            cv.putVideoId(video.getId());
            cv.putName(video.getName());
            cv.putSize(video.getSize());
            cv.putType(video.getType());
            cv.putMovieId(ContentUris.parseId(uri));
            cv.insertCV(getActivity().getContentResolver());
        }
    }

    public void saveReview(Uri uri){
        for (int i = 0; i < mReview.size(); i++) {
            ReviewApiModel review = mReview.get(i);
            ReviewContentValues cv = new ReviewContentValues();
            cv.putMovieId(ContentUris.parseId(uri));
            cv.putAuthor(review.getAuthor());
            cv.putContent(review.getContent());
            cv.putReviewId(review.getId());
            cv.insertCV(getActivity().getContentResolver());
        }
    }
    private void toggleButtonFav(boolean setToAdded) {
        if (setToAdded) {
            isFavorited = true;
            mBtnAddFavMovie.setText(R.string.main_remove_from_fav);
        } else {
            isFavorited = false;
            mBtnAddFavMovie.setText(R.string.main_add_to_fav);
        }

    }

    //Handle the case to avoid null activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

}
