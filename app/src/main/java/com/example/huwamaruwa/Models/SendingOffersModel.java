package com.example.huwamaruwa.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class SendingOffersModel implements Parcelable {

    String productName, quantity, rental, productRequestId, sendingRequestId, status,  productImage;

    public SendingOffersModel() {
    }

    protected SendingOffersModel(Parcel in) {
        productName = in.readString();
        quantity = in.readString();
        rental = in.readString();
        productRequestId = in.readString();
        sendingRequestId = in.readString();
        status = in.readString();
        productImage = in.readString();
    }

    public static final Creator<SendingOffersModel> CREATOR = new Creator<SendingOffersModel>() {
        @Override
        public SendingOffersModel createFromParcel(Parcel in) {
            return new SendingOffersModel(in);
        }

        @Override
        public SendingOffersModel[] newArray(int size) {
            return new SendingOffersModel[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public String getProductRequestId() {
        return productRequestId;
    }

    public void setProductRequestId(String productRequestId) {
        this.productRequestId = productRequestId;
    }

    public String getSendingRequestId() {
        return sendingRequestId;
    }

    public void setSendingRequestId(String sendingRequestId) {
        this.sendingRequestId = sendingRequestId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productName);
        parcel.writeString(quantity);
        parcel.writeString(rental);
        parcel.writeString(productRequestId);
        parcel.writeString(sendingRequestId);
        parcel.writeString(status);
        parcel.writeString(productImage);
    }
}
