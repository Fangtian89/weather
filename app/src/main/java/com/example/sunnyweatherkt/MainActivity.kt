package com.example.sunnyweatherkt

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

    val TAG = "WeatherResult"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: "+MyApplication.context.resources.displayMetrics)
        val bitmap=BitmapFactory.decodeResource(MyApplication.context.resources,R.drawable.sky)
        Log.d(TAG, "onCreate: ${bitmap.byteCount} ${bitmap.width} ${bitmap.height}")

        Log.d(TAG, "onCreate: ....")

        //静态加载PlaceFragment
        val intentService = Intent(this, MyService::class.java)
        Log.d(TAG, "onCreate: start Service")
        startService(intentService)


    }

}




