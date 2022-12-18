package com.example.foodapp.Model;

public class SaleCodeModel {
    String code;
    String codeName;
    int saleValue;


    public SaleCodeModel(String code, String codeName, int saleValue, Boolean successful) {
        this.code = code;
        this.codeName = codeName;
        this.saleValue = saleValue;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public int getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(int saleValue) {
        this.saleValue = saleValue;
    }
}
