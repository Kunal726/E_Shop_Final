package com.sunnytech.task.eshop_admin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private final ArrayList<ServiceModel> serviceModels;
    private final Context context;

    public ServiceAdapter(Context context, ArrayList<ServiceModel> serviceModels) {
        this.context = context;
        this.serviceModels = serviceModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_service, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(serviceModels != null && serviceModels.size() > 0)
        {
            ServiceModel serviceModel = serviceModels.get(position);
            holder.id.setText("Order Id : " + serviceModel.getOrderId());
            holder.addr.setText("Service Address : " + serviceModel.getAddress());
            holder.status.setText("Service Status : " + serviceModel.getStatus());
            holder.time.setText("Service time : " + serviceModel.getTime());
            holder.price.setText("Price : " + serviceModel.getPrice());
            holder.tax.setText("Tax/Gst : " + serviceModel.getTax());
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            ServiceListAdapter serviceListAdapter = new ServiceListAdapter(context, serviceModel.getServiceListModels());
            holder.recyclerView.setAdapter(serviceListAdapter);

        }
    }

    @Override
    public int getItemCount() {
        return serviceModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, addr, status, time, price, tax;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.text_service_id);
            addr = itemView.findViewById(R.id.text_service_addr);
            status = itemView.findViewById(R.id.text_service_status);
            time = itemView.findViewById(R.id.text_service_time);
            price = itemView.findViewById(R.id.text_service_final_total);
            recyclerView = itemView.findViewById(R.id.cause_list);
            tax = itemView.findViewById(R.id.text_service_tax);
        }
    }
}
