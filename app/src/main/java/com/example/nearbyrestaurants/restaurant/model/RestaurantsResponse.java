package com.example.nearbyrestaurants.restaurant.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RestaurantsResponse {

    @SerializedName("html_attributions")
    private List<Object> htmlAttributions = null;

    @SerializedName("next_page_token")
    private String nextPageToken;

    @SerializedName("results")
    private List<Result> results = null;

    @SerializedName("status")
    private String status;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
