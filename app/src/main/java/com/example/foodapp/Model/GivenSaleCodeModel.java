package com.example.foodapp.Model;

public class GivenSaleCodeModel {
    SaleCodeModel saleCodeModel;
    Boolean successful;

    public GivenSaleCodeModel(SaleCodeModel saleCodeModel, Boolean successful) {
        this.saleCodeModel = saleCodeModel;
        this.successful = successful;
    }

    public SaleCodeModel getSaleCodeModel() {
        return saleCodeModel;
    }

    public void setSaleCodeModel(SaleCodeModel saleCodeModel) {
        this.saleCodeModel = saleCodeModel;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }
}
