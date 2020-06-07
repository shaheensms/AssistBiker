package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class Sent_Response_cart {

    @Expose
    @SerializedName("customer_id")  private  int  customer_id  ;
    @Expose
    @SerializedName("due_amount")  private  float  due_amount  ;
    @Expose
    @SerializedName("order_date")  private  String  order_date  ;
    @Expose
    @SerializedName("order_status")  private  String  order_status  ;
    @Expose
    @SerializedName("order_comment")  private  String  order_comment  ;
    @Expose
    @SerializedName("order_type")  private  String  order_type  ;
    @Expose
    @SerializedName("order_list")  private  String  order_list  ;
    @Expose
    @SerializedName("payment_type")  private  String  payment_type  ;
    @Expose
    @SerializedName("delivery_zone")  private  String  delivery_zone  ;
    @Expose
    @SerializedName("delivery_address")  private  String  delivery_address  ;
    @Expose
    @SerializedName("trans_id")  private  String  test  ;

    public Sent_Response_cart() {
    }

    public Sent_Response_cart(int customer_id, float due_amount, String order_date, String order_status, String order_comment, String order_type, String order_list, String payment_type, String delivery_zone, String delivery_address, String test) {
        this.customer_id = customer_id;
        this.due_amount = due_amount;
        this.order_date = order_date;
        this.order_status = order_status;
        this.order_comment = order_comment;
        this.order_type = order_type;
        this.order_list = order_list;
        this.payment_type = payment_type;
        this.delivery_zone = delivery_zone;
        this.delivery_address = delivery_address;
        this.test = test;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public float getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(float due_amount) {
        this.due_amount = due_amount;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_comment() {
        return order_comment;
    }

    public void setOrder_comment(String order_comment) {
        this.order_comment = order_comment;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_list() {
        return order_list;
    }

    public void setOrder_list(String order_list) {
        this.order_list = order_list;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getDelivery_zone() {
        return delivery_zone;
    }

    public void setDelivery_zone(String delivery_zone) {
        this.delivery_zone = delivery_zone;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
