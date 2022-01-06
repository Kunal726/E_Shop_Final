package com.sunnytech.task.eshop_admin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONException

class Charges : AppCompatActivity(), charge_info {
    private var shopid = ""
    private lateinit var chargesAdapter : Charges_Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var chargesmodel : ArrayList<Charges_Model>
    private lateinit var image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charges)

        shopid = intent.getStringExtra("id").toString()

        chargesmodel = ArrayList()
        chargesAdapter = Charges_Adapter(this@Charges, chargesmodel, this)
        recyclerView = findViewById(R.id.charges_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chargesAdapter

        image = findViewById(R.id.pay_img)

        GetCharges()
    }

    private fun GetCharges() {
        if (!NoConnection().isConnected(this))
            NoConnection().ShowInternetDialog(this, this)
        else {
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Loading .......")
            progressDialog.show()

            val stringRequest = @SuppressLint("NotifyDataSetChanged")
            object : StringRequest(
                Method.POST,
                Constants.url_shop_charges,
                Response.Listener { s ->
                    progressDialog.dismiss()
                    try {
                        val array = JSONArray(s)

                        for (i in 0 until array.length()) {
                            val shop = array.getJSONObject(i)

                            if (i == 0)
                                Glide.with(this)
                                    .load(Constants.IMAGE_ROOT_URL + shop.getString("img"))
                                    .into(image)


                            chargesmodel.add(
                                Charges_Model(
                                    shop.getString("img"),
                                    shop.getString("date"),
                                    shop.getString("amt")
                                )
                            )
                        }
                        chargesAdapter.notifyDataSetChanged()

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
                    params["sid"] = shopid
                    return params
                }
            }

            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(stringRequest)
        }
    }

    override fun OnClickCharges(img: String) {
        Glide.with(this).load(img).into(image)
    }
}