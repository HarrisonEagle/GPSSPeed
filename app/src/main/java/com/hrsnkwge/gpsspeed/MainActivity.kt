package com.hrsnkwge.gpsspeed

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hrsnkwge.gpsspeed.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        supportFragmentManager.beginTransaction().add(R.id.container,FirstFragment()).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
        }else{
            supportFragmentManager.beginTransaction().add(R.id.container,FirstFragment()).commit()
        }
        binding.homebtn.setOnClickListener {
            binding.homeicon.setImageResource(R.drawable.home_light)
            binding.listicon.setImageResource(R.drawable.list_dark)
            binding.hometext.setTextColor(Color.parseColor("#4c4c4c"))
            binding.listtext.setTextColor(Color.parseColor("#ffffff"))
            binding.homebtn.setBackgroundColor(Color.parseColor("#4c4c4c"))
            binding.listbtn.setBackgroundColor(Color.parseColor("#ffffff"))
            binding.homebtn.setBackgroundColor(Color.parseColor("#ffffff"))
            binding.listbtn.setBackgroundColor(Color.parseColor("#4c4c4c"))
            supportFragmentManager.beginTransaction().replace(R.id.container,FirstFragment()).commit()
        }
        binding.listbtn.setOnClickListener {
            binding.homeicon.setImageResource(R.drawable.home_dark)
            binding.listicon.setImageResource(R.drawable.list_light)
            binding.hometext.setTextColor(Color.parseColor("#ffffff"))
            binding.listtext.setTextColor(Color.parseColor("#4c4c4c"))
            binding.homebtn.setBackgroundColor(Color.parseColor("#4c4c4c"))
            binding.listbtn.setBackgroundColor(Color.parseColor("#ffffff"))
            supportFragmentManager.beginTransaction().replace(R.id.container,SecondFragment()).commit()
        }


    }


}