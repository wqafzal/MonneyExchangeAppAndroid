package com.example.moneyexchangeapp.feature.exchange.presentation.calculator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.core.base.BaseRecyclerAdapter
import com.example.moneyexchangeapp.databinding.RowCalculatedResultBinding
import com.example.moneyexchangeapp.feature.exchange.domain.model.ExchangeRate

class ConvertedAmountListAdapter : BaseRecyclerAdapter<ExchangeRate, ConvertedAmountListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_calculated_result, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        DataBindingUtil.bind<RowCalculatedResultBinding>(holder.itemView)?.let {
            it.data = item
        }


    }
}