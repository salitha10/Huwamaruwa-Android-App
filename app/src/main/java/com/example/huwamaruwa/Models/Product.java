package com.example.huwamaruwa.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Product implements Parcelable {

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {

        @Override
        public Product createFromParcel(Parcel source) {
// TODO Auto-generated method stub
            return new Product(source);  //using parcelable constructor
        }

        @Override
        public Product[] newArray(int size) {
// TODO Auto-generated method stub
            return new Product[size];
        }
    };

    private String id;
    private boolean isPremium;
    private double price;
    private String title;

    private String description;
    private String images1;
    private String images2;
    private String images3;
    private String images4;
    private int categoryID;
    private String contactNumber;
    private String location;
    private double depositPercentage;
    private String sellerID;
    private int maxRentalTime;
    private int minRentalTime;
    private boolean perHour;

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public String getImages2() {
        return images2;
    }

    public String getImages3() {
        return images3;
    }

    public String getImages4() {
        return images4;
    }

    private int date_in_sec;
    private int date_in_min;
    private int date_in_hour;
    private int date_in_day;
    private int date_in_year;
    private Date date;
    public Product() {
    }

    public int getDate_in_year() {
        return date_in_year;
    }

    public void setDate_in_year(int date_in_year) {
        this.date_in_year = date_in_year;
    }

    public int getDate_in_sec() {
        return date_in_sec;
    }

    public void setDate_in_sec(int date_in_sec) {
        this.date_in_sec = date_in_sec;
    }

    public int getDate_in_min() {
        return date_in_min;
    }

    public void setDate_in_min(int date_in_min) {
        this.date_in_min = date_in_min;
    }

    public int getDate_in_hour() {
        return date_in_hour;
    }

    public void setDate_in_hour(int date_in_hour) {
        this.date_in_hour = date_in_hour;
    }

    public int getDate_in_day() {
        return date_in_day;
    }

    public void setDate_in_day(int date_in_day) {
        this.date_in_day = date_in_day;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(boolean premium) {
        isPremium = premium;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

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

    public double getDepositPercentage() {
        return depositPercentage;
    }

    public void setDepositPercentage(double deposit) {
        this.depositPercentage = deposit;
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

    public Product(Parcel in) {
        String data[] = new String[13];
        in.readStringArray(data);

        this.price =Double.parseDouble(data[0]) ;
        this.title = data[1];
        this.description = data[2];
        this.images1 = data[3];
        this.images2 = data[4];
        this.images3 = data[5];
        this.images4 = data[6];
        this.isPremium =Boolean.parseBoolean(data[7]);
        this.perHour = Boolean.parseBoolean(data[8]);
        this.maxRentalTime = Integer.parseInt(data[9]);
        this.minRentalTime = Integer.parseInt(data[10]);
        this.id = data[11];
        this.contactNumber = data[12];
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                String.valueOf(this.price),
                this.title,
                this.description,
                this.images1,
                this.images2,
                this.images3,
                this.images4,
                String.valueOf(this.isPremium),
                String.valueOf(this.perHour),
                String.valueOf(this.maxRentalTime),
                String.valueOf(this.minRentalTime),
                this.id,
                this.contactNumber
        });
    }



}
