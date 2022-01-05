package com.sunnytech.task.eshop_admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ServiceListModel> serviceListModels;

    public ServiceListAdapter(Context context, ArrayList<ServiceListModel> serviceListModels) {
        this.context = context;
        this.serviceListModels = serviceListModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_service_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(serviceListModels != null && serviceListModels.size() > 0) {
            ServiceListModel serviceListModel = serviceListModels.get(position);
            holder.serName.setText(serviceListModel.getCause());
            holder.serPrice.setText(serviceListModel.getPrice());
        }

    }

    @Override
    public int getItemCount() {
        return serviceListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView serName, serPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serName = itemView.findViewById(R.id.order_service_name);
            serPrice = itemView.findViewById(R.id.order_service_price);
        }
    }
}
