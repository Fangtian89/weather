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
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.getSky
import com.example.sunnyweatherkt.ui.weather.WeatherActivity
import com.google.gson.Gson

class FavouriteAdapter(val fragment:FavouriteFragment,val weather:MutableMap<PlaceResponsing.Place,RealTimeResponse.RealTime>):RecyclerView.Adapter<FavouriteAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Holder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.favourite_item,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {


            val list = ArrayList<PlaceResponsing.Place>(weather.keys)
            //val position=holder.adapterPosition
            val name=list[position].name

            holder.placeName.text=name
            holder.temperatureInfo.text= weather[list[position]]!!.temperature.toString()+"°C"      //通过key 得到 value
            holder.skyInfo.text= weather[list[position]]!!!!.skycon
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

            }
    }

    override fun getItemCount()=weather.size

    inner class Holder(view:View):RecyclerView.ViewHolder(view){
        val placeName=view.findViewById(R.id.placeInfo) as TextView
        val skyIcon=view.findViewById(R.id.skyIcon)as ImageView
        val skyInfo=view.findViewById(R.id.skyInfo)as TextView
        val temperatureInfo=view.findViewById(R.id.temperatureInfo)as TextView
    }
}


