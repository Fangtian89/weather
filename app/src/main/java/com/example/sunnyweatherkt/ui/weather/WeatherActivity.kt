package com.example.sunnyweatherkt.ui.weather

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.Util.showToastLg
import com.example.sunnyweatherkt.logic.model.Weather
import com.example.sunnyweatherkt.logic.model.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.forecast_item.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    val weatherViewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java)}
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        //------------------------------------------------------------------------------------------
        val decorView=window.decorView                                                                              //状态栏和背景融合
        decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor= Color.TRANSPARENT
        //------------------------------------------------------------------------------------------


        if (weatherViewModel.locationLng.isEmpty()){
            weatherViewModel.locationLng=intent.getStringExtra("location_lng")?:""              //判断 intent.getStringExtra("location_lng") 是否为空，为空给null
        }

        if(weatherViewModel.locationLat.isEmpty()){
            weatherViewModel.locationLat=intent.getStringExtra("location_lat")?:""              //为空给null
        }

        if(weatherViewModel.placeName.isEmpty()){
            weatherViewModel.placeName=intent.getStringExtra("place_name")?:""                  //为空给null
        }

        weatherViewModel.refreshWeather(weatherViewModel.locationLng,weatherViewModel.locationLat)      //触发viewModel内部变化

        weatherViewModel.weatherLiveData.observe(this, Observer {                               //观察livedata 变化
            result ->
            val weatherResult=result.getOrNull()                                                        //getOrNull 一种防空方法，若为空则给 0
            if(weatherResult!=null){
                showWeatherInfo(weatherResult)
            }else{
                "无法获取天气信息!！".showToastLg()
                result.exceptionOrNull()?.printStackTrace()
                println(result.exceptionOrNull()?.message+"!!!")
            }
            swipeRefresh.isRefreshing=false                                                             //隐藏进度条
        })
        //weatherViewModel.refreshWeather(weatherViewModel.locationLng,weatherViewModel.locationLat)
        swipeRefresh.setColorSchemeResources(R.color.design_default_color_on_secondary)                 //刷新功能
        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }

        nav_button.setOnClickListener{                                                                  // navigation 键
            drawerLayout.openDrawer(GravityCompat.START)                                                //打开drawerLayout,打开placeFragment,实际上placeFragment 早在开始 weatherActivity是已经静态加载
        }

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {                           //drawerLayout 监听器 ,新知识!!!

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {
                val manager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
            }
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    fun refreshWeather(){
        weatherViewModel.refreshWeather(weatherViewModel.locationLng,weatherViewModel.locationLat)      //触发变化
        swipeRefresh.isRefreshing=true                                                                  //显示进度条
    }

    fun showWeatherInfo(result:Weather){                                                                //展示weather
        placeName.text=weatherViewModel.placeName
        val realTimeWeather=result.realTime                                                         //取结果，分配
        val dailyWeather=result.dailyResponse                                                       //取结果，分配
//---------------------------------------------realTimeWeather--------------------------------------
        currentTemp.text=realTimeWeather.temperature.toString()+"°C"
        currentSky.text=realTimeWeather.skycon
        val pm25=realTimeWeather.air_quality.aqi.chn.toString()
        currentAQI.text=pm25


        nowLayout.setBackgroundResource(getSky(realTimeWeather.skycon).bg)
        //nowLayout.setBackgroundResource(R.mipmap.bg_clear_day)
//---------------------------------------------dailyWeather-----------------------------------------

        forecastLayout.removeAllViews()                                                             //parentview removeall + parentview.addview
        val days=dailyWeather.skycon.size
        for(i in 0 until days){

            val skycon=dailyWeather.skycon[i]
            val temperature=dailyWeather.temperature[i]

            val view=LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false)     //动态加载xml 成为 view, 先remove view, 最后再 addView
            val dateInfo=view.findViewById(R.id.dateInfo) as TextView                                                       //分配地址
            val skyInfo=view.findViewById(R.id.skyInfo) as TextView
            val skyIcon=view.findViewById(R.id.skyIcon) as ImageView

            val temperatureInfo=view.findViewById(R.id.temperatureInfo)as TextView
            val simpleDateFormat=SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text=simpleDateFormat.format(skycon.date)

            val sky= getSky(skycon.value)                                                           //通过key， 确定 sky 的 value 即 class Sky(val info:String,val icon:Int,val bg:Int)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text=sky.info
            val tempText="${temperature.min.toInt()}~${temperature.max.toInt()}°C"
            temperatureInfo.text=tempText
            forecastLayout.addView(view)
        }
        val lifeIndex=dailyWeather.life_index
        coldRiskText.text=lifeIndex.coldRisk[0].desc
        dressingText.text=lifeIndex.dressing[0].desc
        ultravioletText.text=lifeIndex.ultraviolet[0].desc
        carWashingText.text=lifeIndex.carWashing[0].desc
        weatherLayout.visibility= View.VISIBLE
    }

}