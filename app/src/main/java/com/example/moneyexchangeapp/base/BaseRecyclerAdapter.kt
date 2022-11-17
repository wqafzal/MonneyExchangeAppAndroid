package com.example.moneyexchangeapp.base

import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

abstract class BaseRecyclerAdapter<T, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<H>() {
    var items: ArrayList<T> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<T>?) {
        this.items.clear()
        if (items != null && items.isNotEmpty()) {
            this.items.addAll(items)
        }
        notifyDataSetChanged()
    }

}