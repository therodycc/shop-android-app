package com.example.shopping.screenSuplier.UtilidadesListView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.screenSuplier.SecondSuplierActivity;
import com.google.android.material.card.MaterialCardView;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterCompany extends RecyclerView.Adapter<RecyclerViewAdapterCompany.ViewHolder> {

    public ArrayList<EntityCompanyModelo> listCompany;
    Context context;

    public RecyclerViewAdapterCompany(Context context, ArrayList<EntityCompanyModelo> listCompany) {
        this.listCompany = listCompany;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterCompany.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.item_company_list,parent,false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapterCompany.ViewHolder holder, int position) {
        holder.name.setText(listCompany.get(position).getName());
        holder.phone.setText(listCompany.get(position).getPhone());
        Glide.with(context).load(listCompany.get(position).getImagen()).into(holder.imagen);
        holder.country.setText(listCompany.get(position).getCountry());
        holder.address.setText(listCompany.get(position).getAddress());
        holder.estado.setText(listCompany.get(position).getEstado());
        holder.link.setText(listCompany.get(position).getLink());
        holder.idcompany.setText(listCompany.get(position).getId());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(context, SecondSuplierActivity.class);
                String idCompany = holder.idcompany.getText().toString();
                GlobalUsuario.idCompany = Integer.parseInt(idCompany);
                context.startActivity(start);
            }
        });

    }

    @Override
    public int getItemCount() {
            return listCompany.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name,phone,country,address,link, estado, idcompany, order;
        public RoundedImageView imagen;
        public MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_nameCompany);
            phone = (TextView) itemView.findViewById(R.id.item_phoneCompany);
            country = (TextView) itemView.findViewById(R.id.item_countryCompany);
            address = (TextView) itemView.findViewById(R.id.item_addressCompany);
            idcompany = (TextView) itemView.findViewById(R.id.item_idCompany);
            link = (TextView) itemView.findViewById(R.id.item_linkCompany);
            estado = (TextView) itemView.findViewById(R.id.item_statusCompany);
            imagen = (RoundedImageView) itemView.findViewById(R.id.item_imageCompany);
            cardView = (MaterialCardView) itemView.findViewById(R.id.cardview_businness_company);


        }

    }


}
