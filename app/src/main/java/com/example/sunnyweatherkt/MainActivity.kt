package com.example.sunnyweatherkt

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatActivity
import com.example.sunnyweatherkt.ui.favourite.MyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(){

    val TAG = "WeatherResult"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)                                                      //静态加载PlaceFragment

//        Log.d(TAG, "onCreate: "+MyApplication.context.resources.displayMetrics)
//        val bitmap=BitmapFactory.decodeResource(MyApplication.context.resources,R.drawable.sky)
//        Log.d(TAG, "onCreate: ${bitmap.byteCount} ${bitmap.width} ${bitmap.height}")
//        Log.d(TAG, "onCreate: ....")


        val intentService = Intent(this, MyService::class.java)
        this.startService(intentService)

        val alarmManager=getSystemService(ALARM_SERVICE) as AlarmManager
        val intent= Intent("com.example.SunnyWeatherKT.updateBroadcastReceiver")
        intent.setPackage(packageName)
        val pendingIntent=PendingIntent.getBroadcast(MyApplication.context,0,intent,0)
//        val triggerTime=SystemClock.elapsedRealtime()+10*1000
        val calendar = Calendar.getInstance()
        Log.d(TAG, "1 onCreate: time in millis "+SimpleDateFormat("y.M.dd H:m ZZZZ").format(calendar.timeInMillis))
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY,7)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        Log.d(TAG, "2 onCreate: time in millis "+SimpleDateFormat("y.M.dd H:m ZZZZ").format(calendar.timeInMillis))
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent)
    }
}




