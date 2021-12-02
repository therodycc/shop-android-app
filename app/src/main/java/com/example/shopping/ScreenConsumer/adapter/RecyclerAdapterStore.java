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

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.activity.ShopsActivity;
import com.example.shopping.ScreenConsumer.entity.StoreModelo;
import com.example.shopping.screenSuplier.UtilidadesListView.RecyclerViewAdapterCategory;
import com.google.android.material.card.MaterialCardView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class RecyclerAdapterStore extends RecyclerView.Adapter<RecyclerAdapterStore.MyViewHolder> {

    public ArrayList<StoreModelo> storeModeloArrayList;
    Context context;

    public RecyclerAdapterStore(ArrayList<StoreModelo> storeModeloArrayList, Context context) {
        this.storeModeloArrayList = storeModeloArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterStore.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.item_consumer_shops,parent,false);
        RecyclerAdapterStore.MyViewHolder viewHolder = new RecyclerAdapterStore.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterStore.MyViewHolder holder, int position) {

        holder.name.setText(storeModeloArrayList.get(position).getName());
        holder.social.setText(storeModeloArrayList.get(position).getSocial());
        holder.phone.setText(storeModeloArrayList.get(position).getTel());
        holder.id.setText(storeModeloArrayList.get(position).getId());
        Glide.with(context).load(storeModeloArrayList.get(position).getImage()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle var = new Bundle();
                Intent intent = new Intent(v.getContext(), ShopsActivity.class);
                var.putString("idcompany",holder.id.getText().toString());
                intent.putExtras(var);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeModeloArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, id, social, phone;
        public MaterialCardView cardView;
        public RoundedImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.consumer_name_store);
            id = (TextView) itemView.findViewById(R.id.consumer_id_store);
            social = (TextView) itemView.findViewById(R.id.consumer_social_store);
            phone = (TextView) itemView.findViewById(R.id.consumer_phone_store);
            imageView = (RoundedImageView) itemView.findViewById(R.id.consumer_image_store);
            cardView = (MaterialCardView) itemView.findViewById(R.id.cardview_store_consumer);

        }

    }



}
