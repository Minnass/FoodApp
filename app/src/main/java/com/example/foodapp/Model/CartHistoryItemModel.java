package com.example.foodapp.Model;

import java.io.Serializable;
import java.util.Date;

public class CartHistoryItemModel  implements Serializable {
    String orderCode;
    Date orderDate;
    float totalPrice;
    String receivedUser;
    String phoneNumber;
    String address;
    int saleCode;
    String deliveryType;
    String paymentType;
    int deliveryFee;

    public CartHistoryItemModel(String orderCode, Date orderDate, float totalPrice, String receivedUser, String phoneNumber, String address, int saleCode, String deliveryType, String paymentType, int deliveryFee) {
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.receivedUser = receivedUser;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.saleCode = saleCode;
        this.deliveryType = deliveryType;
        this.paymentType = paymentType;
        this.deliveryFee = deliveryFee;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getReceivedUser() {
        return receivedUser;
    }

    public void setReceivedUser(String receivedUser) {
        this.receivedUser = receivedUser;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(int saleCode) {
        this.saleCode = saleCode;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
