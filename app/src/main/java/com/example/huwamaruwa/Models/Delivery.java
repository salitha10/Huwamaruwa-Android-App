package com.example.huwamaruwa.Models;

public class Delivery {

    private String pickedUP;
    private String pickedUpDate;
    private String inTransit;
    private String InTransitDate;
    private String Delivered;
    private String deliveredDate;

    public String getInTransitDate() {
        return InTransitDate;
    }

    public void setInTransitDate(String inTransitDate) {
        InTransitDate = inTransitDate;
    }

    public String getPickedUP() {
        return pickedUP;
    }

    public void setPickedUP(String pickedUP) {
        this.pickedUP = pickedUP;
    }

    public String getPickedUpDate() {
        return pickedUpDate;
    }

    public void setPickedUpDate(String pickedUpDate) {
        this.pickedUpDate = pickedUpDate;
    }

    public String getInTransit() {
        return inTransit;
    }

    public void setInTransit(String inTransit) {
        this.inTransit = inTransit;
    }

    public String getDelivered() {
        return Delivered;
    }

    public void setDelivered(String delivered) {
        Delivered = delivered;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
}
