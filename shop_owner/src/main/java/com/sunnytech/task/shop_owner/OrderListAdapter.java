package com.sunnytech.task.shop_owner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<OrderListModel> orderListModels;

    public OrderListAdapter(Context context, ArrayList<OrderListModel> orderListModels) {
        this.context = context;
        this.orderListModels = orderListModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_order_list, parent, false);
        return new OrderListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(orderListModels != null && orderListModels.size() > 0) {
            OrderListModel orderListModel = orderListModels.get(position);
            holder.prdName.setText(orderListModel.getPname());
            holder.prdPrice.setText(orderListModel.getPprice());
            holder.prdQty.setText(orderListModel.getPqty());
        }

    }

    @Override
    public int getItemCount() {
        return orderListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView prdName, prdQty, prdPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prdName = itemView.findViewById(R.id.order_product_name);
            prdPrice = itemView.findViewById(R.id.order_product_price);
            prdQty = itemView.findViewById(R.id.order_product_qty);
        }
    }
}
