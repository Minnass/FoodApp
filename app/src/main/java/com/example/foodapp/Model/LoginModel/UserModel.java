package com.example.foodapp.Model.LoginModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date ;

public class UserModel implements Serializable {
    private String name;
    @SerializedName("dateofbirth")
    private Date dateOfBirth;
    private  String sex;
    private  String address;

    public UserModel(String name, Date dateOfBirth, String sex, String address) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

