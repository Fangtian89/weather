package com.example.sunnyweatherkt.ui.place

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import com.example.sunnyweatherkt.MainActivity
import com.example.sunnyweatherkt.MyApplication
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.Util.MapUtils
import com.example.sunnyweatherkt.Util.Utility
import com.example.sunnyweatherkt.Util.showToastSt
import com.example.sunnyweatherkt.ui.favourite.FavouriteViewModel
import com.example.sunnyweatherkt.ui.weather.WeatherActivity
import com.example.sunnyweatherkt.ui.weather.WeatherViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.favouritelayout.*
import kotlinx.android.synthetic.main.fragment_place.*
import kotlinx.android.synthetic.main.fragment_place.recyclerView
import kotlinx.coroutines.*
import java.security.Permissions
import java.text.SimpleDateFormat
import java.util.*
import com.example.sunnyweatherkt.Util.startActivity
import kotlin.collections.ArrayList

class PlaceFragment:Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }        //调用的时候 实例化一个viewModel
    val favouriteViewModeliew by lazy { ViewModelProvider(this).get(FavouriteViewModel::class.java) }
    val weatherViewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    lateinit var adapter: PlaceAdapter
    var address: String?=null
    lateinit var mLocationManager: LocationManager
    lateinit var searchPlaceEdit:EditText
    lateinit var deleteButton: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchPlaceEdit=view.findViewById(R.id.edit)as EditText
        deleteButton=view.findViewById(R.id.delete_2)as ImageButton

        if (activity is MainActivity && viewModel.isSavedPlace()) {                                   //PlaceFragment -> viewModel -> Repository -> Dao (SharedPreferences) 获取已经保存(访问)的地址
            val place =
                viewModel.getSavedPlace()                                                           //要先判断一下是否有此保存的数据，然后拿出来
            val intent = Intent(
                context,
                WeatherActivity::class.java
            ).apply {                                                                               //跳珠进入 weatherActivity
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

        val layoutManager =
            LinearLayoutManager(activity)                                                           //加载 RecyclerView
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter
        //Log.d(TAG, "onActivityCreated_2: " + activity)

        searchPlaceEdit.addTextChangedListener { edit ->                                            //重要方法 addTextChangedListener
            val content = edit.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlacesViewModel(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places =
                result.getOrNull()                                                                  //getOrNull() 即非null 返回正差值，为null 时返回 null
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



        navigationLocation.setOnClickListener {
            if (ContextCompat.checkSelfPermission(                                                  //请求地理权限
                    MyApplication.context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)    //!!!在fragment 里面询问要用这句话，才能 call onRequestPermission
            } else {                                                                                //如果权限已经有了
                address= context?.let { it1 -> Utility.getCurrentLocation(it1) }
                address?.let { it1 -> showPlaces4CurrentLocation(it1) }
            }
        }

        deleteButton.setOnClickListener {
            searchPlaceEdit.setText("")
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {                                   //两个重要方法
        super.onActivityCreated(savedInstanceState)                                                  //Fragment 里面放 RecyclerView
    }

    private fun showPlaces4CurrentLocation(address:String) {
        val layoutManager = LinearLayoutManager(activity)                                           //加载 RecyclerView
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter
        //Log.d(TAG, "onActivityCreated_2: " + activity)

//        searchPlaceEdit.addTextChangedListener { edit ->                                            //重要方法 addTextChangedListener
//            val content = edit.toString()
//            if (content.isNotEmpty()) {
//                viewModel.searchPlacesViewModel(content)
//                //Log.d(TAG, "onActivityCreated_4: " + activity)
//            } else {
//                recyclerView.visibility = View.GONE
//                bgImageView.visibility = View.VISIBLE
//                viewModel.placeList.clear()
//                adapter.notifyDataSetChanged()
//            }
//        }
        searchPlaceEdit.setText(address)

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places =
                result.getOrNull()                                                                  //getOrNull() 即非null 返回正差值，为null 时返回 null
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,

        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1->
                if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(context!=null){
                        address=Utility.getCurrentLocation(context!!)
                        showPlaces4CurrentLocation(address!!)
                    }

                }
        }
    }

//    override fun onStop() {
//        super.onStop()
//        mLocationManager.removeUpdates(mLocationListener)
//    }
}