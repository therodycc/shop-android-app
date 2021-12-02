package com.example.shopping.ScreenConsumer.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.entity.ProductModelo;
import com.example.shopping.screenMain.ScreenSignup;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class RecyclerAdapterProduct extends RecyclerView.Adapter<RecyclerAdapterProduct.MyViewHolder> {

    public ArrayList<ProductModelo> productModeloArrayList;
    public ArrayList<ProductModelo> checkprodutArrayList = new ArrayList<>();
    Context context;

    public RecyclerAdapterProduct(ArrayList<ProductModelo> productModeloArrayList, Context context) {
        this.productModeloArrayList = productModeloArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterProduct.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_consumer_product_c, parent, false);
        RecyclerAdapterProduct.MyViewHolder viewHolder = new RecyclerAdapterProduct.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterProduct.MyViewHolder holder, int position) {

        holder.name.setText(productModeloArrayList.get(position).getName());
        holder.price.setText(productModeloArrayList.get(position).getPrice());
        holder.description.setText(productModeloArrayList.get(position).getDescription());
        holder.id.setText(productModeloArrayList.get(position).getId());
        Glide.with(context).load(productModeloArrayList.get(position).getImage()).into(holder.image);

        holder.descriptionOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialAlertDialogBuilder build = new MaterialAlertDialogBuilder(v.getContext())
                        .setTitle("Description")
                        .setIcon(R.drawable.ic_description)
                        .setBackground(context.getResources().getDrawable(R.drawable.background_alert_term_policy))
                        .setMessage(holder.description.getText().toString());
                build.setPositiveButton("Great!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                build.show();
            }
        });

        holder.setItemClickListener(new itemClickListener() {
            @Override
            public void onitemclick(View v, int post) {
                CheckBox checkBox = (CheckBox) v;
                if(checkBox.isChecked()){
                    checkprodutArrayList.add(productModeloArrayList.get(post));
                }else  if(!checkBox.isChecked()){
                    checkprodutArrayList.remove(productModeloArrayList.get(post));
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return productModeloArrayList.size();
    }

    public void filterList(ArrayList<ProductModelo> filterlist) {
        productModeloArrayList =filterlist;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, id, price, description, descriptionOption;
        public RoundedImageView image;
        CheckBox checkBox;
        itemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.consumer_name_product_store);
            id = (TextView) itemView.findViewById(R.id.consumer_id_product_store);
            price = (TextView) itemView.findViewById(R.id.consumer_price_product_store);
            description = (TextView) itemView.findViewById(R.id.consumer_description_product_store);
            image = (RoundedImageView) itemView.findViewById(R.id.consumer_image_product_store);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_product_consumer);
            descriptionOption = (TextView) itemView.findViewById(R.id.tv_description_option);

            checkBox.setOnClickListener(this);
        }

        public  void setItemClickListener(itemClickListener ic){
            this.itemClickListener =ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onitemclick(v,getLayoutPosition());
        }
    }

    }




