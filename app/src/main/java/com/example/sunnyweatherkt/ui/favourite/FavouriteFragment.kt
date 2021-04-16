package com.example.sunnyweatherkt.ui.favourite

import android.os.Build
import android.os.Bundle
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
import com.example.sunnyweatherkt.MyApplication
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.Sky
import com.example.sunnyweatherkt.logic.model.getSky
import com.example.sunnyweatherkt.ui.place.PlaceAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.favouritelayout.*
import kotlinx.android.synthetic.main.favouritelayout.view.*
import java.net.ResponseCache

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


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val placeList=favouriteViewModeliew.readFavouritePlace()                                    //从SharedPreferences 获取地址list
        var list:MutableMap<String,PlaceResponsing.Place> = mutableMapOf()                                     //

        placeList.forEach { (t, u) ->                                                               //造一个新的list, key是城市名字,value是PlaceResponsing.Place
                list[t] = Gson().fromJson(u,PlaceResponsing.Place::class.java)
        }
        favouriteViewModeliew.refreshFavouriteWeather(list)                                         //激发改变
        favouriteViewModeliew.weatherLiveData.observe(viewLifecycleOwner){                          //观察变化
            result->
                val weatherResult=result.getOrNull()
                if(weatherResult!=null){
                    //displayFavourite(weatherResult)
                    val layoutManager=LinearLayoutManager(activity)
                    recyclerView.layoutManager=layoutManager
                    adapter=FavouriteAdapter(this,weatherResult)
                    recyclerView.adapter=adapter
                }
        }
    }

}





