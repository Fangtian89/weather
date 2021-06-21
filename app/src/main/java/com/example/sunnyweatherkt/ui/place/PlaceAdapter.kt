package com.example.sunnyweatherkt.ui.place

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweatherkt.R
import com.example.sunnyweatherkt.logic.model.PlaceResponsing
import com.example.sunnyweatherkt.ui.weather.WeatherActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.place_item.view.*

class PlaceAdapter(private val fragment:PlaceFragment,private val placeList:List<PlaceResponsing.Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){                                //注意 RecyclerView.ViewHolder 有一个带参数的 constructor, 因此在extend 的时候要把它放到class 参数当中，成为main constructor
        val placeName:TextView=view.findViewById(R.id.placeName)
        val placeAddress:TextView=view.findViewById(R.id.placeAddress)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        val holder=ViewHolder(view)

        holder.itemView.setOnClickListener{                                                         //跳转到weatherActivity
            val position = holder.adapterPosition
            val place=placeList[position]
            val intent= Intent(parent.context,WeatherActivity::class.java).apply{
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
                putExtra("place",Gson().toJson(place))
            }
            fragment.viewModel.savedPlace(place)                                                    //存储选中的城市
            fragment.startActivity(intent)
            fragment.activity?.finish()
        }
        return holder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place=placeList[position]
        holder.placeAddress.text=place.address
        holder.placeName.text=place.name
    }


    override fun getItemCount()=placeList.size
}