package com.example.huwamaruwa.Models;

import java.io.Serializable;

public class ProductReviews implements Serializable {

    private String productID;
    private String comment;
    private String thumbnailURL;
    private String date;
    private float qualityRating;
    private float usabilityRating;
    private float priceRating;
    private float averageRating;
    private String reviewerID;
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

//Getters and setters

    //public String getDate() { return date; }

    //public void setDate(String date) { this.date = date; }

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

    //public String getBuyerID() { return reviewerID
    // ; }

    //public void setBuyerID(String reviewerID
    // ) { this.reviewerID
    // = reviewerID
    // ; }

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

    public String getBuyerID() {
        return reviewerID
                ;
    }

    public void setBuyerID(String reviewerID
    ) {
        this.reviewerID
                = reviewerID
        ;
    }
}
