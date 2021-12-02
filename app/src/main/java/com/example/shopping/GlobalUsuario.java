package com.example.shopping;

import com.example.shopping.ScreenConsumer.entity.BuyProductModelo;

import java.util.ArrayList;

public class GlobalUsuario {

    public static int idusuario;
    public static String username;
    public static String password;
    public static String address;
    public static int typeUSER;
    public static String firstname;
    public static String lastname;
    public static String phone;


    public static ArrayList<BuyProductModelo> buyProductModelos;

    //independiente
    public static int idCompany;

    public GlobalUsuario(int idusuario, String username, String password,int typeUSER,
                         String lastname , String firstname, String phone, String Address) {
        this.idusuario=idusuario;
        this.username=username;
        this.password=password;
        this.typeUSER = typeUSER;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address = Address;

    }
}
