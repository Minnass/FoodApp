package com.example.foodapp.Model;

public class DetailProduct {
    int eaterNumber;
    String expiration;
    String preparationTime;
    String preservationGuide;

    public DetailProduct(int eaterNumber, String expiration, String preparationTime, String preservationGuide) {
        this.eaterNumber = eaterNumber;
        this.expiration = expiration;
        this.preparationTime = preparationTime;
        this.preservationGuide = preservationGuide;
    }

    public int getEaterNumber() {
        return eaterNumber;
    }

    public void setEaterNumber(int eaterNumber) {
        this.eaterNumber = eaterNumber;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getPreservationGuide() {
        return preservationGuide;
    }

    public void setPreservationGuide(String preservationGuide) {
        this.preservationGuide = preservationGuide;
    }
}
