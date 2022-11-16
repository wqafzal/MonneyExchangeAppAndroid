package com.example.moneyexchangeapp.ui.exchange.history.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.base.BaseRecyclerAdapter
import com.example.moneyexchangeapp.data.HistoricalData
import com.example.moneyexchangeapp.data.model.ExchangeRate
import com.example.moneyexchangeapp.databinding.RowExchangeRateBinding
import com.example.moneyexchangeapp.databinding.RowHistoryBinding

class ExchangeHistoryAdapter :
    BaseRecyclerAdapter<HistoricalData, ExchangeHistoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_history, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val _data = items[position]
        DataBindingUtil.bind<RowHistoryBinding>(holder.itemView)?.apply {
            this.data = _data
            list.adapter = ExchangeRateAdapter().apply {
                this.setItems(_data.currencies)
            }
        }
    }
}

class ExchangeRateAdapter :
    BaseRecyclerAdapter<ExchangeRate, ExchangeRateAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_exchange_rate, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        DataBindingUtil.bind<RowExchangeRateBinding>(holder.itemView)?.apply {
            this.exchangeRate = data
        }
    }
}