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
import com.example.shopping.screenSuplier.EditAndDeleteCategoryActivity;
import com.google.android.material.card.MaterialCardView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class RecyclerViewAdapterCategory extends RecyclerView.Adapter<RecyclerViewAdapterCategory.ViewHolder> {

public ArrayList<EntityCategoryModelo> categoryModeloArrayList ;
    Context context;

public RecyclerViewAdapterCategory(ArrayList<EntityCategoryModelo> categoryModeloArrayList, Context context) {
        this.categoryModeloArrayList = categoryModeloArrayList;
        this.context = context;
        }


@NonNull
@Override
public RecyclerViewAdapterCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.item_category_list,parent,false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;
        }

@Override
public void onBindViewHolder(@NonNull final RecyclerViewAdapterCategory.ViewHolder holder, int position) {
        holder.name.setText(categoryModeloArrayList.get(position).getName());
        holder.quantifyCategory.setText(categoryModeloArrayList.get(position).getQuantityCategory());
        holder.id.setText(categoryModeloArrayList.get(position).getId());
        holder.descripcion.setText(categoryModeloArrayList.get(position).getDescripcion());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Bundle var = new Bundle();
        Intent start = new Intent(v.getContext(), EditAndDeleteCategoryActivity.class);
        var.putString("id_category", holder.id.getText().toString());
        var.putString("name_category", holder.name.getText().toString());
        var.putString("descripcion_category", holder.descripcion.getText().toString());
        start.putExtras(var);
        v.getContext().startActivity(start);
        }
        });

        }

@Override
public int getItemCount() {
        return categoryModeloArrayList.size();
        }

public static class ViewHolder extends RecyclerView.ViewHolder{
    public TextView name,quantifyCategory, id, descripcion;
    public MaterialCardView cardView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.item_nameCategory);
        id = (TextView) itemView.findViewById(R.id.item_idCategory);
        quantifyCategory = (TextView) itemView.findViewById(R.id.item_countCategory);
        descripcion = (TextView) itemView.findViewById(R.id.item_descripcionCategory);
        cardView = (MaterialCardView) itemView.findViewById(R.id.cardview_category_company);

    }

}



}
