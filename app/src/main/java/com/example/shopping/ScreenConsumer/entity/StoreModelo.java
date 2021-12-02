package com.example.shopping.ScreenConsumer.entity;

public class StoreModelo {

    String id, name, social, tel, image;

    public StoreModelo(String id, String name, String social, String tel, String image) {
        this.id = id;
        this.name = name;
        this.social = social;
        this.tel = tel;
        this.image = image;
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

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
