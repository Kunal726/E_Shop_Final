package com.sunnytech.task.shop_owner

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sunnytech.task.shop_owner.databinding.FragmentOwnerLoginBinding
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.HashMap

class Owner_Login : Fragment() {
    private lateinit var binding: FragmentOwnerLoginBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentOwnerLoginBinding.bind(inflater.inflate(R.layout.fragment_owner_login, container, false))
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(SharedPrefmanager.getInstance(requireContext().applicationContext).isLoggedIN)
        {
            startActivity(Intent((activity as Shop_Owner), Shop::class.java))

        }

        binding.textSignup.setOnClickListener {
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frag_container, Owner_Sign_In())
                fragmentTransaction.commit()

        }
        binding.btnLogin.setOnClickListener{ Login() }
    }

    private fun Login() {
        val uname = binding.usernameInput.text.toString().trim()
        val pass = binding.pass.text.toString().trim()
        if(uname == "" && pass == "")
            Toast.makeText(requireContext(), "Please Enter Username and Password", Toast.LENGTH_LONG).show()
        else if(uname == "")
            Toast.makeText(requireContext(), "Please Enter Username", Toast.LENGTH_LONG).show()
        else if (pass == "")
            Toast.makeText(requireContext(), "Please Enter Password", Toast.LENGTH_LONG).show()
        else
        {
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Logging In .......")
            progressDialog.show()

            val stringRequest = @SuppressLint("SimpleDateFormat")
            object : StringRequest(
                Method.POST, Constants.url_login_owner,
                Response.Listener { s ->
                    progressDialog.dismiss()
                    try {
                        val obj = JSONObject(s)
                        if(!obj.getBoolean("error")) {
                            SharedPrefmanager.getInstance(requireContext().applicationContext)
                                .userLogin(
                                    obj.getInt("id"),
                                    obj.getString("name"),
                                    obj.getString("email"),
                                    obj.getString("shop"),
                                    obj.getString("date"),
                                    obj.getString("status"),
                                    obj.getString("category"),
                                    obj.getString("service")
                                )
                            Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_LONG).show()

                            if (SharedPrefmanager.getInstance(requireContext().applicationContext).keyStatus != "ACTIVE")
                            {
                                parentFragmentManager.beginTransaction().replace(R.id.frag_container, ChargesFragment()).commit()
                            } else
                                startActivity(Intent((activity as Shop_Owner), Shop::class.java))

                        } else {
                            Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    } catch (e : JSONException) {
                        e.stackTrace
                    }

                },
                Response.ErrorListener { e ->
                    progressDialog.hide()
                  // this.string = "fail"
                    try {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    } catch (e : Exception)
                    {
                        Toast.makeText(context, "Please Check Internet connectivity", Toast.LENGTH_LONG).show()
                    }
                }){
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["uname"] = uname
                    params["pass"] = pass
                    return params
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)

        }
    }
}