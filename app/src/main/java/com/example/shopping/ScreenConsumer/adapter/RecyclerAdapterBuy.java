package com.example.shopping.ScreenConsumer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.entity.BuyProductModelo;
import com.example.shopping.ScreenConsumer.entity.ProductModelo;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class RecyclerAdapterBuy extends RecyclerView.Adapter<RecyclerAdapterBuy.MyViewHolder> {

    public ArrayList<BuyProductModelo> buyProductModelos;
    Context context;

    public RecyclerAdapterBuy(ArrayList<BuyProductModelo> buyProductModelos, Context context) {
        this.buyProductModelos = buyProductModelos;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterBuy.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_buy_consumer, parent, false);
        RecyclerAdapterBuy.MyViewHolder viewHolder = new RecyclerAdapterBuy.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterBuy.MyViewHolder holder, int position) {

        holder.name.setText(buyProductModelos.get(position).getName());
        holder.price.setText(buyProductModelos.get(position).getPrice());
        holder.count.setText(buyProductModelos.get(position).getCantidad());
        holder.id.setText(buyProductModelos.get(position).getId());
        holder.idOrden.setText(buyProductModelos.get(position).getId_orden());

    }

    @Override
    public int getItemCount() {
        return buyProductModelos.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, id, price, count, idOrden;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_product_buy);
            id = (TextView) itemView.findViewById(R.id.id_product_buy);
            price = (TextView) itemView.findViewById(R.id.price_product_buy);
            count = (TextView) itemView.findViewById(R.id.count_product_buy);
            idOrden = (TextView) itemView.findViewById(R.id.idorden_product_buy);
        }

    }

    }




