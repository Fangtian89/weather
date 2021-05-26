package com.example.sunnyweatherkt.ui.favourite

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemTouchHelper(val helperCallback:ItemTouchHelperCallback):ItemTouchHelper.Callback() {
    val TAG="swipe"

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        Log.d(TAG, "getMovementFlags: ")
        return makeMovementFlags(ItemTouchHelper.UP,ItemTouchHelper.START)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        Log.d(TAG, "onMove: ")
        helperCallback.onMove(viewHolder.adapterPosition,target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Log.d(TAG, "onSwiped: ")
       helperCallback.onItemDelete(viewHolder.adapterPosition)
    }

//    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
//        super.onSelectedChanged(viewHolder, actionState)
//    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return super.isItemViewSwipeEnabled()
    }

    interface ItemTouchHelperCallback {
        fun onItemDelete(position:Int)
        fun onMove(fromPosition: Int, toPosition: Int)
    }
}


