package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sent_Response_register {

    //customer_email, customer_pass, customer_contact, customer_contact2, customer_image, customer_name, customer_address
    @Expose
    @SerializedName("customer_id")
    private String customer_id;
    @Expose
    @SerializedName("customer_email")
    private String customer_email;
    @Expose
    @SerializedName("customer_pass")
    private String customer_pass;
    @Expose
    @SerializedName("customer_contact")
    private String customer_contact;
    @Expose
    @SerializedName("customer_contact2")
    private String customer_contact2;
    @Expose
    @SerializedName("customer_image")
    private String customer_image;
    @Expose
    @SerializedName("customer_name")
    private String customer_name;
    @Expose
    @SerializedName("customer_address")
    private String customer_address;

    public Sent_Response_register() {
    }

    public Sent_Response_register(String customer_email, String customer_pass, String customer_contact, String customer_contact2, String customer_image, String customer_name, String customer_address) {
        this.customer_email = customer_email;
        this.customer_pass = customer_pass;
        this.customer_contact = customer_contact;
        this.customer_contact2 = customer_contact2;
        this.customer_image = customer_image;
        this.customer_name = customer_name;
        this.customer_address = customer_address;
    }

    public Sent_Response_register(String customer_id, String customer_email, String customer_pass, String customer_contact, String customer_contact2, String customer_image, String customer_name, String customer_address) {
        this.customer_id = customer_id;
        this.customer_email = customer_email;
        this.customer_pass = customer_pass;
        this.customer_contact = customer_contact;
        this.customer_contact2 = customer_contact2;
        this.customer_image = customer_image;
        this.customer_name = customer_name;
        this.customer_address = customer_address;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_pass() {
        return customer_pass;
    }

    public void setCustomer_pass(String customer_pass) {
        this.customer_pass = customer_pass;
    }

    public String getCustomer_contact() {
        return customer_contact;
    }

    public void setCustomer_contact(String customer_contact) {
        this.customer_contact = customer_contact;
    }

    public String getCustomer_contact2() {
        return customer_contact2;
    }

    public void setCustomer_contact2(String customer_contact2) {
        this.customer_contact2 = customer_contact2;
    }

    public String getCustomer_image() {
        return customer_image;
    }

    public void setCustomer_image(String customer_image) {
        this.customer_image = customer_image;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
