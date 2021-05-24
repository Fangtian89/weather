package com.example.sunnyweatherkt.Util

import android.location.LocationManager

object MapUtils {
    fun isGPSProviderAvailable(locationManager:LocationManager):Boolean{
        val list=locationManager.getProviders(true)
        return list.contains(LocationManager.GPS_PROVIDER)
    }

    fun isWIFIProviderAvailable(locationManager: LocationManager):Boolean{
        val list=locationManager.getProviders(true)
        return list.contains(LocationManager.NETWORK_PROVIDER)
    }
}