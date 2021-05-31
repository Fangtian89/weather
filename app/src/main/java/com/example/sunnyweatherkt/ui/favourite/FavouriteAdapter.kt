package com.example.sunnyweatherkt.ui.favourite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.Util.startActivity
import com.example.sunnyweatherkt.logic.Repository
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.getSky
import com.example.sunnyweatherkt.ui.weather.WeatherActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.now.*

class FavouriteAdapter(val fragment:FavouriteFragment,val weather:MutableMap<PlaceResponsing.Place,RealTimeResponse.RealTime>):RecyclerView.Adapter<FavouriteAdapter.Holder>(),RecyclerViewItemTouchHelper.ItemTouchHelperCallback {
    lateinit var list:List<PlaceResponsing.Place>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Holder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.favourite_item,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        list =ArrayList<PlaceResponsing.Place>(weather.keys)                                    //把weather 里的 keys 都拿出来，放入一个Arraylist 中
        val name=list[position].name
        holder.placeName.text=name
        holder.temperatureInfo.text= weather[list[position]]!!.temperature.toString()+"°C"          //通过key 得到 value，  因为在FavouriteFrag 里面判断了weather 不为null, 所以敢硬气的用 !!
        holder.skyInfo.text= weather[list[position]]!!.skycon
        holder.skyIcon.setImageResource(getSky(weather[list[position]]!!.skycon).icon)

        holder.itemView.setOnClickListener{
            val clickPosition=holder.adapterPosition
            val lng=list[clickPosition].location.lng
            val lat=list[clickPosition].location.lat
            val name=list[clickPosition].name
            startActivity<WeatherActivity>(fragment.context!!){
                putExtra("location_lng",lng)
                putExtra("location_lat",lat)
                putExtra("place_name",name)
                putExtra("place", Gson().toJson(list[clickPosition]))
            }
            Repository.savePlace(list[clickPosition])                                               //没有用 viewmodel ，而直接任意一个viewmodel 用 Repository 里的函数了。 好，坏？
        }
    }

    override fun getItemCount()=weather.size

    inner class Holder(view:View):RecyclerView.ViewHolder(view){
        val placeName=view.findViewById(R.id.placeInfo) as TextView
        val skyIcon=view.findViewById(R.id.skyIcon)as ImageView
        val skyInfo=view.findViewById(R.id.skyInfo)as TextView
        val temperatureInfo=view.findViewById(R.id.temperatureInfo)as TextView
    }

    override fun onItemDelete(position: Int) {                                                      //接口 RecyclerViewItemTouchHelper.ItemTouchHelperCallback 的具体实现

        Repository.removeFavouritePlace(list[position])                                             //方法可能不好,直接访问 Repository
        weather.remove(list[position])                                                              //删除时，这个要有
        //notifyItemRemoved(position)                                                               也可以有
        notifyDataSetChanged()                                                                      //其次这个要有
        if(Repository.readFavouritePlace().isEmpty()){                                              //方法可能不好
            fragment.activity?.finish()                                                             // favouriteActivity finish()

        }
    }

    override fun onMove(fromPosition: Int, toPosition: Int) {
        TODO("Not yet implemented")
    }
}


