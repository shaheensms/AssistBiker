package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseNewsFeed {
    @SerializedName("")
    @Expose
    private List<NewsFeedModel> newsFeedModelList = null;

    /**
     * No args constructor for use in serialization
     */
    public ResponseNewsFeed() {
    }

    /**
     * @param newsFeedModelList
     */
    public ResponseNewsFeed(List<NewsFeedModel> newsFeedModelList) {
        this.newsFeedModelList = newsFeedModelList;
    }

    public List<NewsFeedModel> getNewsFeedModelList() {
        return newsFeedModelList;
    }

    public void setNewsFeedModelList(List<NewsFeedModel> newsFeedModelList) {
        this.newsFeedModelList = newsFeedModelList;
    }
}

