package com.example.sunnyweatherkt.Util

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.util.TimeZone

object Utility {
    private lateinit var dialog:androidx.appcompat.app.AlertDialog
    fun setProgressDialog(activity:Activity){
        val llPadding = 30
        val ll = LinearLayout(activity)                                                             //做一个 linearLayout
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(activity).apply {
            isIndeterminate = true
            setPadding(0, 0, llPadding, 0)
            layoutParams = llParam
        }                                                                                           //做一个 旋转的 progressBar

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(activity).apply {
            text = "Loading ..."
            setTextColor(Color.parseColor("#000000"))
            textSize = 20f
            layoutParams = llParam
        }                                                                                           //做一个 textView

        ll.addView(progressBar)                                                                     //把 progressBar 放到 linearLayout 上
        ll.addView(tvText)                                                                          //把 textView 放到 linearLayout 上
        val builder = androidx.appcompat.app.AlertDialog.Builder(activity).apply {
            setCancelable(false)
            setView(ll)
        }                                                                                           //做一个 AlertDialog 的 builder

        dialog = builder.create()                                                               //产生 dialog
        dialog.show()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }
    fun dismissDialog(){
        dialog?.dismiss()
    }

    val tz = TimeZone.getDefault()
    @RequiresApi(Build.VERSION_CODES.N)
    val isDaylightTime = tz.observesDaylightTime()
    val timeZone = TimeZone.getDefault().getDisplayName(isDaylightTime, TimeZone.SHORT)

}