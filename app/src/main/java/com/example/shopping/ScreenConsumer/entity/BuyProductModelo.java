package com.example.shopping.ScreenConsumer.entity;

public class BuyProductModelo {

    String id,name,price,cantidad,id_orden;

    public BuyProductModelo(String id, String name, String price, String cantidad, String id_orden) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.cantidad = cantidad;
        this.id_orden = id_orden;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getId_orden() {
        return id_orden;
    }

    public void setId_orden(String id_orden) {
        this.id_orden = id_orden;
    }
}
