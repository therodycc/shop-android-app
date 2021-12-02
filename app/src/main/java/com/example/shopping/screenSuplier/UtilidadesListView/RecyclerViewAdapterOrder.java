package com.example.shopping.screenSuplier.UtilidadesListView;

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
import com.example.shopping.screenSuplier.CompleteOrderActivity;
import com.example.shopping.screenSuplier.EditAndDeleteCategoryActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RecyclerViewAdapterOrder extends RecyclerView.Adapter<RecyclerViewAdapterOrder.ViewHolder> {

    public ArrayList<EntityOrderModelo> orderModeloArrayList;
    Context context;

    public RecyclerViewAdapterOrder(ArrayList<EntityOrderModelo> orderModeloArrayList, Context context) {
        this.orderModeloArrayList = orderModeloArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerViewAdapterOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_order_suplier, parent, false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapterOrder.ViewHolder holder, int position) {
        holder.id.setText(orderModeloArrayList.get(position).getIdorder());
        holder.idCliente.setText(orderModeloArrayList.get(position).getIdCliente());
        holder.fullNameClient.setText(orderModeloArrayList.get(position).getFullNameClient());
        holder.state.setText(orderModeloArrayList.get(position).getState());
        holder.total.setText(orderModeloArrayList.get(position).getTotal());
        holder.phone.setText(orderModeloArrayList.get(position).getPhone());
        holder.date.setText(orderModeloArrayList.get(position).getDate());
        holder.comment.setText(orderModeloArrayList.get(position).getComment());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Bundle var = new Bundle();
                Intent start = new Intent(v.getContext(), CompleteOrderActivity.class);
                var.putString("id_order", holder.id.getText().toString());
                var.putString("nameclient", holder.fullNameClient.getText().toString());
                var.putString("date", holder.date.getText().toString());
                var.putString("total", holder.total.getText().toString());
                var.putString("phone", holder.phone.getText().toString());
                var.putString("comment", holder.comment.getText().toString());
                var.putString("state", holder.state.getText().toString());
                start.putExtras(var);
                v.getContext().startActivity(start);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderModeloArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView id,fullNameClient, phone, state, date, total,idCliente, comment;
        public MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameClient = (TextView) itemView.findViewById(R.id.item_orderSuplier_fullnamecliente);
            id = (TextView) itemView.findViewById(R.id.item_orderSuplier_idOrder);
            state = (TextView) itemView.findViewById(R.id.item_orderSuplier_stateorder);
            total = (TextView) itemView.findViewById(R.id.item_orderSuplier_totalorder);
            phone = (TextView) itemView.findViewById(R.id.item_orderSuplier_phoneclient);
            idCliente = (TextView) itemView.findViewById(R.id.item_orderSuplier_idClient);
            date = (TextView) itemView.findViewById(R.id.item_orderSuplier_dateorder);
            comment = (TextView) itemView.findViewById(R.id.item_orderSuplier_commentorder);
            cardView = (MaterialCardView) itemView.findViewById(R.id.cardview_orderConsumer_company);

        }

    }


}
