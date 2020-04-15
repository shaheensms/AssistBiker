package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsFeedModel {

    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("is_product")
    private Boolean is_product;
    @Expose
    @SerializedName("product_id")
    private Integer product_id;
    @Expose
    @SerializedName("newsfeed_id")
    private Integer newsfeed_id;
    @Expose
    @SerializedName("product_price")
    private Float product_price;

    public NewsFeedModel() {
    }

    public NewsFeedModel(String title, String image, String description, Boolean is_product, Integer product_id, Integer newsfeed_id, Float product_price) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.is_product = is_product;
        this.product_id = product_id;
        this.newsfeed_id = newsfeed_id;
        this.product_price = product_price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_product() {
        return is_product;
    }

    public void setIs_product(Boolean is_product) {
        this.is_product = is_product;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getNewsfeed_id() {
        return newsfeed_id;
    }

    public void setNewsfeed_id(Integer newsfeed_id) {
        this.newsfeed_id = newsfeed_id;
    }

    public Float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Float product_price) {
        this.product_price = product_price;
    }


}
