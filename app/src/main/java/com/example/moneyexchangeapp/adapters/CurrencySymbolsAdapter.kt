package com.example.moneyexchangeapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.moneyexchangeapp.data.model.ExchangeRate
import com.example.moneyexchangeapp.databinding.RowCurrencyBinding

class CurrencySymbolsAdapter : BaseAdapter() {
    private var items: ArrayList<ExchangeRate> = ArrayList()
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): ExchangeRate {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding: RowCurrencyBinding =
            if (convertView == null) RowCurrencyBinding.inflate(LayoutInflater.from(parent?.context)) else RowCurrencyBinding.bind(
                convertView
            )
        binding.currencyText.text = getItem(position).symbol
        return binding.root
    }

    fun setItems(items: List<ExchangeRate>?) {
        this.items.clear()
        if (items != null && items.isNotEmpty()) {
            this.items.addAll(items)
        }
        notifyDataSetChanged()
    }
}