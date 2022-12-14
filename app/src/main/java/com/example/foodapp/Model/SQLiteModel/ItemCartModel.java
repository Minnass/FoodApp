package com.example.foodapp.Model.SQLiteModel;

public class ItemCartModel {
    String foodName;
    int quantity;
    int Price;
    int discount;
    String image;
    boolean selected;

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ItemCartModel(String foodName, int quantity, int price, int discount, String image) {
        this.foodName = foodName;
        this.quantity = quantity;
        Price = price;
        this.discount = discount;
        this.image = image;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
