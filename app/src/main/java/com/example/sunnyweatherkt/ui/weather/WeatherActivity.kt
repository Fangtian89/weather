package com.example.sunnyweatherkt.ui.weather

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunnyweatherkt.MyApplication
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.Util.Utility
import com.example.sunnyweatherkt.Util.showToastLg
import com.example.sunnyweatherkt.Util.startActivity
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.Weather
import com.example.sunnyweatherkt.logic.model.getSky
import com.example.sunnyweatherkt.ui.favourite.FavouriteActivity
import com.example.sunnyweatherkt.ui.favourite.FavouriteViewModel
import com.example.sunnyweatherkt.ui.place.PlaceFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.hour.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity(){

    val weatherViewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    val favouriteViewModeliew by lazy { ViewModelProvider(this).get(FavouriteViewModel::class.java) }
    var n: PlaceResponsing.Place? = null
    val TAG="WeatherResult"
    val TAG2="LocationInfo"
    lateinit var mLocationManager:LocationManager
    lateinit var dialog:AlertDialog


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        Utility.setProgressDialog(this)

        var weatherResult: Weather?=null
        //------------------------------------------------------------------------------------------
        val decorView = window.decorView                                                                              //????????????????????????
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        //------------------------------------------------------------------------------------------
        //Log.d(TAG, "onCreate: 1")

        if (weatherViewModel.locationLng.isEmpty()) {
            weatherViewModel.locationLng = intent.getStringExtra("location_lng")
                    ?: ""                                                                           //?????? intent.getStringExtra("location_lng") ????????????????????????null
        }

        if (weatherViewModel.locationLat.isEmpty()) {
            weatherViewModel.locationLat = intent.getStringExtra("location_lat")
                    ?: ""                                                                           //?????????null
        }

        if (weatherViewModel.placeName.isEmpty()) {
            weatherViewModel.placeName = intent.getStringExtra("place_name")
                    ?: ""                                                                           //?????????null
        }


        weatherViewModel.refreshWeather(weatherViewModel.locationLng, weatherViewModel.locationLat)      //??????viewModel????????????
        weatherViewModel.weatherLiveData.observe(this, Observer {                               //??????livedata ??????
            result ->
            weatherResult = result.getOrNull()                                                      //getOrNull ???????????????????????????????????? 0
            if (weatherResult != null) {
                showWeatherInfo(weatherResult!!)
                Utility.dismissDialog()
            } else {
                "????????????????????????!???".showToastLg()
                result.exceptionOrNull()?.printStackTrace()
                println(result.exceptionOrNull()?.message + "!!!")
            }
            swipeRefresh.isRefreshing = false                                                             //???????????????
        })

        swipeRefresh.setColorSchemeResources(R.color.design_default_color_on_secondary)                 //????????????
        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }


        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {                           //drawerLayout ????????? ,?????????!!!

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })

        fab.setOnClickListener {
            val placeInGson = Gson().fromJson(intent.getStringExtra("place"), PlaceResponsing.Place::class.java)       //?????????????????????,???????????????
            if(weatherResult!=null){
                Repository.saveFavouritePlace(placeInGson, weatherResult!!)
                nav_list.visibility=View.VISIBLE
            }
        }
        nav_button.setOnClickListener {                                                                  // navigation ?????????????????? fragment
            drawerLayout.openDrawer(GravityCompat.START)                                                //???????????????????????????drawerLayout,??????placeFragment,?????????placeFragment ???????????? weatherActivity?????????????????????
            val fragmentManager=supportFragmentManager
            val transaction=fragmentManager.beginTransaction()
            val fragment=PlaceFragment()
            transaction.add(R.id.frag,fragment)
            transaction.commit()
        }

        nav_list.setOnClickListener {
            //val fragment=FavouriteFragment()                                                          //????????????????????????????????????
            //supportFragmentManager.beginTransaction().add(R.id.drawerLayout,FavouriteFragment::class.java.newInstance()).addToBackStack(null).commit()
            startActivity<FavouriteActivity>(this) {}
        }
                                                                                                    //????????????PlaceFragment
        if(Repository.readFavouritePlace().isEmpty()){                                              //??????????????????,??????????????? Repository
            nav_list.visibility=View.GONE
        }else
            nav_list.visibility=View.VISIBLE
    }

    override fun onRestart() {
        super.onRestart()
        if (Repository.readFavouritePlace().isEmpty()){
            nav_list.visibility=View.GONE
        }
    }

    fun refreshWeather() {
        weatherViewModel.refreshWeather(weatherViewModel.locationLng, weatherViewModel.locationLat)      //????????????,?????????
        swipeRefresh.isRefreshing = true                                                                  //???????????????
    }




    fun showWeatherInfo(result: Weather) {                                                                //??????weather

        placeName.text = weatherViewModel.placeName
        val realTimeWeather = result.realTime                                                         //??????????????????
        val dailyWeather = result.dailyResponse                                                       //??????????????????
        val hourlyWeather=result.hourlyResponse                                                       //??????????????????


//---------------------------------------------realTimeWeather--------------------------------------
        val temp=realTimeWeather.temperature
        currentTemp.text = String.format("%.1f",temp) + "??C"
        currentSky.text = realTimeWeather.skycon
        val pm25 = realTimeWeather.air_quality.aqi.chn.toString()
        currentAQI.text = pm25
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm")
        val formatted = formatter.format(date)
        upateTime.text = "last update: $formatted"

        nowLayout.setBackgroundResource(getSky(realTimeWeather.skycon).bg)

        //nowLayout.setBackgroundResource(R.mipmap.bg_clear_day)



//---------------------------------------------hourlyWeather----------------------------------------
        if(hourlyWeather!=null){
            val layoutManager=LinearLayoutManager(this)
            layoutManager.orientation=LinearLayoutManager.HORIZONTAL
            val hourlyAdapter=HourlyAdapter(hourlyWeather)
            recyclerView.adapter = hourlyAdapter
            recyclerView.layoutManager=layoutManager
        }

//---------------------------------------------dailyWeather-----------------------------------------

        forecastLayout.removeAllViews()                                                             //parentview removeall + parentview.addview
        val days = dailyWeather.skycon.size
        for (i in 0 until days) {

            val skycon = dailyWeather.skycon[i]
            val temperature = dailyWeather.temperature[i]

            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)     //????????????xml ?????? view, ???remove view, ????????? addView
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView                                                       //????????????
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView

            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("y.M.dd H:m ZZZZ", Locale.getDefault())

            dateInfo.text = simpleDateFormat.format(skycon.date)



            val sky = getSky(skycon.value)                                                           //??????key??? ?????? sky ??? value ??? class Sky(val info:String,val icon:Int,val bg:Int)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()}~${temperature.max.toInt()}??C"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        val lifeIndex = dailyWeather.life_index
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }


}

