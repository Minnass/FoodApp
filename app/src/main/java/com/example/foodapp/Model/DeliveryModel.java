package com.example.foodapp.Model;

import java.io.Serializable;

public class DeliveryModel implements Serializable {
        String deliveryName;
        String deliveryImage;
        int fee;
        String description;
        boolean checked;

    public DeliveryModel(String deliveryName, String deliveryImage, int fee, String description) {
        this.deliveryName = deliveryName;
        this.deliveryImage = deliveryImage;
        this.fee = fee;
        this.description = description;
        checked=false;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryImage() {
        return deliveryImage;
    }

    public void setDeliveryImage(String deliveryImage) {
        this.deliveryImage = deliveryImage;
    }
}
