package com.sunnytech.task.shop_owner;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<OrderModel> orderModels;
    private OrderListAdapter orderListAdapter;

    public OrderAdapter(Context context, ArrayList<OrderModel> orderModels) {
        this.context = context;
        this.orderModels = orderModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_order, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(orderModels != null && orderModels.size() > 0)
        {
            OrderModel orderModel = orderModels.get(position);
            holder.orderid.setText("Order Id : " + orderModel.getId());
            holder.orderaddress.setText("Delivery Address : " + orderModel.getAddr());
            holder.ordertotal.setText("Total : " + orderModel.getTotal());
            holder.orderdiscount.setText("Discount : " + orderModel.getDiscount());
            holder.finaltotal.setText("Final Total : " + orderModel.getFinaltotal());
            holder.status.setText("Order Status : " + orderModel.getStatus());
            holder.tax.setText("Gst/Tax : " + orderModel.getTax());
            holder.orderlist.setLayoutManager(new LinearLayoutManager(context));
            orderListAdapter = new OrderListAdapter(context, orderModel.getOrderListModels());
            holder.orderlist.setAdapter(orderListAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderid, orderaddress, ordertotal, orderdiscount, finaltotal, status, tax;
        RecyclerView orderlist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.text_order_id);
            orderaddress = itemView.findViewById(R.id.text_order_addr);
            ordertotal = itemView.findViewById(R.id.text_order_total);
            orderdiscount = itemView.findViewById(R.id.text_order_discount);
            finaltotal = itemView.findViewById(R.id.text_order_final_total);
            status = itemView.findViewById(R.id.text_order_status);
            orderlist = itemView.findViewById(R.id.order_list);
            tax = itemView.findViewById(R.id.text_tax);
        }
    }
}
