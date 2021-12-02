package com.example.shopping.ScreenConsumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.activity.DetalleOrderActivity;
import com.example.shopping.ScreenConsumer.entity.DetalleOrderModelo;
import com.example.shopping.ScreenConsumer.entity.OrderModelo;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RecyclerAdapterDetalleOrders extends RecyclerView.Adapter<RecyclerAdapterDetalleOrders.MyViewHolder> {

    public ArrayList<DetalleOrderModelo> detalleOrderModelos;
    Context context;

    public RecyclerAdapterDetalleOrders(ArrayList<DetalleOrderModelo> detalleOrderModelos, Context context) {
        this. detalleOrderModelos =  detalleOrderModelos;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterDetalleOrders.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.item_buy_consumer,parent,false);
        RecyclerAdapterDetalleOrders.MyViewHolder viewHolder = new RecyclerAdapterDetalleOrders.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterDetalleOrders.MyViewHolder holder, int position) {
        holder.id.setText( detalleOrderModelos.get(position).getId());
        holder.name.setText( detalleOrderModelos.get(position).getName());
        holder.cant.setText( detalleOrderModelos.get(position).getCant());
        holder.price.setText( detalleOrderModelos.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return  detalleOrderModelos.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView id,name,cant, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id_product_buy);
            name = (TextView) itemView.findViewById(R.id.name_product_buy);
            cant = (TextView) itemView.findViewById(R.id.count_product_buy);
            price = (TextView) itemView.findViewById(R.id.price_product_buy);

        }

    }



}
