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
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sunnytech.task.shop_owner.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONException

class HomeFragment : Fragment(), Product_Info {
    private lateinit var productmodelArrayList : ArrayList<Product_Model>
    private lateinit var productAdapter: Product_Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentHomeBinding
    private var Root : View ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        Root = binding.root
        return Root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        (activity as Shop).findViewById<FloatingActionButton>(R.id.add_product).visibility = View.VISIBLE
        productmodelArrayList = ArrayList()
        recyclerView = binding.productsList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = Product_Adapter(requireContext(), productmodelArrayList, this)
        recyclerView.adapter = productAdapter
        binding.shopNameDisplay.text = SharedPrefmanager.getInstance(requireContext().applicationContext).keyShopName

        val str = SharedPrefmanager.getInstance(requireContext().applicationContext).keyShopCat
        if(str == "Electrician" || str ==  "Doctor" || str ==  "Plumber" || str ==  "Clinic") {
            "Services".also { binding.productText.text = it }
        }
        else
            "Products".also { binding.productText.text = it }

        ShowProducts()
    }

    private fun ShowProducts() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait Fetching data.......")
        progressDialog.show()

        val stringRequest =
            @SuppressLint("NotifyDataSetChanged")
            object : StringRequest(
                Method.POST, Constants.prd_list,
                Response.Listener { s ->
                    progressDialog.dismiss()
                    try {
                        val array = JSONArray(s)
                        for (i in 0 until array.length()) {
                            val product = array.getJSONObject(i)
                            val image = Constants.IMAGE_ROOT_URL + product.getString("image")
                            productmodelArrayList.add(
                                Product_Model(
                                    image,
                                    product.getString("name"),
                                    product.getString("price"),
                                    product.getString("qty"),
                                    product.getInt("id").toString()
                                )
                            )
                        }
                        productAdapter.notifyDataSetChanged()

                    } catch (e : JSONException) {
                        e.printStackTrace()
                    }

                },
                Response.ErrorListener { e ->
                    progressDialog.hide()
                    try {
                        Toast.makeText(context, "Please Check Internet connectivity", Toast.LENGTH_LONG).show()
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

    override fun onItemClicked(productModel: Product_Model, position: Int) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_shop, SingleProductFragment(productmodelArrayList, position))
        fragmentTransaction.addToBackStack(null)
        (activity as Shop).findViewById<FloatingActionButton>(R.id.add_product).visibility = View.INVISIBLE
        fragmentTransaction.commit()
    }
}