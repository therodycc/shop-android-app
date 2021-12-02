package com.example.shopping.ScreenConsumer.entity;

public class OrderModelo {

    String idOrder, nameCompany, subTotal, status, date, phone;

    public OrderModelo(String idOrder, String nameCompany, String subTotal, String status, String date, String phone) {
        this.idOrder = idOrder;
        this.nameCompany = nameCompany;
        this.subTotal = subTotal;
        this.status = status;
        this.date = date;
        this.phone = phone;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
