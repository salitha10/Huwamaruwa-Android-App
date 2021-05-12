package com.example.huwamaruwa.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class SellerReview implements Parcelable {

    private String ID;
    private String sellerID;
    private String reviewerID;
    private String comment;
    private float priceRating;
    private float handlingRating;
    private float comRating;
    private float averageRating;
    private String date;

    public SellerReview(Parcel in) {
        ID = in.readString();
        sellerID = in.readString();
        reviewerID = in.readString();
        comment = in.readString();
        priceRating = in.readFloat();
        handlingRating = in.readFloat();
        comRating = in.readFloat();
        averageRating = in.readFloat();
        date = in.readString();
    }

    public static final Creator<SellerReview> CREATOR = new Creator<SellerReview>() {
        @Override
        public SellerReview createFromParcel(Parcel in) {
            return new SellerReview(in);
        }

        @Override
        public SellerReview[] newArray(int size) {
            return new SellerReview[size];
        }
    };

    public SellerReview() {

    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getPriceRating() {
        return priceRating;
    }

    public void setPriceRating(float priceRating) {
        this.priceRating = priceRating;
    }

    public float getHandlingRating() {
        return handlingRating;
    }

    public void setHandlingRating(float handlingRating) {
        this.handlingRating = handlingRating;
    }

    public float getComRating() {
        return comRating;
    }

    public void setComRating(float comRating) {
        this.comRating = comRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(sellerID);
        parcel.writeString(reviewerID);
        parcel.writeString(comment);
        parcel.writeFloat(priceRating);
        parcel.writeFloat(handlingRating);
        parcel.writeFloat(comRating);
        parcel.writeFloat(averageRating);
        parcel.writeString(date);
    }
}