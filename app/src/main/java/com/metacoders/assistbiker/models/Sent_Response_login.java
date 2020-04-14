package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sent_Response_login {

    @Expose
    @SerializedName("customer_contact")  private  String  customer_contact  ;
    @Expose
    @SerializedName("customer_pass")  private  String  customer_pass  ;

    public Sent_Response_login() {
    }

    public Sent_Response_login(String customer_contact, String customer_pass) {
        this.customer_contact = customer_contact;
        this.customer_pass = customer_pass;
    }

    public String getCustomer_email() {
        return customer_contact;
    }

    public void setCustomer_email(String customer_contact) {
        this.customer_contact = customer_contact;
    }

    public String getCustomer_pass() {
        return customer_pass;
    }

    public void setCustomer_pass(String customer_pass) {
        this.customer_pass = customer_pass;
    }
}
