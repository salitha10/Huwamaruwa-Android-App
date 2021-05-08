package com.example.huwamaruwa.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductReviews implements Parcelable {
    private String productID;
    private String reviewerID;
    private String comment;
    private String thumbnailURL;
    private String date;
    private float qualityRating;
    private float usabilityRating;
    private float priceRating;
    private float averageRating;
    private String ID;

    //Default constructor
    public ProductReviews() {}

    public ProductReviews(Parcel in) {
        productID = in.readString();
        reviewerID = in.readString();
        comment = in.readString();
        thumbnailURL = in.readString();
        date = in.readString();
        qualityRating = in.readFloat();
        usabilityRating = in.readFloat();
        priceRating = in.readFloat();
        averageRating = in.readFloat();
        ID = in.readString();
    }

    public static final Creator<ProductReviews> CREATOR = new Creator<ProductReviews>() {
        @Override
        public ProductReviews createFromParcel(Parcel in) {
            return new ProductReviews(in);
        }

        @Override
        public ProductReviews[] newArray(int size) {
            return new ProductReviews[size];
        }
    };

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getUsabilityRating() {
        return usabilityRating;
    }

    public void setUsabilityRating(float usabilityRating) {
        this.usabilityRating = usabilityRating;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public float getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(float qualityRating) {
        this.qualityRating = qualityRating;
    }

    public float getPriceRating() {
        return priceRating;
    }

    public void setPriceRating(float priceRating) {
        this.priceRating = priceRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productID);
        parcel.writeString(reviewerID);
        parcel.writeString(comment);
        parcel.writeString(thumbnailURL);
        parcel.writeString(date);
        parcel.writeFloat(qualityRating);
        parcel.writeFloat(usabilityRating);
        parcel.writeFloat(priceRating);
        parcel.writeFloat(averageRating);
        parcel.writeString(ID);
    }
}
