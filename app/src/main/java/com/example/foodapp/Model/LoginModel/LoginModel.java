package com.example.foodapp.Model.LoginModel;

public class LoginModel {
    boolean success;
    UserModel user;

    public LoginModel(boolean success, UserModel user) {
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
