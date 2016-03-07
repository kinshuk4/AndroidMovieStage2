package com.k2udacity.moviestage2.apis.model;

import com.k2udacity.moviestage2.provider.video.VideoCursor;

public class VideoApiModel {
    private String id;
    private String iso_639_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public VideoApiModel(VideoCursor c) {
        id = c.getVideoId();
        key = c.getKey();
        name = c.getName();
        size = c.getSize();
        type = c.getType();
    }

    public String getId() {
        return id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
