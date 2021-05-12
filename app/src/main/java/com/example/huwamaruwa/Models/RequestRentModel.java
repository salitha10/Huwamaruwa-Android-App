package com.example.huwamaruwa.Models;

public class RequestRentModel {

    private String id;
    private String isPremium;
    private String Address;
    private String ContactNumber;
    private String Duration;
    private Double total;
    private Double initialDeposit;
    private String productId;
    private String dateDif;
    private String status;
    private String userId;
    private String sellerId;

    public RequestRentModel() {
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateDif() {
        return dateDif;
    }

    public void setDateDif(String dateDif) {
        this.dateDif = dateDif;
    }

    public String getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(String isPremium) {
        this.isPremium = isPremium;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(Double initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
