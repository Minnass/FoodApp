package com.example.foodapp.Model.SQLiteModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ItemCartModel implements Parcelable {
    String foodName;
    int quantity;
    int Price;
    int discount;
    String image;
    boolean selected;

    protected ItemCartModel(Parcel in) {
        foodName = in.readString();
        quantity = in.readInt();
        Price = in.readInt();
        discount = in.readInt();
        image = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<ItemCartModel> CREATOR = new Creator<ItemCartModel>() {
        @Override
        public ItemCartModel createFromParcel(Parcel in) {
            return new ItemCartModel(in);
        }

        @Override
        public ItemCartModel[] newArray(int size) {
            return new ItemCartModel[size];
        }
    };

    public ItemCartModel(String foodName, int quantity, int Price, int discount, String image) {
        super();
        this.foodName = foodName;
        this.quantity = quantity;
        this.Price = Price;
        this.discount = discount;
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foodName);
        dest.writeInt(quantity);
        dest.writeInt(Price);
        dest.writeInt(discount);
        dest.writeString(image);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

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
}
