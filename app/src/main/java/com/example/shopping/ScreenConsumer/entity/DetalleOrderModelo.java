package com.example.shopping.ScreenConsumer.entity;

public class DetalleOrderModelo {

    String id, name, cant, price;

    public DetalleOrderModelo(String id, String name, String cant, String price) {
        this.id = id;
        this.name = name;
        this.cant = cant;
        this.price = price;
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

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
