package com.sunnytech.task.shop_owner

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Register_User(context: Context) {
    var context: Context ?= null
    var string = ""
    init {
        this.context = context
    }

    fun Register_Owner(uname: String, pass: String, name: String): Int {
        var i = 0
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Registering Owner .......")
        progressDialog.show()

        val stringRequest = object : StringRequest(Method.POST, Constants.url_register,
        Response.Listener { s ->
            progressDialog.dismiss()
            i = 1
            Toast.makeText(context, s, Toast.LENGTH_LONG).show()

        },
        Response.ErrorListener { e ->
            progressDialog.hide()
            i = 0
            try {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            } catch (e : Exception)
            {
                Toast.makeText(context, "Please Check Internet connectivity", Toast.LENGTH_LONG).show()
            }
        }){
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["user"] = uname
                params["pass"] = pass
                params["name"] = name

                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
        return i
    }

    fun setShopData(name: String, category: String, add : String, landmark: String, city: String, fromCity: String, toCity: String, delServ: String) : Int {
        var i = 0
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Saving Data .......")
        progressDialog.show()

        val stringRequest = object : StringRequest(Method.POST, Constants.url_save,
                Response.Listener { s ->
                    progressDialog.dismiss()

                    Toast.makeText(context, s, Toast.LENGTH_LONG).show()
                    i = 1
                },
                Response.ErrorListener { e ->
                    progressDialog.hide()
                    i = 0
                    try {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    } catch (e : Exception)
                    {
                        Toast.makeText(context, "Please Check Internet connectivity", Toast.LENGTH_LONG).show()
                    }
                }){
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["name"] = name
                params["cat"] = category
                params["add"] = add
                params["land"] = landmark
                params["city"] = city
                params["fromCity"] = fromCity
                params["toCity"] = toCity
                params["delServ"] = delServ

                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
        return i
    }


}