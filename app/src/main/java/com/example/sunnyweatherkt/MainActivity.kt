package com.example.sunnyweatherkt

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sunnyweatherkt.Util.MapUtils
import com.example.sunnyweatherkt.Util.showToastLg
//import com.example.sunnyweatherkt.Util.*
import com.example.sunnyweatherkt.Util.showToastSt

import com.example.sunnyweatherkt.Util.test
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.network.ServiceCreator
import com.example.sunnyweatherkt.ui.favourite.MyService
import com.example.sunnyweatherkt.ui.place.MyObserver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.lang.Exception
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity(){
//    lateinit var locationManager: LocationManager
    val TAG = "WeatherResult"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //静态加载PlaceFragment
        val intentService = Intent(this, MyService::class.java)
        Log.d(TAG, "onCreate: start Service")
        startService(intentService)
        //        locationManager=applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager                    //做一个 locationmanager 对象
        //可以返回都有哪些provider,或像下面直接指定 , val provider=locationManager.getProviders(true)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            return
        }
    }
}




