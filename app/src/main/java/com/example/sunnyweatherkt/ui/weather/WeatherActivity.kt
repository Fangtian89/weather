package com.example.sunnyweatherkt.ui.weather

import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweatherkt.R
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


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)


        setProgressDialog()




        var weatherResult: Weather?=null
        //------------------------------------------------------------------------------------------
        val decorView = window.decorView                                                                              //状态栏和背景融合
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        //------------------------------------------------------------------------------------------
        //Log.d(TAG, "onCreate: 1")

        if (weatherViewModel.locationLng.isEmpty()) {
            weatherViewModel.locationLng = intent.getStringExtra("location_lng")
                    ?: ""                                                                           //判断 intent.getStringExtra("location_lng") 是否为空，为空给null
        }

        if (weatherViewModel.locationLat.isEmpty()) {
            weatherViewModel.locationLat = intent.getStringExtra("location_lat")
                    ?: ""                                                                           //为空给null
        }

        if (weatherViewModel.placeName.isEmpty()) {
            weatherViewModel.placeName = intent.getStringExtra("place_name")
                    ?: ""                                                                           //为空给null
        }


        weatherViewModel.refreshWeather(weatherViewModel.locationLng, weatherViewModel.locationLat)      //触发viewModel内部变化
        //Log.d(TAG, "onCreate: 2")
        weatherViewModel.weatherLiveData.observe(this, Observer {                               //观察livedata 变化
            result ->
            weatherResult = result.getOrNull()                                                      //getOrNull 一种防空方法，若为空则给 0
            if (weatherResult != null) {
                showWeatherInfo(weatherResult!!)
            } else {
                "无法获取天气信息!！".showToastLg()
                result.exceptionOrNull()?.printStackTrace()
                println(result.exceptionOrNull()?.message + "!!!")
            }
            swipeRefresh.isRefreshing = false                                                             //隐藏进度条
        })
        //weatherViewModel.refreshWeather(weatherViewModel.locationLng,weatherViewModel.locationLat)
        swipeRefresh.setColorSchemeResources(R.color.design_default_color_on_secondary)                 //刷新功能
        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }



        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {                           //drawerLayout 监听器 ,新知识!!!

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })

        fab.setOnClickListener {
            val placeInGson = Gson().fromJson(intent.getStringExtra("place"), PlaceResponsing.Place::class.java)       //保存选中的地点,及天气选项
            if(weatherResult!=null){
                Repository.saveFavouritePlace(placeInGson, weatherResult!!)
                nav_list.visibility=View.VISIBLE
            }
        }
        nav_button.setOnClickListener {                                                                  // navigation 键，动态加载 fragment
            drawerLayout.openDrawer(GravityCompat.START)                                                //如果用静态加载打开drawerLayout,打开placeFragment,实际上placeFragment 早在开始 weatherActivity是已经静态加载
            val fragmentManager=supportFragmentManager
            val transaction=fragmentManager.beginTransaction()
            val fragment=PlaceFragment()
            transaction.add(R.id.frag,fragment)
            transaction.commit()
        }

        nav_list.setOnClickListener {
            //val fragment=FavouriteFragment()                                                          //打开保存的喜爱地址和天气
            //supportFragmentManager.beginTransaction().add(R.id.drawerLayout,FavouriteFragment::class.java.newInstance()).addToBackStack(null).commit()
            startActivity<FavouriteActivity>(this) {}
        }
                                                                                                    //静态加载PlaceFragment
        if(Repository.readFavouritePlace().isEmpty()){                                              //方法可能不好,直接访问了 Repository
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

//    override fun onResume() {
//        super.onResume()
//        if (Repository.readFavouritePlace().isNotEmpty()){
//            nav_list.visibility=View.GONE
//        }
//    }


    fun refreshWeather() {
        weatherViewModel.refreshWeather(weatherViewModel.locationLng, weatherViewModel.locationLat)      //触发变化
        swipeRefresh.isRefreshing = true                                                                  //显示进度条
    }


    fun showWeatherInfo(result: Weather) {                                                                //展示weather
        dialog.dismiss()
        placeName.text = weatherViewModel.placeName
        val realTimeWeather = result.realTime                                                         //取结果，分配
        val dailyWeather = result.dailyResponse                                                       //取结果，分配
//---------------------------------------------realTimeWeather--------------------------------------
        currentTemp.text = realTimeWeather.temperature.toString() + "°C"
        currentSky.text = realTimeWeather.skycon
        val pm25 = realTimeWeather.air_quality.aqi.chn.toString()
        currentAQI.text = pm25
        //Log.d(TAG, "showWeatherInfo: 3 "+Thread.currentThread().name)
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm")
        val formatted = formatter.format(date)
        upateTime.text = "last update: $formatted"

        nowLayout.setBackgroundResource(getSky(realTimeWeather.skycon).bg)

        //nowLayout.setBackgroundResource(R.mipmap.bg_clear_day)
//---------------------------------------------dailyWeather-----------------------------------------

        forecastLayout.removeAllViews()                                                             //parentview removeall + parentview.addview
        val days = dailyWeather.skycon.size
        for (i in 0 until days) {

            val skycon = dailyWeather.skycon[i]
            val temperature = dailyWeather.temperature[i]

            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)     //动态加载xml 成为 view, 先remove view, 最后再 addView
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView                                                       //分配地址
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView

            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)

            val sky = getSky(skycon.value)                                                           //通过key， 确定 sky 的 value 即 class Sky(val info:String,val icon:Int,val bg:Int)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()}~${temperature.max.toInt()}°C"
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


    fun setProgressDialog() {
        val llPadding = 30
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam
        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam
        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(this)
        tvText.text = "Loading ..."
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20f
        tvText.layoutParams = llParam
        ll.addView(progressBar)
        ll.addView(tvText)
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(ll)
        dialog = builder.create()
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

}

