package com.example.sunnyweatherkt.Util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.sunnyweatherkt.MyApplication

inline fun <reified T>startActivity(context: Context, block: Intent.()->Unit){
    val intent= Intent(context,T::class.java)

    intent.block()
    context.startActivity(intent)
}

fun String.showToastSt(duration:Int=Toast.LENGTH_SHORT){
    Toast.makeText(MyApplication.context,this,duration).show()
}

fun String.showToastLg(duration:Int=Toast.LENGTH_LONG){
    Toast.makeText(MyApplication.context,this,duration).show()
}

fun test(){
    Log.d("TAG", "test: ")}

