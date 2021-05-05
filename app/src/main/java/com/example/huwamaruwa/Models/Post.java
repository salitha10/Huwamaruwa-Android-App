package com.example.huwamaruwa.Models;

import java.util.Date;

public class Post {
    private String id;
    private String isPremium;
    private String price;
    private String title;
    private Date date;
    private String description;
    private String images1;
    private String images2;
    private String images3;
    private String images4;


    private int categoryID;
    private String contactNumber;
    private String location;
    private String deposit;
    private String sellerID;
    private int maxRentalTime;
    private int minRentalTime;
    private boolean perHour;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(String isPremium) {
        this.isPremium = isPremium;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public int getMaxRentalTime() {
        return maxRentalTime;
    }

    public void setMaxRentalTime(int maxRentalTime) {
        this.maxRentalTime = maxRentalTime;
    }

    public int getMinRentalTime() {
        return minRentalTime;
    }

    public void setMinRentalTime(int minRentalTime) {
        this.minRentalTime = minRentalTime;
    }

    public boolean isPerHour() {
        return perHour;
    }

    public void setPerHour(boolean perHour) {
        this.perHour = perHour;
    }
}
