package com.example.huwamaruwa.buyerRentalRequestManage;

import android.os.Parcel;
import android.os.Parcelable;

public class BuyerRentalRequestsModel implements Parcelable {

    String productTitle, category, quantity, requiredDate, Budget, buyerRequestId;

    public String getBuyerRequestId() {
        return buyerRequestId;
    }

    public void setBuyerRequestId(String buyerRequestId) {
        this.buyerRequestId = buyerRequestId;
    }

    protected BuyerRentalRequestsModel(Parcel in) {
        productTitle = in.readString();
        category = in.readString();
        quantity = in.readString();
        requiredDate = in.readString();
        Budget = in.readString();
        buyerRequestId = in.readString();
    }

    public static final Creator<BuyerRentalRequestsModel> CREATOR = new Creator<BuyerRentalRequestsModel>() {
        @Override
        public BuyerRentalRequestsModel createFromParcel(Parcel in) {
            return new BuyerRentalRequestsModel(in);
        }

        @Override
        public BuyerRentalRequestsModel[] newArray(int size) {
            return new BuyerRentalRequestsModel[size];
        }
    };

    public BuyerRentalRequestsModel(){

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productTitle);
        parcel.writeString(category);
        parcel.writeString(quantity);
        parcel.writeString(requiredDate);
        parcel.writeString(Budget);
        parcel.writeString(buyerRequestId);
    }
}
