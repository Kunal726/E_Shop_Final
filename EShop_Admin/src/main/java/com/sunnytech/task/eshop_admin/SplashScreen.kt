package com.sunnytech.task.eshop_admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if(! NoConnection().isConnected(this@SplashScreen))
            NoConnection().ShowInternetDialog(this, this@SplashScreen)
        else {
            Handler().postDelayed({
                val i = Intent(this@SplashScreen, Shop_Admin::class.java)
                startActivity(i)
            }, 3000)
        }
    }
}