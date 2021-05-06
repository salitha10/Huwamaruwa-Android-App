package com.example.huwamaruwa.buyerRentalRequestManage;

public class SentRentalRequestsByASellerModel {
    int image;
    String productTitle, category, date, rentalPrice;

    public SentRentalRequestsByASellerModel(int image, String rentalPrice, String productTitle, String category, String date) {
        this.image = image;
        this.rentalPrice = rentalPrice;
        this.productTitle = productTitle;
        this.category = category;
        this.date = date;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(String rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
