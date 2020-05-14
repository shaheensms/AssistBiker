package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductsModel  implements Serializable {

    @Expose
    @SerializedName("product_id")
    private Integer product_id;
    @Expose
    @SerializedName("p_cat_id")
    private Integer p_cat_id;
    @Expose
    @SerializedName("manufacturer_id")
    private Integer manufacturer_id;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("product_title")
    private String product_title;
    @Expose
    @SerializedName("product_img1")
    private String product_img1;
    @Expose
    @SerializedName("product_img2")
    private String product_img2;
    @Expose
    @SerializedName("product_img3")
    private String product_img3;
    @Expose
    @SerializedName("product_price")
    private Float product_price;
    @Expose
    @SerializedName("product_desc")
    private String product_desc;
    @Expose
    @SerializedName("product_keywords")
    private String product_keywords;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("product_discount")
    private String product_discount;

    public ProductsModel() {
    }

    public ProductsModel(Integer product_id, Integer p_cat_id, Integer manufacturer_id, String date, String product_title, String product_img1, String product_img2, String product_img3, Float product_price, String product_desc, String product_keywords, String status, String product_discount) {
        this.product_id = product_id;
        this.p_cat_id = p_cat_id;
        this.manufacturer_id = manufacturer_id;
        this.date = date;
        this.product_title = product_title;
        this.product_img1 = product_img1;
        this.product_img2 = product_img2;
        this.product_img3 = product_img3;
        this.product_price = product_price;
        this.product_desc = product_desc;
        this.product_keywords = product_keywords;
        this.status = status;
        this.product_discount = product_discount;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getP_cat_id() {
        return p_cat_id;
    }

    public void setP_cat_id(Integer p_cat_id) {
        this.p_cat_id = p_cat_id;
    }

    public Integer getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(Integer manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_img1() {
        return product_img1;
    }

    public void setProduct_img1(String product_img1) {
        this.product_img1 = product_img1;
    }

    public String getProduct_img2() {
        return product_img2;
    }

    public void setProduct_img2(String product_img2) {
        this.product_img2 = product_img2;
    }

    public String getProduct_img3() {
        return product_img3;
    }

    public void setProduct_img3(String product_img3) {
        this.product_img3 = product_img3;
    }

    public Float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Float product_price) {
        this.product_price = product_price;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_keywords() {
        return product_keywords;
    }

    public void setProduct_keywords(String product_keywords) {
        this.product_keywords = product_keywords;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }

    @Override
    public String toString() {
        return "ProductsModel{" +
                "product_id=" + product_id +
                ", p_cat_id=" + p_cat_id +
                ", manufacturer_id=" + manufacturer_id +
                ", date='" + date + '\'' +
                ", product_title='" + product_title + '\'' +
                ", product_img1='" + product_img1 + '\'' +
                ", product_img2='" + product_img2 + '\'' +
                ", product_img3='" + product_img3 + '\'' +
                ", product_price=" + product_price +
                ", product_desc='" + product_desc + '\'' +
                ", product_keywords='" + product_keywords + '\'' +
                ", status='" + status + '\'' +
                ", product_discount='" + product_discount + '\'' +
                '}';
    }
}

