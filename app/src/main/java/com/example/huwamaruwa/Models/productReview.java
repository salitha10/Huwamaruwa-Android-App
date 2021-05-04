package com.example.huwamaruwa.Models;

public class productReview {
    private String productID;
    private String buyerID;
    private String comment;
    private String thumbnailURL;
    private String date;
    private double qualityRating;
    private double usabilityRating;
    private double priceRating;
    private double averageRating;


    //Getters and setters

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public double getUsabilityRating() { return usabilityRating; }

    public void setUsabilityRating(double usabilityRating) { this.usabilityRating = usabilityRating; }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
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

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public double getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(double qualityRating) {
        this.qualityRating = qualityRating;
    }

    public double getPriceRating() {
        return priceRating;
    }

    public void setPriceRating(double priceRating) {
        this.priceRating = priceRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
