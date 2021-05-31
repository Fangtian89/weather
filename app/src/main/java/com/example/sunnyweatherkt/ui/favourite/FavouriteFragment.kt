package com.example.sunnyweatherkt.ui.favourite

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.Weather
import com.google.gson.Gson
import kotlinx.android.synthetic.main.favouritelayout.*
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
                recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                    /**
                     * Retrieve any offsets for the given item. Each field of `outRect` specifies
                     * the number of pixels that the item view should be inset by, similar to padding or margin.
                     * The default implementation sets the bounds of outRect to 0 and returns.
                     *
                     *
                     *
                     * If this ItemDecoration does not affect the positioning of item views, it should set
                     * all four fields of `outRect` (left, top, right, bottom) to zero
                     * before returning.
                     *
                     *
                     *
                     * If you need to access Adapter for additional data, you can call
                     * [RecyclerView.getChildAdapterPosition] to get the adapter position of the
                     * View.
                     *
                     * @param outRect Rect to receive the output.
                     * @param view    The child view to decorate
                     * @param parent  RecyclerView this ItemDecoration is decorating
                     * @param state   The current state of RecyclerView.
                     */
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.set(3,3,3,3)
                    }
                })

                val callBack=RecyclerViewItemTouchHelper(adapter)                                   //swipe是 重要指示
                val itemTouchHelper=ItemTouchHelper(callBack)
                itemTouchHelper.attachToRecyclerView(recyclerView)
            }
            adapter.notifyDataSetChanged()
    }



}





