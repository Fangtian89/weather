package com.example.sunnyweatherkt.ui.favourite

import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.Weather
import com.google.gson.Gson
import com.yanzhenjie.recyclerview.*
import kotlinx.android.synthetic.main.favouritelayout.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FavouriteFragment: Fragment(){
    val favouriteViewModeliew by lazy { ViewModelProvider(this).get(FavouriteViewModel::class.java) }
    lateinit var adapter: FavouriteAdapter
    lateinit var list:ArrayList<PlaceResponsing.Place>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.favouritelayout,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date= Calendar.getInstance().time
        val formatter= SimpleDateFormat("YYYY/M/dd HH:mm")
        val formatted=formatter.format(date)
        updateTime.text=formatted
        val placeWeatherList=favouriteViewModeliew.readFavouritePlace()                             //从SharedPreferences 直接读取地址及天气list
        val weatherResult = mutableMapOf<PlaceResponsing.Place,RealTimeResponse.RealTime>()

        placeWeatherList.forEach(){                                                                 //转换
            val placeInGson=Gson().fromJson(it.key,PlaceResponsing.Place::class.java)
            val weatherInGson=Gson().fromJson(it.value,Weather::class.java)
            val realTimeInGson=weatherInGson.realTime
            weatherResult[placeInGson] = realTimeInGson                                             //put
        }
        list=ArrayList(weatherResult.keys)

//            val layoutManager=LinearLayoutManager(activity)                                       //展示到RecyclerView
//            recyclerView.layoutManager=layoutManager
//            if (weatherResult!=null){                                                             //确定 weatherResult 绝不为空，在Adapter 里 才敢保证用 !!
//                adapter=FavouriteAdapter(this,weatherResult)                                      //实例化 adapter
//                recyclerView.adapter=adapter
//                recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
//                    override fun getItemOffsets(
//                        outRect: Rect,
//                        view: View,
//                        parent: RecyclerView,
//                        state: RecyclerView.State
//                    ) {
//                        super.getItemOffsets(outRect, view, parent, state)
//                        outRect.set(3,3,3,3)
//                    }
//                })
//            }

        val swipeMenuCreator=
            SwipeMenuCreator { swipeLeftMenu, swipeRightMenu, position ->
                val width=resources.getDimensionPixelSize(R.dimen.dp_70)
                val height=ViewGroup.LayoutParams.MATCH_PARENT

                val deleteItem= object : SwipeMenuItem(context) {
                }.setImage(R.drawable.swipedelete)
                    .setWidth(width)
                    .setHeight(height)
                swipeRightMenu.addMenuItem(deleteItem)                                              //添加右侧菜单

//                val addItem= object : SwipeMenuItem(context) {
//                }.setText("add")
//                    .setTextColor(resources.getColor(R.color.black))
//                    .setWidth(200)
//                    .setHeight(height)
//                swipeRightMenu.addMenuItem(addItem)                                               //向右侧再添加一个添加菜单
            }

        val mMenuItemClickListener= object : OnItemMenuClickListener {
            override fun onItemClick(menuBridge: SwipeMenuBridge, position: Int) {

                menuBridge.closeMenu()
                val direction = menuBridge.direction                                                // 左侧还是右侧菜单。
                val menuPosition = menuBridge.position                                              // 菜单在RecyclerView的Item中的Position。

                if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                    Repository.removeFavouritePlace(list[position])                                 //sharedpreferences 里面的删除
                    weatherResult.remove(list[position])                                            //传给 adapter 里的数据删除
                    list.removeAt(position)                                                         //这里list 也需要删除，
                    adapter.notifyDataSetChanged()
                    if(Repository.readFavouritePlace().isEmpty()){                                              //方法可能不好
                        activity?.finish()                                                          // favouriteActivity finish()
                    }
                } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
                    Toast.makeText(
                        context,
                        "list第$position; 左侧菜单第$menuPosition",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

        recyclerView.setSwipeMenuCreator(swipeMenuCreator)
        recyclerView.setOnItemMenuClickListener(mMenuItemClickListener)
        val layoutManager=LinearLayoutManager(activity)                                             //展示到RecyclerView
        recyclerView.layoutManager=layoutManager

        if (weatherResult!=null){                                                                   //确定 weatherResult 绝不为空，在Adapter 里 才敢保证用 !!
            adapter=FavouriteAdapter(this,weatherResult)                                    //实例化 adapter
            recyclerView.adapter=adapter                                                            //setAdapter 要放到 setWipeMenuCreator 后面
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
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
        }

//        recyclerView.setSwipeMenuCreator(swipeMenuCreator)
//        recyclerView.setOnItemMenuClickListener(mMenuItemClickListener)



    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {                                   //只起到展示作用
        super.onActivityCreated(savedInstanceState)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}





