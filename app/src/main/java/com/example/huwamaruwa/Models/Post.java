package com.example.huwamaruwa.Models;

public class Post {
    private String postId;
    private String premiumItem;
    private String dropdown1;
    private String getDropdown2;
    private String productName;
    private String rentalFee;
    private String contactNumber;
    private String description;
    private String deposit;
    private String images1;
    private String images2;
    private String images3;
    private String images4;

    public Post() {
    }

    public Post(String postId,String premiumItem, String dropdown1, String getDropdown2, String productName,
                String rentalFee, String contactNumber, String description, String deposit,
                String images1, String images2, String images3, String images4) {
        this.postId=postId;
        this.premiumItem = premiumItem;
        this.dropdown1 = dropdown1;
        this.getDropdown2 = getDropdown2;
        this.productName = productName;
        this.rentalFee = rentalFee;
        this.contactNumber = contactNumber;
        this.description = description;
        this.deposit = deposit;
        this.images1 = images1;
        this.images2 = images2;
        this.images3 = images3;
        this.images4 = images4;
    }


    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPremiumItem() {
        return premiumItem;
    }

    public void setPremiumItem(String premiumItem) {
        this.premiumItem = premiumItem;
    }

    public String getDropdown1() {
        return dropdown1;
    }

    public void setDropdown1(String dropdown1) {
        this.dropdown1 = dropdown1;
    }

    public String getGetDropdown2() {
        return getDropdown2;
    }

    public void setGetDropdown2(String getDropdown2) {
        this.getDropdown2 = getDropdown2;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(String rentalFee) {
        this.rentalFee = rentalFee;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getImages1() {
        return images1;
    }

    public void setImages1(String images1) {
        this.images1 = images1;
    }

    public String getImages2() {
        return images2;
    }

    public void setImages2(String images2) {
        this.images2 = images2;
    }

    public String getImages3() {
        return images3;
    }

    public void setImages3(String images3) {
        this.images3 = images3;
    }

    public String getImages4() {
        return images4;
    }

    public void setImages4(String images4) {
        this.images4 = images4;
    }
}
