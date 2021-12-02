package com.example.shopping.ScreenConsumer.Factory;

import android.content.Context;

public class Creator {

    String link;
    Context context;

    public Creator(String link, Context context){
        this.link = link;
        this.context = context;
    }

    public IFactory getFactiry(int typeFactory){

        if(typeFactory == 1){
            return new Store(link,context);
        } else if(typeFactory == 2){
            return new Product(link,context);

        }else if(typeFactory == 3){
            return new Orders(link,context);
        }else if(typeFactory == 4){
            return new DetalleOrders(link,context);

        }else{
            return null;
        }
 }

}
