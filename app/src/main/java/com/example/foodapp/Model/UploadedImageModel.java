package com.example.foodapp.Model;

public class UploadedImageModel {
    Boolean success;
    String imageName;

    public UploadedImageModel(Boolean success, String imageName) {
        this.success = success;
        this.imageName = imageName;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
