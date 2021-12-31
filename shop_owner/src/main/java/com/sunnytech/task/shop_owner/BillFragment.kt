package com.sunnytech.task.shop_owner

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonArray
import com.sunnytech.task.shop_owner.databinding.FragmentBillBinding
import org.json.JSONArray
import org.json.JSONException


class BillFragment : Fragment() {
    private lateinit var billBinding: FragmentBillBinding
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderModels: ArrayList<OrderModel>
    private lateinit var serviceAdapter: ServiceAdapter
    private lateinit var serviceModels: ArrayList<ServiceModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        billBinding = FragmentBillBinding.bind(inflater.inflate(R.layout.fragment_bill, container, false))
        return billBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        billBinding.orders.layoutManager = LinearLayoutManager(requireContext())
        orderModels = ArrayList()
        serviceModels = ArrayList()
        orderAdapter = OrderAdapter(context, orderModels)
        serviceAdapter = ServiceAdapter(context, serviceModels)

        var str = SharedPrefmanager.getInstance(requireContext().applicationContext).keyShopCat
        if(str == "Electrician" || str ==  "Doctor" || str ==  "Plumber" || str ==  "Clinic")
        {
            billBinding.orders.adapter = serviceAdapter
            Log.e("ser","ok")
        }
        else {
            billBinding.orders.adapter = orderAdapter
        }

        getOrders()

    }

    private fun getOrders() {
        var str = SharedPrefmanager.getInstance(requireContext().applicationContext).keyShopCat
        if(!(str == "Electrician" || str ==  "Doctor" || str ==  "Plumber" || str ==  "Clinic")) {

            val progressDialog = ProgressDialog(requireContext())
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
                                        orderListModels
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
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            Log.e("Error", e.printStackTrace().toString())
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Please Check Internet connectivity",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["shopName"] =
                            SharedPrefmanager.getInstance(requireContext().applicationContext).keyShopName
                        params["owner"] =
                            SharedPrefmanager.getInstance(requireContext().applicationContext).keyId.toString()
                        return params
                    }
                }

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        } else {
            val progressDialog = ProgressDialog(requireContext())
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
                                        service.getString("price")
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
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            Log.e("Error", e.printStackTrace().toString())
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Please Check Internet connectivity",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["shopName"] =
                            SharedPrefmanager.getInstance(requireContext().applicationContext).keyShopName
                        params["owner"] =
                            SharedPrefmanager.getInstance(requireContext().applicationContext).keyId.toString()
                        return params
                    }
                }

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }

}