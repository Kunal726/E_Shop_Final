package com.sunnytech.task.eshop_admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Shop_Registered_Adapter extends RecyclerView.Adapter<Shop_Registered_Adapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Shop_Registered_Model> shop_registered_models;
    private Shop_Info shop_info;

    public Shop_Registered_Adapter(Context context, ArrayList<Shop_Registered_Model> shop_registered_models, Shop_Info info) {
        this.context = context;
        this.shop_registered_models = shop_registered_models;
        shop_info = info;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_lists, parent, false);
        return new Shop_Registered_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shop_Registered_Model registeredModel = shop_registered_models.get(position);
        holder.shop_name.setText(registeredModel.getShop_Name());
        holder.cardView.setOnClickListener(view -> {
            shop_info.onItemClicked(registeredModel.getShop_Id(), registeredModel.getShop_cat());
        });
    }

    @Override
    public int getItemCount() {
        return shop_registered_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView shop_name;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name);
            cardView = itemView.findViewById(R.id.shop);
        }
    }
}
