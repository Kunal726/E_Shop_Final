package com.sunnytech.task.eshop_admin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class Bill : AppCompatActivity() {
    private var shopid = ""
    private var category = ""
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderModels: ArrayList<OrderModel>
    private lateinit var serviceAdapter: ServiceAdapter
    private lateinit var serviceModels: ArrayList<ServiceModel>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        shopid = intent.getStringExtra("id").toString()
        category = intent.getStringExtra("cat").toString()
        recyclerView = findViewById(R.id.orders)
        recyclerView.layoutManager = LinearLayoutManager(this@Bill)
        orderModels = ArrayList()
        serviceModels = ArrayList()
        orderAdapter = OrderAdapter(this, orderModels)
        serviceAdapter = ServiceAdapter(this, serviceModels)

        if(category == "Electrician" || category ==  "Doctor" || category ==  "Plumber" || category ==  "Clinic")
        {
            recyclerView.adapter = serviceAdapter
            Log.e("ser","ok")
        }
        else {
            recyclerView.adapter = orderAdapter
        }

        getBill()
    }

    private fun getBill() {
        if (!NoConnection().isConnected(this@Bill))
            NoConnection().ShowInternetDialog(this@Bill, this@Bill)
        else {
            if(!(category == "Electrician" || category ==  "Doctor" || category ==  "Plumber" || category ==  "Clinic")) {

                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Please Wait Fetching data.......")
                progressDialog.show()

                val stringRequest =
                    @SuppressLint("NotifyDataSetChanged")
                    object : StringRequest(
                        Method.POST, Constants.prd_order,
                        Response.Listener { s ->
                            progressDialog.dismiss()
                            try {
                                val array = JSONArray(s)
                                for (i in 0 until array.length()) {
                                    val orderListModels = ArrayList<OrderListModel>()
                                    val order = array.getJSONObject(i)

                                    val orderlist = order.getJSONArray("list")
                                    for (j in 0 until orderlist.length()) {
                                        val list = orderlist.getJSONObject(j)
                                        orderListModels.add(
                                            OrderListModel(
                                                list.getString("pname"),
                                                list.getString("pqty"),
                                                list.getString("pprice")
                                            )
                                        )
                                    }

                                    orderModels.add(
                                        OrderModel(
                                            order.getString("id"),
                                            order.getString("address"),
                                            order.getString("total"),
                                            order.getString("discount"),
                                            order.getString("finaltotal"),
                                            order.getString("status"),
                                            orderListModels,
                                            order.getString("tax")
                                        )
                                    )

                                }
                                orderAdapter.notifyDataSetChanged()

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                        },
                        Response.ErrorListener { e ->
                            progressDialog.hide()
                            try {
                                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                                Log.e("Error", e.printStackTrace().toString())
                            } catch (e: Exception) {
                                Toast.makeText(
                                    this,
                                    "Please Check Internet connectivity",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val params: MutableMap<String, String> = HashMap()
                            params["sid"] = shopid
                            return params
                        }
                    }

                val requestQueue = Volley.newRequestQueue(this)
                requestQueue.add(stringRequest)
            } else {
                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Please Wait Fetching data.......")
                progressDialog.show()

                val stringRequest =
                    @SuppressLint("NotifyDataSetChanged")
                    object : StringRequest(
                        Method.POST, Constants.srv_order,
                        Response.Listener { s ->
                            progressDialog.dismiss()
                            try {
                                val array = JSONArray(s)
                                for (i in 0 until array.length()) {
                                    val serviceListModels = ArrayList<ServiceListModel>()
                                    val service = array.getJSONObject(i)

                                    val servicelist = service.getJSONArray("list")
                                    for (j in 0 until servicelist.length()) {
                                        val list = servicelist.getJSONObject(j)
                                        serviceListModels.add(
                                            ServiceListModel(
                                                list.getString("sname"),
                                                list.getString("sprice")
                                            )
                                        )
                                    }

                                    serviceModels.add(
                                        ServiceModel(
                                            service.getString("id"),
                                            service.getString("time"),
                                            service.getString("addr"),
                                            service.getString("status"),
                                            serviceListModels,
                                            service.getString("price"),
                                            service.getString("tax")
                                        )
                                    )

                                }
                                serviceAdapter.notifyDataSetChanged()

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                        },
                        Response.ErrorListener { e ->
                            progressDialog.hide()
                            try {
                                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                                Log.e("Error", e.printStackTrace().toString())
                            } catch (e: Exception) {
                                Toast.makeText(
                                    this,
                                    "Please Check Internet connectivity",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }) {
                        override fun getParams(): MutableMap<String, String> {
                            val params: MutableMap<String, String> = HashMap()
                            params["sid"] = shopid
                            return params
                        }
                    }

                val requestQueue = Volley.newRequestQueue(this)
                requestQueue.add(stringRequest)
            }
        }
    }

}