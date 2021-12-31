package com.sunnytech.task.shop_owner;

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
        return new ServiceAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(serviceModels != null && serviceModels.size() > 0)
        {
            ServiceModel serviceModel = serviceModels.get(position);
            holder.id.setText("Order Id : " + serviceModel.getOrderId());
            holder.addr.setText("Service Address : " + serviceModel.getAddress());
            if(SharedPrefmanager.getInstance(context.getApplicationContext()).getKeyDelService().equalsIgnoreCase("n"))
                holder.addr.setVisibility(View.GONE);
            else
                holder.addr.setVisibility(View.VISIBLE);
            holder.status.setText("Service Status : " + serviceModel.getStatus());
            holder.time.setText("Service time : " + serviceModel.getTime());
            holder.price.setText("Price : " + serviceModel.getPrice());
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            ServiceListAdapter serviceListAdapter = new ServiceListAdapter(context, serviceModel.getServiceListModels());
            holder.recyclerView.setAdapter(serviceListAdapter);
            if(serviceModel.getStatus().equalsIgnoreCase("pending"))
                holder.appCompatButton.setVisibility(View.VISIBLE);
            else
                holder.appCompatButton.setVisibility(View.GONE);

            holder.appCompatButton.setOnClickListener(view -> {
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait Updating Status .......");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        Constants.url_change_order_status,
                        response -> {
                            progressDialog.dismiss();
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            holder.appCompatButton.setVisibility(View.GONE);
                            holder.status.setText("Service Status : COMPLETED");
                        },
                        error -> {
                            progressDialog.hide();
                            try {
                                Toast.makeText(context, "Please Check Internet connectivity", Toast.LENGTH_LONG).show();
                            } catch (Exception exception)
                            {
                                Toast.makeText(context, "Please Check Internet connectivity", Toast.LENGTH_LONG).show();
                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap();
                        params.put("oid",serviceModel.getOrderId());
                        params.put("ostatus","C");
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);

            });
        }
    }

    @Override
    public int getItemCount() {
        return serviceModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, addr, status, time, price;
        RecyclerView recyclerView;
        AppCompatButton appCompatButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.text_service_id);
            addr = itemView.findViewById(R.id.text_service_addr);
            status = itemView.findViewById(R.id.text_service_status);
            time = itemView.findViewById(R.id.text_service_time);
            price = itemView.findViewById(R.id.text_service_final_total);
            recyclerView = itemView.findViewById(R.id.cause_list);
            appCompatButton = itemView.findViewById(R.id.btn_complete);
        }
    }
}
