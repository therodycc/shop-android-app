package com.example.shopping.ScreenConsumer.Factory;

import com.example.shopping.ScreenConsumer.entity.BuyProductModelo;
import com.example.shopping.ScreenConsumer.entity.DetalleOrderModelo;
import com.example.shopping.ScreenConsumer.entity.OrderModelo;
import com.example.shopping.ScreenConsumer.entity.ProductModelo;
import com.example.shopping.ScreenConsumer.entity.StoreModelo;

import java.util.ArrayList;

public interface IFactory {
    void create(String[] datos);
    void all(String id);
    void createarraylist(String[] details);
    ArrayList<StoreModelo> storemodelolist();
    ArrayList<ProductModelo> productmodelolist();
    ArrayList<OrderModelo> ordersmodelolist();
    ArrayList<DetalleOrderModelo> detalleorderlist();
}
