package com.example.huwamaruwa.Models;

public class SellerReview {

    String ID;
    String productID;
    String reviewerID;
    String comment;
    float priceRating;
    float handlingRating;
    float comRating;
    String date;

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

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getString() {
        return date;
    }

    public void setString(String date) {
        this.date = date;
    }
}