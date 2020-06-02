package com.metacoders.assistbiker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class zoneResponse {
    @Expose
    @SerializedName("zone_id")
    private int zone_id;
    @Expose
    @SerializedName("zone_name")
    private String zone_name;
    @Expose
    @SerializedName("zone_charge")
    private float zone_charge;

    public zoneResponse(int zone_id, String zone_name, float zone_charge) {
        this.zone_id = zone_id;
        this.zone_name = zone_name;
        this.zone_charge = zone_charge;
    }

    public zoneResponse() {
    }

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public float getZone_charge() {
        return zone_charge;
    }

    public void setZone_charge(float zone_charge) {
        this.zone_charge = zone_charge;
    }
}
