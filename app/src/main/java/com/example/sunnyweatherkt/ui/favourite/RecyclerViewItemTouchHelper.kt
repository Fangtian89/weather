package com.example.sunnyweatherkt.ui.favourite

import android.R.attr
import android.R.attr.bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweatherkt.MyApplication
import com.example.sunnyweatherkt.R


class RecyclerViewItemTouchHelper(val helperCallback:ItemTouchHelperCallback):ItemTouchHelper.Callback() {
    val TAG="swipe"

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        Log.d(TAG, "getMovementFlags: ")
        return makeMovementFlags(0,ItemTouchHelper.START)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        Log.d(TAG, "onMove: ")
        helperCallback.onMove(viewHolder.adapterPosition,target.adapterPosition)                    //可以回调接口的方法
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {                    //会给一个 swipe 的position viewHoler.position
        Log.d(TAG, "onSwiped: "+ direction)
       helperCallback.onItemDelete(viewHolder.adapterPosition)                                      //具体操作则通过自己定义的方法实现, helpferCallback 即是进来的一个实现了接口的对象，
                                                                                                    //可以回调接口的方法
    }


    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){                                        //dX大于0时向右滑动，小于0向左滑动
            val itemView=viewHolder.itemView                                                        //获取滑动的view
            val resources=MyApplication.context.resources
            val bitMap=BitmapFactory.decodeResource(resources, R.drawable.swipedelete)              //获取删除指示的背景图片
            val padding=10
            val maxDrawWidth=2*padding+bitMap.width/4                                               //最大的绘制宽度
            val paint=Paint()                                                                       //一个画笔
            paint.setColor(resources.getColor(R.color.lightblue))
            val itemTop=itemView.bottom-itemView.height
            val x=Math.round(Math.abs(dX))                                                          //滑动距离绝对值
            val drawWidth=Math.min(x,maxDrawWidth)
            val k=itemView.top
            val m=itemView.y
            val a=itemView.bottom
            val u=itemView.height
            val q=itemView.x
            val w=itemView.right
            val i=itemView.left
            val p=itemView.width
            itemView.alpha=0.7f                                                                     //透明度

            if(dX<0){
                c.drawRect(itemView.left.toFloat(),itemView.top.toFloat(),
                    itemView.right.toFloat(),itemView.bottom.toFloat(),paint)                       //根据滑动实时绘制一个方形背景

                if(x>padding){                                                                      //滑动距离大于padding时开始绘制背景图片
                    val rect=Rect()
                    rect.left=itemView.width-bitMap.width
                    rect.top = itemView.top //图片居中

                    val maxRight: Int = rect.left + bitMap.width
                    rect.right = itemView.right
                    rect.bottom =itemView.bottom

                    var rect1: Rect? =null
                    if(x<maxRight){
                        rect1=Rect()
                        rect1.left=10
                        rect1.top=100
                        rect1.bottom= bitMap.height
                        rect1.right=130
                    }
                    c.drawBitmap(bitMap,null,rect,paint)                                           //对图片剪接和限定显示区域,rect1 source, rect destination
                }


                itemView.translationX
            }else{
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }else{
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return super.isItemViewSwipeEnabled()
    }

    interface ItemTouchHelperCallback {                                                             //自定义一个接口，里面是delete 或 onMove 具体做什么
        fun onItemDelete(position:Int)
        fun onMove(fromPosition: Int, toPosition: Int)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        Log.d(TAG, "onSelectedChanged: "+actionState)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f                                                                                 //滑动多少才被视为 swipe 掉
    }

}


