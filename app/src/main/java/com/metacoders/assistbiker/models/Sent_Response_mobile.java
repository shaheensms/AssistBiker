package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sent_Response_mobile {


    @Expose
    @SerializedName("customer_contact")  private  String customer_contact    ;

    public Sent_Response_mobile() {
    }

    public Sent_Response_mobile(String customer_contact) {
        this.customer_contact = customer_contact;
    }

    public String getCustomer_contact() {
        return customer_contact;
    }

    public void setCustomer_contact(String customer_contact) {
        this.customer_contact = customer_contact;
    }
}
