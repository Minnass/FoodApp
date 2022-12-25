package com.example.foodapp.Model.LoginModel;

public class ChangingPasswordModel {
    Boolean success;
    String message;

    public ChangingPasswordModel(Boolean success, String mesage) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMesage() {
        return message;
    }

    public void setMesage(String message) {
        this.message = message;
    }
}
