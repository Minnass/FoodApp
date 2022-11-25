package com.example.foodapp.Model;

public class ItemCartModel {
    int quantity;
    String nameProduct;
    int img_id;
    float price;

    public ItemCartModel(int quantity, String nameProduct, int img_id, float price) {
                this.quantity = quantity;
                this.nameProduct = nameProduct;
                this.img_id = img_id;
                this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public int getImg_id() {
        return img_id;
    }

    public float getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
