package com.k2udacity.moviestage2.apis.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.k2udacity.moviestage2.provider.movie.MovieCursor;

public class MovieApiModel implements Parcelable {
    private boolean adult;
    private String backdrop_path;
    private List<Integer> genre_ids;
    private long id;
    private String original_language;
    private String original_title;
    private String overview;
    private String release_date;
    private String poster_path;
    private double popularity;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;

    public MovieApiModel(MovieCursor movieCursor) {
        backdrop_path = movieCursor.getBackdropPath();
        id = movieCursor.getMovieId();
        overview = movieCursor.getOverview();
        release_date = movieCursor.getReleaseDate();
        poster_path = movieCursor.getPosterPath();
        title = movieCursor.getTitle();
        vote_average = movieCursor.getVoteAverage();
    }
    @SuppressWarnings("unused")
    public boolean isAdult() {
        return adult;
    }
    @SuppressWarnings("unused")
    public String getBackdrop_path() {
        return backdrop_path;
    }
    @SuppressWarnings("unused")
    public List<Integer> getGenre_ids() {
        return genre_ids;
    }
    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }
    @SuppressWarnings("unused")
    public String getOriginal_language() {
        return original_language;
    }
    @SuppressWarnings("unused")
    public String getOriginal_title() {
        return original_title;
    }
    @SuppressWarnings("unused")
    public String getOverview() {
        return overview;
    }
    @SuppressWarnings("unused")
    public String getRelease_date() {
        return release_date;
    }
    @SuppressWarnings("unused")
    public String getPoster_path() {
        return poster_path;
    }
    @SuppressWarnings("unused")
    public double getPopularity() {
        return popularity;
    }
    @SuppressWarnings("unused")
    public String getTitle() {
        return title;
    }
    @SuppressWarnings("unused")
    public boolean isVideo() {
        return video;
    }
    @SuppressWarnings("unused")
    public double getVote_average() {
        return vote_average;
    }

    @SuppressWarnings("unused")
    public int getVote_count() {
        return vote_count;
    }

    protected MovieApiModel(Parcel in) {
        adult = in.readByte() != 0x00;
        backdrop_path = in.readString();
        if (in.readByte() == 0x01) {
            genre_ids = new ArrayList<Integer>();
            in.readList(genre_ids, Integer.class.getClassLoader());
        } else {
            genre_ids = null;
        }
        id = in.readLong();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        popularity = in.readDouble();
        title = in.readString();
        video = in.readByte() != 0x00;
        vote_average = in.readDouble();
        vote_count = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 0x01 : 0x00));
        dest.writeString(backdrop_path);
        if (genre_ids == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genre_ids);
        }
        dest.writeLong(id);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeDouble(popularity);
        dest.writeString(title);
        dest.writeByte((byte) (video ? 0x01 : 0x00));
        dest.writeDouble(vote_average);
        dest.writeInt(vote_count);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieApiModel> CREATOR = new Parcelable.Creator<MovieApiModel>() {
        @Override
        public MovieApiModel createFromParcel(Parcel in) {
            return new MovieApiModel(in);
        }

        @Override
        public MovieApiModel[] newArray(int size) {
            return new MovieApiModel[size];
        }
    };
}