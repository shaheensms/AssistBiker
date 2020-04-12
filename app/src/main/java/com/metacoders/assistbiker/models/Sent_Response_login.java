package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sent_Response_login {

    @Expose
    @SerializedName("customer_email")  private  String  customer_email  ;
    @Expose
    @SerializedName("customer_pass")  private  String  customer_pass  ;

    public Sent_Response_login() {
    }

    public Sent_Response_login(String customer_email, String customer_pass) {
        this.customer_email = customer_email;
        this.customer_pass = customer_pass;
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
}
