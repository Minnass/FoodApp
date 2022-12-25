package com.example.foodapp.Model;

import java.io.Serializable;

public class DeliveryModel implements Serializable {
        String name;
        String icon;
        int fee;
        String description;
        boolean checked;

    public DeliveryModel(String name, String icon, int fee, String description) {
        this.name = name;
        this.icon = icon;
        this.fee = fee;
        this.description = description;
        checked=false;
    }

    public String getDeliveryName() {
        return name;
    }

    public void setDeliveryName(String name) {
        this.name = name;
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
        return icon;
    }

    public void setDeliveryImage(String icon) {
        this.icon = icon;
    }
}
