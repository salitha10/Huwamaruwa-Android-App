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
    private String location;
    private String deposit;
    private String sellerID;
    private int maxRentalTime;
    private int minRentalTime;
    private boolean perHour;


    public Product() {
    }

    public Product(Parcel in) {
        String data[] = new String[8];
        in.readStringArray(data);

        this.price = data[0];
        this.title = data[1];
        this.description = data[2];
        this.images1 = data[3];
        this.images2 = data[4];
        this.images3 = data[5];
        this.images4 = data[6];
        this.isPremium = data[7];
    }

    public String getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(String premium) {
        isPremium = premium;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.price, this.title, this.description, this.images1, this.images2, this.images3, this.images4, this.isPremium});
    }
}
