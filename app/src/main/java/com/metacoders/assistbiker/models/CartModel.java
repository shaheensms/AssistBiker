package com.metacoders.assistbiker.models;

import java.io.Serializable;

public class CartModel implements Serializable  {
    String title ;
    Float price ;
    Integer quantity , id ;
    String product_image;


    public CartModel() {
    }

    public CartModel(String title, Float price, Integer quantity, Integer id) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
