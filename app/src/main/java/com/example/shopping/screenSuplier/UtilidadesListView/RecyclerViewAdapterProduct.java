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

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.screenSuplier.EditAndDeleteCategoryActivity;
import com.example.shopping.screenSuplier.EditAndDeleteProductActivity;
import com.google.android.material.card.MaterialCardView;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapterProduct extends RecyclerView.Adapter<RecyclerViewAdapterProduct.ViewHolder> {

    public ArrayList<EntityProductModelo> productModeloArrayList;
    Context context;

    public RecyclerViewAdapterProduct(ArrayList<EntityProductModelo> productModeloArrayList, Context context) {
        this.productModeloArrayList = productModeloArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerViewAdapterProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.item_product_suplier,parent,false);
        RecyclerViewAdapterProduct.ViewHolder mvh = new RecyclerViewAdapterProduct.ViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapterProduct.ViewHolder holder, final int position) {
        holder.id.setText(productModeloArrayList.get(position).getId());
        holder.nameCategory.setText(productModeloArrayList.get(position).getNameCategory());
        holder.name.setText(productModeloArrayList.get(position).getName());
        holder.price.setText(productModeloArrayList.get(position).getPrice());
        Glide.with(context).load(productModeloArrayList.get(position).getImage()).into(holder.image);
        holder.description.setText(productModeloArrayList.get(position).getDescription());
        final String path = productModeloArrayList.get(position).getImage();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle var = new Bundle();
                Intent start = new Intent(v.getContext(), EditAndDeleteProductActivity.class);
                var.putString("id", holder.id.getText().toString());
                var.putString("name", holder.name.getText().toString());
                var.putString("description", holder.description.getText().toString());
                var.putString("image", path);
                var.putString("category",holder.nameCategory.getText().toString());
                var.putString("price",holder.price.getText().toString());
                start.putExtras(var);
                v.getContext().startActivity(start);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productModeloArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id,name,description,price,nameCategory;
        public RoundedImageView image;
        public MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_nameProduct);
            id = (TextView) itemView.findViewById(R.id.txt_idProduct);
            description = (TextView) itemView.findViewById(R.id.txt_descriptionProduct);
            nameCategory = (TextView) itemView.findViewById(R.id.txt_categoryProduct);
            price = (TextView) itemView.findViewById(R.id.txt_priceProduct);
            image = (RoundedImageView) itemView.findViewById(R.id.cr_imagenProduct);

            cardView = (MaterialCardView) itemView.findViewById(R.id.cardView_itemProduct);

        }

    }



}
