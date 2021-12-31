package com.sunnytech.task.shop_owner


import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.androidnetworking.AndroidNetworking
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.theartofdev.edmodo.cropper.CropImage

class Shop : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    lateinit var path: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        AndroidNetworking.initialize(this.applicationContext)
        bottomNavigation = findViewById(R.id.bottom_navigation_view)
        bottomNavigation.visibility = View.VISIBLE
        bottomNavigation.background = null
        bottomNavigation.setOnNavigationItemSelectedListener(navigationListener)
        bottomNavigation.selectedItemId = R.id.home
        findViewById<FloatingActionButton>(R.id.add_product).setOnClickListener{
            findViewById<FloatingActionButton>(R.id.add_product).visibility = View.INVISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_shop, ProductFragment()).commit()
        }
    }

    private val navigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
        when (item.itemId) {
            R.id.home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_shop, HomeFragment()).commit()
                findViewById<FloatingActionButton>(R.id.add_product).visibility = View.VISIBLE
            }
            R.id.bill -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_shop, BillFragment()).commit()
                findViewById<FloatingActionButton>(R.id.add_product).visibility = View.VISIBLE

                val str = SharedPrefmanager.getInstance(this.applicationContext).keyShopCat
                if(str == "Electrician" || str ==  "Doctor" || str ==  "Plumber" || str ==  "Clinic")
                    item.title = "Orders"
                else
                    item.title = "Billing"
            }
            R.id.payment -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_shop, PaymentFragment()).commit()
                findViewById<FloatingActionButton>(R.id.add_product).visibility = View.VISIBLE
            }R.id.logout -> {
                findViewById<FloatingActionButton>(R.id.add_product).visibility = View.VISIBLE
                AlertDialog.Builder(this)
                    .setTitle("LogOut")
                    .setMessage("Do You Really Want to LogOut ?")
                    .setIcon(R.drawable.ic_logout)
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
                        SharedPrefmanager.getInstance(this.applicationContext).logout()
                        startActivity(Intent(this, Shop_Owner::class.java))
                    }
                    .create()
                    .show()
                }
            }

        return@OnNavigationItemSelectedListener true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuexit -> {

                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Exit")
                dialog.setMessage("Do you want to Exit? ")
                dialog.setIcon(R.drawable.ic_exit)
                dialog.setNegativeButton("Cancel", null)
                dialog.setPositiveButton("Ok") { _: DialogInterface, _: Int ->
                    finishAffinity()
                }
                dialog.create()
                dialog.show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if(resultCode == RESULT_OK) {
                val resultUri = result.uri
                path = resultUri
                val imageView = findViewById<ImageView>(R.id.imageView)
                imageView.setImageDrawable(Drawable.createFromStream(contentResolver.openInputStream(resultUri), null))

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                var error = result.error
            }
        }
    }
}