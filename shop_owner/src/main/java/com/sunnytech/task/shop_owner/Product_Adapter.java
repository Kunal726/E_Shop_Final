package com.sunnytech.task.shop_owner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.ViewHolder> {

    Context context;
    ArrayList<Product_Model> product_modelArrayList;
    Product_Info product_info;

    public Product_Adapter(Context context, ArrayList<Product_Model> product_modelArrayList, Product_Info product_info) {
        this.context = context;
        this.product_modelArrayList = product_modelArrayList;
        this.product_info = product_info;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(product_modelArrayList != null && product_modelArrayList.size() > 0)
        {
            Product_Model model = product_modelArrayList.get(position);
            holder.product_name.setText(model.getPrdname());
            String qty = "Qty = " + model.getQty();
            holder.product_qty.setText(qty);
            String price;
            String str = SharedPrefmanager.getInstance(context.getApplicationContext()).getKeyShopCat();
            if(str.equals("Electrician") || str.equals("Doctor") || str.equals("Plumber") || str.equals("Clinic")) {
                holder.product_qty.setVisibility(View.INVISIBLE);
                price = "Charges = " + model.getPrice() + " Rs.";
            } else {
                holder.product_qty.setVisibility(View.VISIBLE);
                price = "Price = " + model.getPrice() + " Rs.";
            }
            holder.product_price.setText(price);
            Glide.with(context).load(model.getImage()).into(holder.product_image);
            holder.cardView.setOnClickListener(view -> {
                product_info.onItemClicked(product_modelArrayList.get(position), position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return product_modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_name, product_price, product_qty;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name_list);
            product_price = itemView.findViewById(R.id.product_price_list);
            product_qty = itemView.findViewById(R.id.product_qty);
            cardView = itemView.findViewById(R.id.product_row);
        }
    }
}
