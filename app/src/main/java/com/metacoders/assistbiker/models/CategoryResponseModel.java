package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResponseModel {
    @Expose
    @SerializedName("p_cat_id")
    private String p_cat_id;
    @Expose
    @SerializedName("p_cat_title")
    private String p_cat_title;
    @Expose
    @SerializedName("p_cat_top")
    private String p_cat_top;
    @Expose
    @SerializedName("p_cat_image")
    private String p_cat_image;

    public CategoryResponseModel() {
    }

    public CategoryResponseModel(String p_cat_id, String p_cat_title, String p_cat_top, String p_cat_image) {
        this.p_cat_id = p_cat_id;
        this.p_cat_title = p_cat_title;
        this.p_cat_top = p_cat_top;
        this.p_cat_image = p_cat_image;
    }

    public String getP_cat_id() {
        return p_cat_id;
    }

    public void setP_cat_id(String p_cat_id) {
        this.p_cat_id = p_cat_id;
    }

    public String getP_cat_title() {
        return p_cat_title;
    }

    public void setP_cat_title(String p_cat_title) {
        this.p_cat_title = p_cat_title;
    }

    public String getP_cat_top() {
        return p_cat_top;
    }

    public void setP_cat_top(String p_cat_top) {
        this.p_cat_top = p_cat_top;
    }

    public String getP_cat_image() {
        return p_cat_image;
    }

    public void setP_cat_image(String p_cat_image) {
        this.p_cat_image = p_cat_image;
    }
}
