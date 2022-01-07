package com.sunnytech.task.shop_owner


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.theartofdev.edmodo.cropper.CropImage
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Shop_Owner : AppCompatActivity() {
    lateinit var path: Uri
    var dat = ""
    var appcharge = 0

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_owner)

        if (intent.hasExtra("chrg"))
        {
            supportFragmentManager.beginTransaction().replace(R.id.frag_container, ChargesFragment()).commit()
        } else {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            if (SharedPrefmanager.getInstance(applicationContext).isLoggedIN) {

                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Loading........")
                progressDialog.show()

                val stringRequest = @SuppressLint("SimpleDateFormat")
                object : StringRequest(
                    Method.POST, Constants.url_refresh,
                    Response.Listener { s ->
                        progressDialog.dismiss()
                        try {
                            val obj = JSONObject(s)
                            if(!obj.getBoolean("error")) {
                                SharedPrefmanager.getInstance(this.applicationContext)
                                    .userLogin(
                                        obj.getInt("id"),
                                        obj.getString("name"),
                                        obj.getString("email"),
                                        obj.getString("shop"),
                                        obj.getString("date"),
                                        obj.getString("status"),
                                        obj.getString("category"),
                                        obj.getString("service"),
                                        obj.getString("monthly_charges")
                                    )

                                if (SharedPrefmanager.getInstance(applicationContext).keyStatus != "ACTIVE")
                                {
                                    supportFragmentManager.beginTransaction().replace(R.id.frag_container, ChargesFragment()).commit()
                                } else {
                                    startActivity(Intent(this, Shop::class.java))
                                }

                            }
                        } catch (e : JSONException) {
                            e.stackTrace
                        }

                    },
                    Response.ErrorListener { e ->
                        progressDialog.dismiss()
                        Toast.makeText(this, "Error. Please Try Again ", Toast.LENGTH_SHORT).show()
                        finishAffinity()
                    }){
                    override fun getParams(): MutableMap<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["owner"] = SharedPrefmanager.getInstance(this@Shop_Owner.applicationContext).keyId.toString()
                        return params
                    }
                }
                val requestQueue = Volley.newRequestQueue(this)
                requestQueue.add(stringRequest)

            }
            else
                fragmentTransaction.replace(R.id.frag_container, Owner_Login())

            fragmentTransaction.commit()
        }



    }

    override fun onRestart() {
        super.onRestart()
        finishAffinity()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                path = resultUri
                val imageView = findViewById<ImageView>(R.id.imgViewCharges)
                imageView.setImageDrawable(
                    Drawable.createFromStream(
                        contentResolver.openInputStream(
                            resultUri
                        ), null
                    )
                )
                findViewById<AppCompatButton>(R.id.btnSend).visibility = View.VISIBLE
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                var error = result.error
            }
        }
    }
}