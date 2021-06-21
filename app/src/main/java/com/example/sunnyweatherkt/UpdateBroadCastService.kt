package com.example.sunnyweatherkt.com.example.sunnyweatherkt
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.Util.Utility
import java.text.SimpleDateFormat


class UpdateBroadCastService:BroadcastReceiver() {
    val Channel_1_ID="channel1"
    var address:String?=null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context!=null){
            address=Utility.getCurrentLocation(context)
            if(address!=null){
                val channel1 = NotificationChannel(                                                         //创建 channel
                    Channel_1_ID,
                    "channel_111",
                    NotificationManager.IMPORTANCE_HIGH
                )

                channel1.description = "this is channel 1"
                channel1.enableLights(true)
                channel1.lightColor = Color.GREEN
                val manager = context!!.getSystemService(
                    NotificationManager::class.java
                )
                manager.createNotificationChannel(channel1)

                val notification = NotificationCompat.Builder(context!!, Channel_1_ID)                                                                                           //创建 notification
                    .setSmallIcon(R.drawable.ic_baseline_message)
                    .setContentText("this is a message "+SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                    .setContentTitle(address)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.drawable.ic_clear_day))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setChannelId(Channel_1_ID)
                    //            .setColor(Color.BLUE) //icon color
                    //.setVibrate(new long[]{0,1000,1000,1000,1000})
                    //.setLights(Color.RED,1000,1000)
                    .build()

                val vi = context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator // Vibration
                vi.vibrate(150)
                manager.notify(1, notification)
            }
        }
    }
}