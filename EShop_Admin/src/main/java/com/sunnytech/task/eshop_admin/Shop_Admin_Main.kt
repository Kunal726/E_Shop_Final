package com.sunnytech.task.eshop_admin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class Shop_Admin_Main : AppCompatActivity(), Shop_Info {
    private lateinit var ShopList : ArrayList<Shop_Registered_Model>
    private lateinit var shop_Registered_Adapter : Shop_Registered_Adapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_admin_main)

        ShopList = ArrayList()
        shop_Registered_Adapter = Shop_Registered_Adapter(this@Shop_Admin_Main, ShopList, this)
        recyclerView = findViewById(R.id.shop_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = shop_Registered_Adapter

        getRegisteredShops()
    }

    private fun getRegisteredShops() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading .......")
        progressDialog.show()

        val stringRequest = @SuppressLint("NotifyDataSetChanged")
        object : StringRequest(
            Method.POST,
            Constants.url_shop_name_list,
            Response.Listener { s ->
                progressDialog.dismiss()
                try {
                    val array = JSONArray(s)

                    for (i in 0 until array.length()) {
                        val shop = array.getJSONObject(i)
                        ShopList.add(
                            Shop_Registered_Model(
                                shop.getString("name"),
                                shop.getString("id"),
                                shop.getString("cat"),
                        )
                        )
                    }
                    shop_Registered_Adapter.notifyDataSetChanged()

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
                params["adminid"] = SharedPrefManager.getInstance(this@Shop_Admin_Main.applicationContext).keyId
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> {

                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Logout")
                dialog.setMessage("Do you really want to Logout? ")
                dialog.setIcon(R.drawable.ic_logout)
                dialog.setNegativeButton("Cancel", null)
                dialog.setPositiveButton("Ok") { _: DialogInterface, _: Int ->
                    SharedPrefManager.getInstance(this.applicationContext).logout()
                    startActivity(Intent(this, Shop_Admin::class.java))
                }
                dialog.create()
                dialog.show()
                return true
            }
            R.id.refresh -> {
                startActivity(Intent(this, Shop_Admin_Main::class.java))
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onItemClicked(id: String, cat:String) {
        startActivity(Intent(this@Shop_Admin_Main, ShopDetails::class.java).putExtra("id", id).putExtra("cat", cat))
    }
}