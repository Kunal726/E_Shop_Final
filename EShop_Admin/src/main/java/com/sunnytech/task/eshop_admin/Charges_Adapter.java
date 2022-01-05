package com.sunnytech.task.eshop_admin;

import android.content.Context;
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

public class Charges_Adapter extends RecyclerView.Adapter<Charges_Adapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Charges_Model> charges_models;
    private charge_info chargeInfo;

    public Charges_Adapter(Context context, ArrayList<Charges_Model> charges_models, charge_info chargeInfo) {
        this.context = context;
        this.charges_models = charges_models;
        this.chargeInfo = chargeInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.charges_list, parent, false);
        return new Charges_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(charges_models != null && charges_models.size() > 0)
        {
            Charges_Model charges_model = charges_models.get(position);
            holder.date.setText("Date : " + charges_model.getDate());
            holder.amount.setText("Amount : " + charges_model.getAmt());
            Glide.with(context).load(charges_model.getImg()).into(holder.imageView);
            holder.cardView.setOnClickListener(view -> {
                chargeInfo.OnClickCharges(charges_model.getImg());
            });
        }
    }

    @Override
    public int getItemCount() {
        return charges_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date, amount;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.charge_image);
            date = itemView.findViewById(R.id.pay_date);
            amount = itemView.findViewById(R.id.pay_amount);
            cardView = itemView.findViewById(R.id.charge_row);
        }
    }
}
