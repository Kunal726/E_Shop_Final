package com.sunnytech.task.shop_owner

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Handler


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val i = Intent(this@SplashScreen, Shop_Owner::class.java)
            startActivity(i)
        }, 3000)
    }

    override fun onRestart() {
        super.onRestart()
        finishAffinity()
    }


}