package com.example.shopping.screenSuplier.UtilidadesListView;

public class EntityOrderModelo {

    String idorder, fullNameClient, phone, state, date, total,idCliente, comment;

    public EntityOrderModelo(String idorder, String fullNameClient, String phone, String state, String date, String total, String idCliente, String comment) {
        this.idorder = idorder;
        this.fullNameClient = fullNameClient;
        this.phone = phone;
        this.state = state;
        this.date = date;
        this.total = total;
        this.idCliente = idCliente;
        this.comment = comment;
    }

    public String getIdorder() {
        return idorder;
    }

    public void setIdorder(String idorder) {
        this.idorder = idorder;
    }

    public String getFullNameClient() {
        return fullNameClient;
    }

    public void setFullNameClient(String fullNameClient) {
        this.fullNameClient = fullNameClient;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
