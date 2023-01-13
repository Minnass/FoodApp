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
    private int userID;
    private String loginType;
    private  String role;

    public UserModel(String name, String dateOfBirth, String sex, String address, String email, int userID, String loginType, String role) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.address = address;
        this.email = email;
        this.userID = userID;
        this.loginType = loginType;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
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

