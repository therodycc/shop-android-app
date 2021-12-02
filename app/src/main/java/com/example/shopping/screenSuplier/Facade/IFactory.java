package com.example.shopping.screenSuplier.Facade;

import com.example.shopping.screenSuplier.UtilidadesListView.EntityCategoryModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityCompanyModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityOrderModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.EntityProductModelo;

import java.util.ArrayList;

public interface IFactory {

    void Create(final String[] datos);
    void Delete(final String id);
    void Edit(final String[] datos);
    void AllSpecific(final String idUsuario);
    ArrayList<EntityCategoryModelo> categorysArrayList();
    ArrayList<EntityCompanyModelo> companyArrayList();
    ArrayList<EntityProductModelo> productArrayList();
    ArrayList<EntityOrderModelo> orderArrayList();

}
