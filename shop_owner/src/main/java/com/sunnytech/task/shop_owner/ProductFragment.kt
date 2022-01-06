package com.sunnytech.task.shop_owner

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sunnytech.task.shop_owner.databinding.FragmentProductBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File


class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private var Root : View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.bind(inflater.inflate(R.layout.fragment_product, container, false))
        Root = binding.root

        AndroidNetworking.initialize(requireContext().applicationContext)

        return Root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.EditPrdName.setText("")
        binding.EditPrdPrice.setText("")
        binding.EditPrdQty.setText("")
        val str = SharedPrefmanager.getInstance(requireContext().applicationContext).keyShopCat
        if(str == "Electrician" || str ==  "Doctor" || str ==  "Plumber" || str ==  "Clinic" || str == "Restaurant"){
            binding.EditPrdQty.setText("0")
            binding.EditPrdQty.visibility = View.GONE
        } else {
            binding.EditPrdQty.setText("")
            binding.EditPrdQty.visibility = View.VISIBLE
        }

        binding.imageView.setOnClickListener{
            if(ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                Dexter.withContext(requireContext())
                    .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                            CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(requireActivity())
                        }

                        override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                            if (p0 != null) {
                                if(p0.isPermanentlyDenied) {
                                    val alertbuilder = android.app.AlertDialog.Builder(requireContext())
                                    alertbuilder.setTitle("Permission Required")
                                        .setMessage("Permission required to access your device storage to pick image. \n Please Enable in Settings")
                                        .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                                            val intent = Intent()
                                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                            intent.data = Uri.fromParts("package", requireActivity().packageName, null)
                                            startActivityForResult(intent, 51)
                                        }
                                        .setNegativeButton("Cancel", null)
                                        .show()
                                }
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            p0: PermissionRequest?,
                            p1: PermissionToken?
                        ) {
                            p1?.continuePermissionRequest()
                        }

                    }).check()
            } else {
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(requireActivity())
            }

            binding.btnAdd.setOnClickListener{
                val path = (activity as Shop).path
                val file = File(path.path)
                val name = binding.EditPrdName.text.toString()
                val price = binding.EditPrdPrice.text.toString()
                val qty = binding.EditPrdQty.text.toString()

                val progressDialog = ProgressDialog(requireContext())
                progressDialog.setMessage("Adding Product. Please Wait..")
                progressDialog.setCancelable(false)
                progressDialog.max = 100
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)

                progressDialog.show()
                AndroidNetworking.upload(Constants.url_prod_det)
                    .addMultipartFile("image", file)
                    .addMultipartParameter("name", name)
                    .addMultipartParameter("qty", qty)
                    .addMultipartParameter("price", price)
                    .addMultipartParameter("shop",SharedPrefmanager.getInstance(requireContext().applicationContext).keyShopName)
                    .addMultipartParameter("owner",SharedPrefmanager.getInstance(requireContext().applicationContext).keyId.toString())
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener{bytesUploaded, totalBytes ->
                        val progress: Float = (bytesUploaded / totalBytes * 100).toFloat()
                        progressDialog.progress = progress.toInt()
                    }
                    .getAsString(object: StringRequestListener {
                        override fun onResponse(response: String?) {
                            if (response != null) {
                                Log.i("mytag",response)
                            }
                            progressDialog.dismiss()
                            Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT).show()
                            binding.EditPrdName.setText("")
                            binding.EditPrdPrice.setText("")
                            binding.EditPrdQty.setText("")
                            binding.imageView.setImageResource(R.drawable.upl_img)
                        }

                        override fun onError(anError: ANError?) {
                            progressDialog.dismiss()
                            anError?.stackTrace
                            Toast.makeText(requireContext()
                                ,"Error occured", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

    }
}