package com.example.shopping.screenSuplier.Facade;

import android.content.Context;

import com.example.shopping.screenSuplier.Facade.EntityFactory.FactoryCategory;
import com.example.shopping.screenSuplier.Facade.EntityFactory.FactoryCompany;
import com.example.shopping.screenSuplier.Facade.EntityFactory.FactoryOrder;
import com.example.shopping.screenSuplier.Facade.EntityFactory.FactoryProduct;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCategoryModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCompanyModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityOrderModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityProductModelo;

import java.util.ArrayList;

public class FactoryMaker {

    IFactory FactoryCategory;
    IFactory FactoryCompany;
    IFactory FactoryProduct;
    IFactory FactoryOrder;


    public FactoryMaker(Context context, String url) {
        FactoryCategory = new FactoryCategory(context, url);
        FactoryCompany = new FactoryCompany(context, url);
        FactoryProduct = new FactoryProduct(context, url);
            FactoryOrder = new FactoryOrder(context,url);

    }

    public void FactoryCategoryMethodCreate(String[] date) {
        FactoryCategory.Create(date);
    }

    public void FactoryCategoryMethodUpdate(String[] date) {
        FactoryCategory.Edit(date);
    }

    public void FactoryCategoryMethodDelete(String iddate) {
        FactoryCategory.Delete(iddate);
    }

    public void FactoryCategoryMethodAll(String date) {
        FactoryCategory.AllSpecific(date);
    }

    public ArrayList<EntityCategoryModelo> getCategory() {
        return FactoryCategory.categorysArrayList();
    }

    public void FactoryCompanyMethodCreated(String[] date) {
        FactoryCompany.Create(date);
    }

    public void FactoryCompanyMethodAll(String date) {
        FactoryCompany.AllSpecific(date);
    }

    public ArrayList<EntityCompanyModelo> getCompany() {
        return FactoryCompany.companyArrayList();
    }

    public void FactoryProductMethodCreated(String[] dates) {
        FactoryProduct.Create(dates);
    }


    public void FactoryProductMethodAll(String idcompany) {
        FactoryProduct.AllSpecific(idcompany);
    }

    public ArrayList<EntityProductModelo> getProduct() {
        return FactoryProduct.productArrayList();
    }

    public void FactoryProductMethodDelete(String id_date) {
        FactoryProduct.Delete(id_date);
    }

    public void FactoryProductMethodUpdate(String[] date) {
        FactoryProduct.Edit(date);
    }

    public void FactoryOrdersMethodAll(String id_date) {
        FactoryOrder.AllSpecific(id_date);
    }

    public ArrayList<EntityOrderModelo> getOrders() {
        return FactoryOrder.orderArrayList();
    }


}
