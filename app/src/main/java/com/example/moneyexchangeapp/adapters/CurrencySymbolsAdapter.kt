package com.example.moneyexchangeapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.moneyexchangeapp.data.model.Country
import com.example.moneyexchangeapp.databinding.RowCurrencyBinding

class CurrencySymbolsAdapter : BaseAdapter() {
    var items: ArrayList<Country> = ArrayList()
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Country {
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
        binding.currencyText.text = getItem(position).currencySymbol
        return binding.root
    }

    fun setItems(items: List<Country>?) {
        this.items.clear()
        if (items != null && items.isNotEmpty()) {
            this.items.addAll(items)
        }
        notifyDataSetChanged()
    }
}