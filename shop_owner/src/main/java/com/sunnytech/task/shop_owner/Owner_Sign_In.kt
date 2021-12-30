package com.sunnytech.task.shop_owner

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.app.ProgressDialog
import android.widget.EditText
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sunnytech.task.shop_owner.databinding.FragmentOwnerSignInBinding

class Owner_Sign_In : Fragment() {

    private lateinit var binding: FragmentOwnerSignInBinding

    var Root: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentOwnerSignInBinding.bind(inflater.inflate(R.layout.fragment_owner__sign__in, container, false))
        Root = binding.root
        return Root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener { RegisterOwner() }
        binding.textLogin.setOnClickListener { val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frag_container, Owner_Login())
            fragmentTransaction.commit() }
    }

    fun RegisterOwner()
    {
        val name = binding.userNameInput.text.toString()
        val user = binding.userEmailInput.text.toString()
        val pass = binding.userPassInput.text.toString()
        val rpass = binding.userReenterPassInput.text.toString()

        if (name != "" && user != "" && pass != "" && rpass != "") {
            if (pass == rpass)
            {
                val owner = Register_User(requireContext())
                if(owner.Register_Owner(user, pass, name) == 0) {
                    val fragmentTransaction = parentFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frag_container, Owner_Data())
                    fragmentTransaction.commit()
                } else {
                    Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(requireContext(), "Passwords Do Not Match", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(requireContext(), "Required Fields Empty!!!", Toast.LENGTH_LONG).show()
        }
    }

}