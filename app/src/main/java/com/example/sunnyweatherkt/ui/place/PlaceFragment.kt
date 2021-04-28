package com.example.sunnyweatherkt.ui.place

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import com.example.sunnyweatherkt.MainActivity
import com.example.sunnyweatherkt.MyApplication
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.Util.showToastSt
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.ui.favourite.FavouriteAdapter
import com.example.sunnyweatherkt.ui.favourite.FavouriteViewModel
import com.example.sunnyweatherkt.ui.weather.WeatherActivity
import com.example.sunnyweatherkt.ui.weather.WeatherViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.favouritelayout.*
import kotlinx.android.synthetic.main.fragment_place.*
import kotlinx.android.synthetic.main.fragment_place.recyclerView
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PlaceFragment:Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }        //调用的时候 实例化一个viewModel
    val favouriteViewModeliew by lazy { ViewModelProvider(this).get(FavouriteViewModel::class.java) }
    val weatherViewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    lateinit var adapter: PlaceAdapter
    val TAG = "WeatherResult"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_place, container, false)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {                                   //两个重要方法
        super.onActivityCreated(savedInstanceState)                                                  //Fragment 里面放 RecyclerView

        //------------------------------------------------------------------------------------------
        if (activity is MainActivity && viewModel.isSavedPlace()) {                                   //PlaceFragment -> viewModel -> Repository -> Dao (SharedPreferences) 获取已经保存(访问)的地址
            val lifecycleOwner = this
            CoroutineScope(Dispatchers.IO).launch {                                                 //update favourite list
                updateFavouriteList(lifecycleOwner)
            }
            val place = viewModel.getSavedPlace()                                                     //要先判断一下是否有此保存的数据，然后拿出来
            val intent = Intent(context, WeatherActivity::class.java).apply {                         //跳珠进入 weatherActivity
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
                putExtra("place", Gson().toJson(place))
            }
            startActivity(intent)
            activity?.finish()                                                                      //关掉之前activity 或 fragment
            return                                                                                  //从数据库获得的数据，展示后直接退出，适用于不是第一次使用
        }

        //------------------------------------------------------------------------------------------

        val layoutManager = LinearLayoutManager(activity)                                       //加载 RecyclerView
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter
        //Log.d(TAG, "onActivityCreated_2: " + activity)

        searchPlaceEdit.addTextChangedListener { edit ->                                        //重要方法 addTextChangedListener
            val content = edit.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlacesViewModel(content)
                //Log.d(TAG, "onActivityCreated_4: " + activity)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places =
                    result.getOrNull()                                                              //getOrNull() 即非null 返回正差值，为null 时返回 null
            if (places != null) {
                //Log.d(TAG, "onActivityCreated_3: " + activity)
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                "places are not found".showToastSt()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    suspend fun updateFavouriteList(lifecycleOwner: LifecycleOwner) {
        val placeWeatherList =
                favouriteViewModeliew.readFavouritePlace()                                              //从SharedPreferences 获取地址,天气list
        var list = ArrayList<PlaceResponsing.Place>()

        placeWeatherList.forEach { (t, u) ->
            list.add(Gson().fromJson(t, PlaceResponsing.Place::class.java))                          //取出地址，放到Arraylist 中,  造一个新的list, key是城市名字,value是PlaceResponsing.Place
        }

//        val mHandler= object : Handler() {
//            override fun handleMessage(msg: Message) {
//
//            }
//        }


        list.forEach() {
            if (view != null) {                                                                     //getView 哪的View? fragment root 的 view
                favouriteViewModeliew.refreshFavouriteWeather(it.location.lng, it.location.lat)

                withContext(Dispatchers.Main){
                    favouriteViewModeliew.favouriteWeatherLiveData.observe(lifecycleOwner, Observer {    //更新sharedpreference 里的天气
                        result ->
                        val weatherResult = result.getOrNull()
                        Thread.sleep(1000)
                        Log.d(TAG, "updateFavouriteList: "+weatherResult)
                        if (weatherResult != null) {
                            favouriteViewModeliew.saveFavouritePlace(it, weatherResult)                   //保存地址和新天气情况在sharedPreferences
                        }
                    })
                }



            }
        }
    }
}