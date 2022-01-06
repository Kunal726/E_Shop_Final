package com.sunnytech.task.eshop_admin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ShopDetails() : AppCompatActivity() {

    private lateinit var id: String
    private lateinit var cat: String
    private lateinit var active_txt : TextView
    private lateinit var inactive_txt : TextView
    private var ownerid = 0
    private var gst = 0
    private var charge = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_details)

        id = intent.getStringExtra("id").toString()
        cat = intent.getStringExtra("cat").toString()

        active_txt = findViewById(R.id.activate)
        inactive_txt = findViewById(R.id.deactivate)
        
        LoadData()

        active_txt.setOnClickListener{Activate()}
        inactive_txt.setOnClickListener{Deactivate()}

        findViewById<AppCompatButton>(R.id.set_gst).setOnClickListener{setGST()}
        findViewById<AppCompatButton>(R.id.set_charges).setOnClickListener{setCharges()}
        findViewById<AppCompatButton>(R.id.charges).setOnClickListener{
            startActivity(Intent(this@ShopDetails, Charges::class.java).putExtra("id", id))
        }
        findViewById<AppCompatButton>(R.id.bill).setOnClickListener{
            startActivity(Intent(this@ShopDetails, Bill::class.java).putExtra("id", id).putExtra("cat", cat))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCharges() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this)
            .inflate(R.layout.charges_month, findViewById(R.id.linear_charge))
        builder.setView(view)
        builder.setNegativeButton("Cancel", null)
        builder.setPositiveButton("Ok") { _: DialogInterface, _: Int ->
            charge = view.findViewById<EditText>(R.id.setcharge).text.toString().toInt()
            findViewById<TextView>(R.id.charge).text = "Charges : " + charge
            UpdateCharge()
        }
        builder.create().show()
    }

    private fun UpdateCharge() {
        if (!NoConnection().isConnected(this@ShopDetails))
            NoConnection().ShowInternetDialog(this@ShopDetails, this@ShopDetails)
        else {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Setting Charges .......")
            progressDialog.show()

            val stringRequest = @SuppressLint("SetTextI18n")
            object : StringRequest(
                Method.POST,
                Constants.url_shop_charge,
                Response.Listener { s ->
                    progressDialog.dismiss()
                    Toast.makeText(this, s, Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { e ->
                    progressDialog.hide()
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["sid"] = id
                    params["charge"] = charge.toString()
                    return params
                }
            }

            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setGST() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this)
            .inflate(R.layout.gst_set, findViewById(R.id.linear_gst))
        builder.setView(view)
        builder.setNegativeButton("Cancel", null)
        builder.setPositiveButton("Ok") { _: DialogInterface, _: Int ->
            gst = view.findViewById<EditText>(R.id.setgst).text.toString().toInt()
            findViewById<TextView>(R.id.gst).text = "TAX/GST : " + gst
            UpdateGST()
        }
        builder.create().show()

    }

    private fun UpdateGST() {
        if (!NoConnection().isConnected(this@ShopDetails))
            NoConnection().ShowInternetDialog(this@ShopDetails, this@ShopDetails)
        else {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Setting Tax/GST .......")
            progressDialog.show()

            val stringRequest = @SuppressLint("SetTextI18n")
            object : StringRequest(
                Method.POST,
                Constants.url_shop_gst,
                Response.Listener { s ->
                    progressDialog.dismiss()
                    Toast.makeText(this, s, Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { e ->
                    progressDialog.hide()
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["sid"] = id
                    params["gst"] = gst.toString()
                    return params
                }
            }

            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }
    }

    private fun Deactivate() {
        if (!NoConnection().isConnected(this@ShopDetails))
            NoConnection().ShowInternetDialog(this@ShopDetails, this@ShopDetails)
        else {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Deactivating .......")
            progressDialog.show()

            val stringRequest = @SuppressLint("SetTextI18n")
            object : StringRequest(
                Method.POST,
                Constants.url_shop_deactivate,
                Response.Listener { s ->
                    progressDialog.dismiss()
                    Toast.makeText(this, s, Toast.LENGTH_LONG).show()
                    if (s == "Deactivated" ) {
                        active_txt.visibility = View.VISIBLE
                        inactive_txt.visibility = View.GONE
                        findViewById<TextView>(R.id.status).text = "Status : INACTIVE"
                    }

                },
                Response.ErrorListener { e ->
                    progressDialog.hide()
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["oid"] = ownerid.toString()
                    return params
                }
            }

            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }
    }

    private fun Activate() {
        if (!NoConnection().isConnected(this@ShopDetails))
            NoConnection().ShowInternetDialog(this@ShopDetails, this@ShopDetails)
        else {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Activating .......")
            progressDialog.show()

            val stringRequest = @SuppressLint("SetTextI18n")
            object : StringRequest(
                Method.POST,
                Constants.url_shop_activate,
                Response.Listener { s ->
                    progressDialog.dismiss()
                    Toast.makeText(this, s, Toast.LENGTH_LONG).show()
                    if (s == "Activated" ) {
                        inactive_txt.visibility = View.VISIBLE
                        active_txt.visibility = View.GONE
                        findViewById<TextView>(R.id.status).text = "Status : ACTIVE"
                    }

                },
                Response.ErrorListener { e ->
                    progressDialog.hide()
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["oid"] = ownerid.toString()
                    return params
                }
            }

            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }
    }

    private fun LoadData() {

        if (!NoConnection().isConnected(this@ShopDetails))
            NoConnection().ShowInternetDialog(this@ShopDetails, this@ShopDetails)
        else {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Loading Data .......")
            progressDialog.show()

            val stringRequest = @SuppressLint("SetTextI18n")
            object : StringRequest(
                Method.POST,
                Constants.url_shop_details,
                Response.Listener { s ->
                    progressDialog.dismiss()
                    try {
                        val obj = JSONObject(s)
                        if(! obj.getBoolean("error")) {
                            findViewById<TextView>(R.id.sname).text = obj.getString("sname")
                            findViewById<TextView>(R.id.oname).text =
                                "Owner : " + obj.getString("oname")
                            findViewById<TextView>(R.id.date).text =
                                "Date : " + obj.getString("date")
                            findViewById<TextView>(R.id.status).text =
                                "Status : " + obj.getString("status")
                            findViewById<TextView>(R.id.gst).text =
                                "TAX/GST : " + obj.getString("gst")
                            findViewById<TextView>(R.id.charge).text =
                                "Charges : " + obj.getString("charges")

                            ownerid = obj.getInt("oid")

                            if (obj.getString("status") == "ACTIVE") {
                                inactive_txt.visibility = View.VISIBLE
                                active_txt.visibility = View.GONE
                            } else {
                                active_txt.visibility = View.VISIBLE
                                inactive_txt.visibility = View.GONE
                            }
                        } else {
                            Toast.makeText(this, obj.getString("message"), Toast.LENGTH_LONG).show()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { e ->
                    progressDialog.hide()
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["shopid"] = id
                    return params
                }
            }

            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, Shop_Admin_Main::class.java))
    }
}