package com.example.shopping.ScreenConsumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.activity.DetalleOrderActivity;
import com.example.shopping.ScreenConsumer.activity.ShopsActivity;
import com.example.shopping.ScreenConsumer.entity.OrderModelo;
import com.example.shopping.ScreenConsumer.entity.StoreModelo;
import com.google.android.material.card.MaterialCardView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class RecyclerAdapterOrder extends RecyclerView.Adapter<RecyclerAdapterOrder.MyViewHolder> {

    public ArrayList<OrderModelo> orderModeloArrayList;
    Context context;

    public RecyclerAdapterOrder(ArrayList<OrderModelo> orderModeloArrayList, Context context) {
        this. orderModeloArrayList =  orderModeloArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterOrder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.item_order,parent,false);
        RecyclerAdapterOrder.MyViewHolder viewHolder = new RecyclerAdapterOrder.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterOrder.MyViewHolder holder, int position) {
        holder.idorder.setText( orderModeloArrayList.get(position).getIdOrder());
        holder.nameCompany.setText( orderModeloArrayList.get(position).getNameCompany());
        holder.status.setText( orderModeloArrayList.get(position).getStatus());
        holder.total.setText( orderModeloArrayList.get(position).getSubTotal());
        holder.phone.setText( orderModeloArrayList.get(position).getPhone());
        holder.date.setText( orderModeloArrayList.get(position).getDate());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle var = new Bundle();

                Intent intent = new Intent(v.getContext(), DetalleOrderActivity.class);
                var.putString("idorder", holder.idorder.getText().toString());
                var.putString("state", holder.status.getText().toString());
                var.putString("phone", holder.phone.getText().toString());
                var.putString("total", holder.total.getText().toString());
                var.putString("namecompany", holder.nameCompany.getText().toString());
                intent.putExtras(var);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  orderModeloArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView idorder, nameCompany, total, status, date, phone;
        public MaterialCardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idorder = (TextView) itemView.findViewById(R.id.item_order_consumer_idOrden);
            nameCompany = (TextView) itemView.findViewById(R.id.item_order_consumer_nameCompany);
            total = (TextView) itemView.findViewById(R.id.item_order_consumer_subTotal);
            status = (TextView) itemView.findViewById(R.id.item_order_consumer_status);
            date = (TextView) itemView.findViewById(R.id.item_order_consumer_date);
            phone = (TextView) itemView.findViewById(R.id.item_order_consumer_phone);
            cardView = (MaterialCardView) itemView.findViewById(R.id.cardview_order_consumer);

        }

    }



}
