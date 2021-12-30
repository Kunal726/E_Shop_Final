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
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.sunnytech.task.shop_owner.databinding.FragmentSingleProductBinding
import org.json.JSONArray
import org.json.JSONException


class SingleProductFragment(private var productmodelarraylist : ArrayList<Product_Model>,
                            private var position: Int
) : Fragment() {
    private lateinit var binding: FragmentSingleProductBinding
    private var Root: View? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSingleProductBinding.bind(inflater.inflate(R.layout.fragment_single_product, container, false))
        Root = binding.root
        return Root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.isEnabled = false
        binding.btnSave.visibility = View.INVISIBLE

        Glide.with(requireContext()).load(productmodelarraylist[position].image).into(binding.singlePrdImage)
        binding.singleName.setText (productmodelarraylist[position].prdname)
        binding.singlePrice.setText (productmodelarraylist[position].price)
        binding.singleQty.setText (productmodelarraylist[position].qty)

        binding.singleName.isEnabled = false
        binding.singlePrice.isEnabled = false
        binding.singleQty.isEnabled = false

        binding.btnEdit.setOnClickListener{
            binding.btnSave.isEnabled = true
            binding.btnSave.visibility = View.VISIBLE

            binding.btnEdit.isEnabled = false
            binding.btnEdit.visibility = View.INVISIBLE

            binding.singleName.isEnabled = true
            binding.singlePrice.isEnabled = true
            binding.singleQty.isEnabled = true
            
        }

        binding.btnSave.setOnClickListener{
            binding.btnSave.isEnabled = false
            binding.btnSave.visibility = View.INVISIBLE

            binding.btnEdit.isEnabled = true
            binding.btnEdit.visibility = View.VISIBLE

            binding.singleName.isEnabled= false
            binding.singlePrice.isEnabled= false
            binding.singleQty.isEnabled= false

            val id = productmodelarraylist[position].id.toString()
            val name = binding.singleName.text.toString()
            val price = binding.singlePrice.text.toString()
            val qty = binding.singleQty.text.toString()

            SaveData(id, name, price, qty)

        }

    }

    private fun SaveData(id: String, name: String, price: String, qty: String) {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait Saving data.......")
        progressDialog.show()

        val stringRequest =
            object : StringRequest(
                Method.POST, Constants.prd_update,
                Response.Listener {
                    progressDialog.dismiss()

                    Toast.makeText(requireContext(), "Product Updated Successfully", Toast.LENGTH_LONG).show()
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
                    params["id"] = id
                    params["name"] = name
                    params["price"] = price
                    params["qty"] = qty
                    return params
                }
            }

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)

    }


}