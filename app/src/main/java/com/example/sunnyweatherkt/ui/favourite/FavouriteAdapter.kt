package com.example.sunnyweatherkt.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.logic.model.RealTimeResponse
import com.example.sunnyweatherkt.logic.model.getSky

class FavouriteAdapter(val weather:MutableMap<String,RealTimeResponse>):RecyclerView.Adapter<FavouriteAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Holder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.favourite_item,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
            val list = ArrayList<String>(weather.keys)
            val position=holder.adapterPosition
            val name=list[position]

            holder.placeName.text=name
            holder.temperatureInfo.text= weather[name]!!.result.realtime.temperature.toString()+"°C"
            holder.skyInfo.text= weather[name]!!.result.realtime.skycon
            holder.skyIcon.setImageResource(getSky(weather[name]!!.result.realtime.skycon).icon)
    }

    override fun getItemCount()=weather.size

    inner class Holder(view:View):RecyclerView.ViewHolder(view){
        val placeName=view.findViewById(R.id.placeInfo) as TextView
        val skyIcon=view.findViewById(R.id.skyIcon)as ImageView
        val skyInfo=view.findViewById(R.id.skyInfo)as TextView
        val temperatureInfo=view.findViewById(R.id.temperatureInfo)as TextView
    }
}