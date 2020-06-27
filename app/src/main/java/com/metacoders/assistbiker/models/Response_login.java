package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response_login {
    @Expose
    @SerializedName("msg")  private  String  msg  ;
    @Expose
    @SerializedName("customer_name")  private  String  customer_name  ;
    @Expose
    @SerializedName("customer_image")  private  String  customer_image  ;
    @Expose
    @SerializedName("customer_id")  private  String  customer_id  ;
    @Expose
    @SerializedName("customer_address")  private  String  customer_address  ;


    public Response_login() {
    }

    public Response_login(String msg, String customer_name, String customer_image, String customer_id, String customer_address) {
        this.msg = msg;
        this.customer_name = customer_name;
        this.customer_image = customer_image;
        this.customer_id = customer_id;
        this.customer_address = customer_address;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_image() {
        return customer_image;
    }

    public void setCustomer_image(String customer_image) {
        this.customer_image = customer_image;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
