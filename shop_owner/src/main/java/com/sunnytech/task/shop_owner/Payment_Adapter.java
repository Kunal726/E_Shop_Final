package com.sunnytech.task.shop_owner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Payment_Adapter extends RecyclerView.Adapter<Payment_Adapter.ViewModel> {

    private final Context context;
    private final ArrayList<Payment_Model> payment_models;

    public Payment_Adapter(Context context, ArrayList<Payment_Model> payment_models) {
        this.context = context;
        this.payment_models = payment_models;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_payment_list, parent, false);
        return new Payment_Adapter.ViewModel(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        if(payment_models != null && payment_models.size() > 0)
        {
            Payment_Model payment_model = payment_models.get(position);
            holder.pid.setText("Payment ID : " + payment_model.getPaymentID());
            holder.oid.setText("Order ID : " + payment_model.getOrderID());
            holder.pmode.setText("Payment Mode : " + payment_model.getPaymentMode());
            holder.price.setText("Payment Amount : " + payment_model.getPaymentAmount());
            holder.pstatus.setText("Payment Status : " + payment_model.getPaymentStatus());
        }

    }

    @Override
    public int getItemCount() {
        return payment_models.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        TextView pid, oid, pmode, price, pstatus;
        public ViewModel(@NonNull View itemView) {
            super(itemView);
            pid = itemView.findViewById(R.id.text_payment_id);
            oid = itemView.findViewById(R.id.text_payment_order_id);
            pmode = itemView.findViewById(R.id.text_payment_mode);
            pstatus = itemView.findViewById(R.id.text_payment_status);
            price = itemView.findViewById(R.id.text_payment_amount);
        }
    }
}
