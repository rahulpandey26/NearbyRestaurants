package com.example.nearbyrestaurants.restaurant.model;

import com.google.gson.annotations.SerializedName;


public class AltId {

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("scope")
    private String scope;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
