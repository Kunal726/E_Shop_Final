package com.sunnytech.task.eshop_admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

@SuppressWarnings("ALL")
public class NoConnection {

    public boolean isConnected(@NonNull Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());
    }

    public void ShowInternetDialog(Context context, @NonNull Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.no_internet_connected, activity.findViewById(R.id.no_internet_layout));
        view.findViewById(R.id.txt_try_again).setOnClickListener(view1 -> {
            if(! isConnected(activity))
                ShowInternetDialog(context, activity);
            else
                activity.startActivity(new Intent(activity.getApplicationContext(), activity.getClass()));
        });

        builder.setView(view);

        builder.create().show();
    }
}
