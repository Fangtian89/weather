package com.example.sunnyweatherkt.Util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.sunnyweatherkt.MyApplication
import java.util.*

object Utility {
    private lateinit var dialog:androidx.appcompat.app.AlertDialog
    fun setProgressDialog(activity:Activity){
        val llPadding = 30
        val ll = LinearLayout(activity)                                                             //做一个 linearLayout
        ll.apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(llPadding, llPadding, llPadding, llPadding)
            gravity = Gravity.CENTER }

        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(activity).apply {
            isIndeterminate = true
            setPadding(0, 0, llPadding, 0)
            layoutParams = llParam
        }                                                                                           //做一个 旋转的 progressBar

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(activity).apply {
            text = "Loading ..."
            setTextColor(Color.parseColor("#000000"))
            textSize = 20f
            layoutParams = llParam
        }                                                                                           //做一个 textView

        ll.addView(progressBar)                                                                     //把 progressBar 放到 linearLayout 上
        ll.addView(tvText)                                                                          //把 textView 放到 linearLayout 上
        val builder = androidx.appcompat.app.AlertDialog.Builder(activity).apply {
            setCancelable(false)
            setView(ll)
        }                                                                                           //做一个 AlertDialog 的 builder

        dialog = builder.create()                                                                   //产生 dialog
        dialog.show()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }
    fun dismissDialog(){
        dialog?.dismiss()
    }

    val tz = TimeZone.getDefault()
    @RequiresApi(Build.VERSION_CODES.N)
    val isDaylightTime = tz.observesDaylightTime()
    val timeZone = TimeZone.getDefault().getDisplayName(isDaylightTime, TimeZone.SHORT)


    var address:String?=null
    private lateinit var mLocationManager:LocationManager

    fun getCurrentLocation(context:Context):String? {
        if(ContextCompat.checkSelfPermission(
                context!!,

                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >=23){

            var location: Location?=null
            val mProviderName:String
            mLocationManager = context!!.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager                    //做一个 locationmanager 对象

            val isGpsEnabled = MapUtils.isGPSProviderAvailable(mLocationManager)
            val isWIFIEnabled= MapUtils.isWIFIProviderAvailable(mLocationManager)

            if (isGpsEnabled&&!isWIFIEnabled){
                mProviderName= LocationManager.GPS_PROVIDER
//                mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                location=mLocationManager.getLastKnownLocation(mProviderName)

                if(location!=null){
                    val lon=location.longitude
                    val lat=location.latitude
                    val geocoder = Geocoder(MyApplication.context, Locale.getDefault())
                    val list = geocoder.getFromLocation( location.latitude, location.longitude,1)
                    address = list[0].locality

                }else{
                    mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                }

            }else if (!isGpsEnabled && isWIFIEnabled){
                mProviderName= LocationManager.NETWORK_PROVIDER
//                mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                location=mLocationManager.getLastKnownLocation(mProviderName)
                if(location!=null){
                    val lon=location.longitude
                    val lat=location.latitude
                    val geocoder = Geocoder(MyApplication.context, Locale.getDefault())
                    val list = geocoder.getFromLocation( location.latitude, location.longitude,1)
                    address = list[0].locality

                }else{
                    mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                }

            }else if(isGpsEnabled && isWIFIEnabled) {
                mProviderName= LocationManager.GPS_PROVIDER
//                mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                location=mLocationManager.getLastKnownLocation(mProviderName)
//                mLocationManager.getCurrentLocation(mProviderName,)
                if(location!=null){
                    val lon=location.longitude
                    val lat=location.latitude
                    val geocoder = Geocoder(MyApplication.context, Locale.getDefault())
                    val list = geocoder.getFromLocation( location.latitude, location.longitude,1)
                    address = list[0].locality

                }else{
                    mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                }

            }else if (!isGpsEnabled && !isWIFIEnabled){
                "please turn on location".showToastSt()
            }
        }else if (Build.VERSION.SDK_INT<23){
            var location: Location?=null
            val mProviderName:String
            val mLocationManager = context!!.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager                    //做一个 locationmanager 对象

            val isGpsEnabled = MapUtils.isGPSProviderAvailable(mLocationManager)
            val isWIFIEnabled= MapUtils.isWIFIProviderAvailable(mLocationManager)

            if (isGpsEnabled&&!isWIFIEnabled){
                mProviderName= LocationManager.GPS_PROVIDER
//                mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                location=mLocationManager.getLastKnownLocation(mProviderName)

                if(location!=null){
                    val lon=location.longitude
                    val lat=location.latitude
                    val geocoder = Geocoder(MyApplication.context, Locale.getDefault())
                    val list = geocoder.getFromLocation( location.latitude, location.longitude,1)
                    address = list[0].locality

                }else{
                    mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                }

            }else if (!isGpsEnabled && isWIFIEnabled){
                mProviderName= LocationManager.NETWORK_PROVIDER
//                mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                location=mLocationManager.getLastKnownLocation(mProviderName)
                if(location!=null){
                    val lon=location.longitude
                    val lat=location.latitude
                    val geocoder = Geocoder(MyApplication.context, Locale.getDefault())
                    val list = geocoder.getFromLocation( location.latitude, location.longitude,1)
                    address = list[0].locality

                }else{
                    mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                }

            }else if(isGpsEnabled && isWIFIEnabled) {
                mProviderName= LocationManager.GPS_PROVIDER
//                mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                location=mLocationManager.getLastKnownLocation(mProviderName)
                if(location!=null){
                    val lon=location.longitude
                    val lat=location.latitude
                    val geocoder = Geocoder(MyApplication.context, Locale.getDefault())
                    val list = geocoder.getFromLocation( location.latitude, location.longitude,1)
                    address = list[0].locality

                }else{
                    mLocationManager.requestLocationUpdates(mProviderName,5000,100f,mLocationListener)
                }

            }else if (!isGpsEnabled && !isWIFIEnabled){
                "please turn on location".showToastSt()
            }
        }
        return address
    }

    val mLocationListener= object : LocationListener {

        override fun onLocationChanged(location: Location) {
            val lon=location.longitude
            val lat=location.latitude
            val geocoder = Geocoder(MyApplication.context, Locale.getDefault())
            val list = geocoder.getFromLocation( location.latitude, location.longitude,1)
            address = list[0].locality
//            showPlaces4CurrentLocation(address)
        }
    }

}