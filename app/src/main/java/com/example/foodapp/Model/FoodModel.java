package com.example.foodapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodModel implements Serializable {
    @SerializedName("foodname")
    String name;
    @SerializedName("price")
    float price;
    @SerializedName("image")
    String image;
    @SerializedName("discription")
    String description;
//    Categories category;
    @SerializedName("discount")
    int discount;
    @SerializedName("quantitysold")
    int quantity;

    @SerializedName("eaterNumber")
    int eaterNumber;

    public FoodModel(String name, float price, String image, String description, int discount, int quantity, int eaterNumber) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.discount = discount;
        this.quantity = quantity;
        this.eaterNumber = eaterNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getEaterNumber() {
        return eaterNumber;
    }

    public void setEaterNumber(int eaterNumber) {
        this.eaterNumber = eaterNumber;
    }
}
