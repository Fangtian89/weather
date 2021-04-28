package com.example.sunnyweatherkt.ui.favourite

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import com.example.sunnyweatherkt.MainActivity
import com.example.sunnyweatherkt.MyApplication
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.*
import com.example.sunnyweatherkt.ui.place.PlaceAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.favouritelayout.*
import kotlinx.android.synthetic.main.favouritelayout.view.*
import kotlinx.android.synthetic.main.now.*
import java.net.ResponseCache
import java.text.SimpleDateFormat
import java.util.*

class FavouriteFragment: Fragment() {
    val favouriteViewModeliew by lazy { ViewModelProvider(this).get(FavouriteViewModel::class.java) }
    lateinit var adapter: FavouriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.favouritelayout,container,false)
        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {                                   //只起到展示作用
        super.onActivityCreated(savedInstanceState)

        val date= Calendar.getInstance().time
        val formatter= SimpleDateFormat("YYYY/M/dd HH:mm")
        val formatted=formatter.format(date)
        updateTime.text=formatted

        val placeWeatherList=favouriteViewModeliew.readFavouritePlace()                             //从SharedPreferences 直接读取地址及天气list

            val weatherResult = mutableMapOf<PlaceResponsing.Place,RealTimeResponse.RealTime>()
            placeWeatherList.forEach(){                                                             //转换
                val placeInGson=Gson().fromJson(it.key,PlaceResponsing.Place::class.java)
                val weatherInGson=Gson().fromJson(it.value,Weather::class.java)
                val realTimeInGson=weatherInGson.realTime
                weatherResult[placeInGson] = realTimeInGson
            }

            val layoutManager=LinearLayoutManager(activity)                                         //展示到RecyclerView
            recyclerView.layoutManager=layoutManager
            if (weatherResult!=null){                                                               //确定 weatherResult 绝不为空，在Adapter 里 才敢保证用 !!
                adapter=FavouriteAdapter(this,weatherResult)
                recyclerView.adapter=adapter

            }
            adapter.notifyDataSetChanged()
    }
}





