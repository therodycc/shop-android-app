package com.example.shopping.screenSuplier.UtilidadesListView;

public class EntityProductModelo {

    String id, description, name, price, image,nameCategory;

    public EntityProductModelo(String id, String description, String name, String price, String image, String nameCategory) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
        this.image = image;
        this.nameCategory = nameCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
