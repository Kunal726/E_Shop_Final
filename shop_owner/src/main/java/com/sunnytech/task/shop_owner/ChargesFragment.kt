package com.sunnytech.task.shop_owner

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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
import com.sunnytech.task.shop_owner.databinding.FragmentChargesBinding
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File


class ChargesFragment() : Fragment() {
    private lateinit var binding: FragmentChargesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChargesBinding.bind(inflater.inflate(R.layout.fragment_charges, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSend.visibility = View.INVISIBLE

        binding.imgViewCharges.setOnClickListener{
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
        }

        binding.btnSend.setOnClickListener{
            val path = (activity as Shop_Owner).path
            val progressDialog = ProgressDialog(requireContext())
            progressDialog.setMessage("Sending data... \n Please Wait..")
            progressDialog.setCancelable(false)
            progressDialog.max = 100
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)

            progressDialog.show()

            val file = File(path.path)
            AndroidNetworking.upload(Constants.send_payment)
                .addMultipartFile("image", file)
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
                        progressDialog.dismiss()
                        Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT).show()
                        if(response != "Error") {
                            binding.imgViewCharges.setImageResource(R.drawable.upl_img)
                            binding.btnSend.visibility = View.INVISIBLE
                            (activity as Shop_Owner).appcharge = 1

                            SharedPrefmanager.getInstance(requireContext().applicationContext).logout()
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.frag_container, Owner_Login()).commit()

                        }
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