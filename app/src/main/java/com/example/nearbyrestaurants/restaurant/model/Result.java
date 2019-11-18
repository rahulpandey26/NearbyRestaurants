package com.example.nearbyrestaurants.restaurant.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Result implements Parcelable {

    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("icon")
    private String icon;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("opening_hours")
    private OpeningHours openingHours;

    @SerializedName("photos")
    private List<Photo> photos = null;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("plus_code")
    private PlusCode plusCode;

    @SerializedName("price_level")
    private int priceLevel;

    @SerializedName("rating")
    private double rating;

    @SerializedName("scope")
    private String scope;

    @SerializedName("alt_ids")
    private List<AltId> altIds = null;

    @SerializedName("reference")
    private String reference;

    @SerializedName("types")
    private List<String> types = null;

    @SerializedName("user_ratings_total")
    private int totalUserRatings;

    @SerializedName("vicinity")
    private String vicinity;

    protected Result(Parcel in) {
        geometry = in.readParcelable(Geometry.class.getClassLoader());
        icon = in.readString();
        id = in.readString();
        name = in.readString();
        openingHours = in.readParcelable(OpeningHours.class.getClassLoader());
        photos = in.createTypedArrayList(Photo.CREATOR);
        placeId = in.readString();
        priceLevel = in.readInt();
        rating = in.readDouble();
        scope = in.readString();
        reference = in.readString();
        types = in.createStringArrayList();
        totalUserRatings = in.readInt();
        vicinity = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(geometry, flags);
        dest.writeString(icon);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeParcelable(openingHours, flags);
        dest.writeTypedList(photos);
        dest.writeString(placeId);
        dest.writeInt(priceLevel);
        dest.writeDouble(rating);
        dest.writeString(scope);
        dest.writeString(reference);
        dest.writeStringList(types);
        dest.writeInt(totalUserRatings);
        dest.writeString(vicinity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public PlusCode getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(PlusCode plusCode) {
        this.plusCode = plusCode;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTotalUserRatings() {
        return totalUserRatings;
    }

    public void setTotalUserRatings(int totalUserRatings) {
        this.totalUserRatings = totalUserRatings;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

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

    public List<AltId> getAltIds() {
        return altIds;
    }

    public void setAltIds(List<AltId> altIds) {
        this.altIds = altIds;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
