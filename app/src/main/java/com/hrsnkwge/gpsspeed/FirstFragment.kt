package com.hrsnkwge.gpsspeed

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.provider.Contacts
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hrsnkwge.gpsspeed.databinding.FragmentFirstBinding
import kotlinx.coroutines.*
import java.io.File
import java.lang.Thread.sleep

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), LocationListener {

    private lateinit var locationManager: LocationManager
    private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var file: File
    private var speed = 0.0f
    private var time = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        locationStart()
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root
        if(Constants.isrecording){
            binding.recordbtn.text = "STOP"
            binding.recordbtn.background.setTint(Color.parseColor("#ff0000"))
        }
        binding.recordbtn.setOnClickListener {
            if(Constants.isrecording){
                binding.recordbtn.text = "START"
                binding.recordbtn.background.setTint(Color.parseColor("#a4c639"))
                Constants.isrecording = false
            }else{

                val filename = EditText(requireContext())
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("レコード名を入力してください")
                dialog.setView(filename)
                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                    // OKボタン押したときの処理
                    if(filename.text.length>0){
                        val path = requireContext().getExternalFilesDir(null)
                        val folder = File(path,"")
                        folder.mkdirs()
                        Log.d("Path:",folder.absolutePath)
                        file = File(folder, filename.text.toString()+".csv")
                        if(!file.exists())file.createNewFile()
                        binding.recordbtn.text = "STOP"
                        binding.recordbtn.background.setTint(Color.parseColor("#ff0000"))
                        Constants.isrecording = true
                        GlobalScope.launch { // launch new coroutine in background and continue
                            while(Constants.isrecording){
                                if(System.currentTimeMillis()-time>1024){
                                    speed = 0.0f
                                    GlobalScope.launch(Dispatchers.Main) {
                                        binding.textviewFirst.setText("%,.1f".format(speed))
                                    }
                                }
                                file.appendText("%,.1f".format(speed)+"\n")
                                delay(1000L)
                            }
                        }
                    }else{
                        val toast = Toast.makeText(requireContext(),"ファイルの作成に失敗しました",Toast.LENGTH_LONG)
                        toast.show()
                    }

                })
                dialog.setNegativeButton("キャンセル", null)
                dialog.show()


            }

        }
        return view
    }

    private fun locationStart() {
        Log.d("debug", "locationStart()")

        // Instances of LocationManager class must be obtained using Context.getSystemService(Class)
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("debug", "location manager Enabled")
        } else {
            // to prompt setting up GPS
            val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(settingsIntent)
            Log.d("debug", "not gpsEnable, startActivity")
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000
            )

            Log.d("debug", "checkSelfPermission false")
            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            100,
            0.5f,
            this
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onLocationChanged(location: Location) {
        if(location.hasSpeed()){
            speed = location.speed*3.6f
            binding.textviewFirst.setText("%,.1f".format(speed))
            time = System.currentTimeMillis()
        }
    }
}