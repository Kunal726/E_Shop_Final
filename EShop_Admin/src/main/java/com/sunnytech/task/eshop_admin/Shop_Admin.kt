package com.sunnytech.task.eshop_admin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Shop_Admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_admin)

        if(! NoConnection().isConnected(this@Shop_Admin))
            NoConnection().ShowInternetDialog(this, this@Shop_Admin)
        else {
            if(SharedPrefManager.getInstance(this.applicationContext).isLoggedIN)
                startActivity(Intent(this@Shop_Admin, Shop_Admin_Main::class.java))
            
            findViewById<AppCompatButton>(R.id.btn_login).setOnClickListener{
                val uname = findViewById<EditText>(R.id.username_input).text.toString()
                val pass = findViewById<EditText>(R.id.pass).text.toString()

                if(uname != "") {
                    if(pass != "") {
                        if(! NoConnection().isConnected(this@Shop_Admin))
                            NoConnection().ShowInternetDialog(this@Shop_Admin, this@Shop_Admin)
                        else {
                            val progressDialog = ProgressDialog(this)
                            progressDialog.setMessage("Logging In .......")
                            progressDialog.show()

                            val stringRequest = object : StringRequest(
                                Method.POST,
                                Constants.url_login,
                                Response.Listener { s ->
                                    progressDialog.dismiss()
                                    try {
                                        val obj = JSONObject(s)
                                        SharedPrefManager.getInstance(this.applicationContext).adminLogin(obj.getString("id"))
                                        Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this@Shop_Admin, Shop_Admin_Main::class.java))
                                    } catch (e: JSONException)
                                    {
                                        e.printStackTrace()
                                    }
                                },
                                Response.ErrorListener { e ->
                                    progressDialog.hide()
                                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                                }) {
                                override fun getParams(): MutableMap<String, String> {
                                    val params: MutableMap<String, String> = HashMap()
                                    params["uname"] = uname
                                    params["pass"] = pass
                                    return params
                                }
                            }

                            val requestQueue = Volley.newRequestQueue(this)
                            requestQueue.add(stringRequest)
                        }

                    } else
                        Toast.makeText(this, "Please Enter Password!!!", Toast.LENGTH_LONG).show()
                } else
                    Toast.makeText(this, "Please Enter User Name!!!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}