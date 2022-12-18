package com.example.foodapp.Model.LoginModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date ;

public class UserModel implements Serializable {
    private String name;
    @SerializedName("dateofbirth")
    private String dateOfBirth;
    private  String sex;
    private  String address;
    private  String email;

    public UserModel(String name, String dateOfBirth, String sex, String address, String email) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.address = address;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
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

