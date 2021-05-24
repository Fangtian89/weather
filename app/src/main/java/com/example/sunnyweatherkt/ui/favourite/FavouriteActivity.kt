package com.example.sunnyweatherkt.ui.favourite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sunnyweatherkt.R

class FavouriteActivity : AppCompatActivity() {                                                     //静态启动 FavouriteFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

    }
}