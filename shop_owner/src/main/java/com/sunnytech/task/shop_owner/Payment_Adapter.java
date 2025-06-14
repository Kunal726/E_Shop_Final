package com.sunnytech.task.shop_owner;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        if (payment_models != null && payment_models.size() > 0) {
            Payment_Model payment_model = payment_models.get(position);
            holder.pid.setText("Payment ID : " + payment_model.getPaymentID());
            holder.oid.setText("Order ID : " + payment_model.getOrderID());
            holder.pmode.setText("Payment Mode : " + payment_model.getPaymentMode());
            holder.price.setText("Payment Amount : " + payment_model.getPaymentAmount());
            holder.pstatus.setText("Payment Status : " + payment_model.getPaymentStatus());
            if (payment_model.getPaymentMode().equalsIgnoreCase("cash") && payment_model.getPaymentStatus().equalsIgnoreCase("pending")) {
                holder.appCompatButton.setVisibility(View.VISIBLE);
            }
            else
                holder.appCompatButton.setVisibility(View.GONE);
            holder.appCompatButton.setOnClickListener(view -> {
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait Updating Status .......");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        Constants.url_change_pay_status,
                        response -> {
                            progressDialog.dismiss();
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            holder.appCompatButton.setVisibility(View.GONE);
                            holder.pstatus.setText("Payment Status : COMPLETED");
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
                        params.put("payid",payment_model.getPaymentID());
                        params.put("paystatus","C");
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
        return payment_models.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        TextView pid, oid, pmode, price, pstatus;
        AppCompatButton appCompatButton;
        public ViewModel(@NonNull View itemView) {
            super(itemView);
            pid = itemView.findViewById(R.id.text_payment_id);
            oid = itemView.findViewById(R.id.text_payment_order_id);
            pmode = itemView.findViewById(R.id.text_payment_mode);
            pstatus = itemView.findViewById(R.id.text_payment_status);
            price = itemView.findViewById(R.id.text_payment_amount);
            appCompatButton = itemView.findViewById(R.id.btn_recieved);
        }
    }
}
