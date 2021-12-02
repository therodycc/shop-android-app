package com.example.shopping.screenSuplier.UtilidadesListView;

public class EntityCategoryModelo {

    String name,quantityCategory,id,Descripcion;

    public EntityCategoryModelo(String name, String quantityCategory, String id, String descripcion) {
        this.name = name;
        this.quantityCategory = quantityCategory;
        this.id = id;
        Descripcion = descripcion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantityCategory() {
        return quantityCategory;
    }

    public void setQuantityCategory(String quantityCategory) {
        this.quantityCategory = quantityCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
