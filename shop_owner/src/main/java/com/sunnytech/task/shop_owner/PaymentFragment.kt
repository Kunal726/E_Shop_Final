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
import com.sunnytech.task.shop_owner.databinding.FragmentPaymentBinding
import org.json.JSONArray
import org.json.JSONException

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var paymentAdapter: Payment_Adapter
    private lateinit var payment_models: ArrayList<Payment_Model>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.bind(inflater.inflate(R.layout.fragment_payment, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.payments.layoutManager = LinearLayoutManager(requireContext())
        payment_models = ArrayList()
        paymentAdapter = Payment_Adapter(requireContext(), payment_models)
        binding.payments.adapter = paymentAdapter
        
        getPayments()
    }

    private fun getPayments() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait Fetching data.......")
        progressDialog.show()

        val stringRequest =
            @SuppressLint("NotifyDataSetChanged")
            object : StringRequest(
                Method.POST, Constants.prd_payment,
                Response.Listener { s ->
                    progressDialog.dismiss()
                    try {
                        val array = JSONArray(s)
                        for (i in 0 until array.length()) {
                            val payment = array.getJSONObject(i)
                            payment_models.add(
                                Payment_Model(
                                    payment.getString("pid"),
                                    payment.getString("oid"),
                                    payment.getString("pmode"),
                                    payment.getString("pstatus"),
                                    payment.getString("pamt")
                                )
                            )
                        }
                        paymentAdapter.notifyDataSetChanged()

                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }

                },
                Response.ErrorListener { e ->
                    progressDialog.hide()
                    try {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        Log.e("Error", e.printStackTrace().toString())
                    } catch (e : Exception)
                    {
                        Toast.makeText(context, "Please Check Internet connectivity", Toast.LENGTH_LONG).show()
                    }
                }){
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["shopName"] = SharedPrefmanager.getInstance(requireContext().applicationContext).keyShopName
                    params["owner"] = SharedPrefmanager.getInstance(requireContext().applicationContext).keyId.toString()
                    return params
                }
            }

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)

    }


}