package com.sunnytech.task.shop_owner


import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle

import android.widget.ArrayAdapter
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sunnytech.task.shop_owner.databinding.FragmentOwnerDataBinding

class Owner_Data : Fragment() {
    private lateinit var binding: FragmentOwnerDataBinding
    private var Root: View? = null
    private var ad: ArrayAdapter<String>? = null
    private val Shop_Type = arrayOf("Select Category", "Restaurant", "Grocery", "Medical", "Repair Services", "Clothing", "Electronic")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentOwnerDataBinding.bind(inflater.inflate(R.layout.fragment_owner__data, container, false))
        Root = binding.root

        return Root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ad = ArrayAdapter(requireContext(), R.layout.category_list, R.id.cat_list, Shop_Type)
        binding.shopCategory.adapter = ad
        binding.btnDone.setOnClickListener{AddData()}
    }

    fun AddData() {
        val shopName = binding.shopNameInput.text.toString()
        val shopAddr = binding.shopAddrInput.text.toString()
        val landmark = binding.shopLandmarkInput.text.toString()
        val city = binding.shopCityInput.text.toString()
        val fromCity = binding.shopFromCityInput.text.toString()
        val toCity = binding.shopToCityInput.text.toString()
        val shopCat = binding.shopCategory.selectedItem.toString()
        val delServ = binding.root.findViewById<RadioButton>(binding.radioDelivery.checkedRadioButtonId).text.toString().get(0).toString()


        if (!(shopName == "" && shopAddr == "" && landmark == "" && city == "" && fromCity == "" && toCity == "" && shopCat == "" && delServ =="")) {
            val regUser = Register_User(requireContext())
            if(regUser.setShopData(shopName, shopCat, shopAddr, landmark, city, fromCity, toCity, delServ) == 0) {
                Toast.makeText(requireContext(), "Data Saved", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Data Not Saved", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireContext(), "Please Fill The Required Fields", Toast.LENGTH_LONG).show()
        }

    }

}