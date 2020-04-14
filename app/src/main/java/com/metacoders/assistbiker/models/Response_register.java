package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response_register {

    @Expose
    @SerializedName("data")  private  String  data  ;
    @Expose
    @SerializedName("msg")  private  String  msg  ;

    public Response_register() {
    }

    public Response_register(String data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
