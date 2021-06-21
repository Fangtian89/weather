package com.example.sunnyweatherkt.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.logic.model.HourlyResponse
import com.example.sunnyweatherkt.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*


class HourlyAdapter(val hourlyWeather:HourlyResponse.Hourly):RecyclerView.Adapter<HourlyAdapter.Holder>() {
    private val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.CHINA)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.houritem,parent,false)
        val holder=Holder(view)
        return holder
    }

    override fun onBindViewHolder(holder: HourlyAdapter.Holder, position: Int) {

        if(hourlyWeather.temperature.size==hourlyWeather.skycon.size){
            val temp=hourlyWeather.temperature[position].value
            holder.temperature.text=String.format("%.1f",temp)+"°C"
            holder.hour.text=simpleDateFormat.format(hourlyWeather.skycon[position].datetime)
            holder.possibility.text=""
            val sky= getSky(hourlyWeather.skycon[position].value)
            holder.imageView.setImageResource(sky.icon)
        }


    }

    override fun getItemCount()=hourlyWeather.skycon.size

    inner class Holder(view: View):RecyclerView.ViewHolder(view){
        val hour:TextView=view.findViewById(R.id.time)
        val imageView:ImageView=view.findViewById(R.id.skyIconi)
        val possibility:TextView=view.findViewById(R.id.possibility)
        val temperature=view.findViewById(R.id.temperature) as TextView

    }
}