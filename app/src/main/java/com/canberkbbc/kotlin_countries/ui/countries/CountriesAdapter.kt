package com.canberkbbc.kotlin_countries.ui.countries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canberkbbc.kotlin_countries.R
import com.canberkbbc.kotlin_countries.data.remote.model.CountryModel
import com.canberkbbc.kotlin_countries.databinding.CountryItemBinding
import com.canberkbbc.kotlin_countries.ui.MainActivity
import com.canberkbbc.kotlin_countries.utils.extensions.getImage
import com.canberkbbc.kotlin_countries.utils.extensions.placeHolderProgressBar

class CountriesAdapter : RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    private lateinit var countryItemBinding: CountryItemBinding
    private var onItemClickListener: ((CountryModel) -> Unit)? = null
    var countryList: MutableList<CountryModel> = mutableListOf()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        countryItemBinding = CountryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(countryItemBinding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val countryItem = countryList[position]
        countryItem.let {
            holder.bind(it, onItemClickListener)
        }
    }

    override fun getItemCount(): Int = countryList.size

    fun setOnItemClickListener(onItemClickListener: ((CountryModel) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    fun updateCountryList(newCountryList : List<CountryModel>){
        countryList.clear();
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    class CountryViewHolder(private val countryItemBinding: CountryItemBinding) :
        RecyclerView.ViewHolder(countryItemBinding.root) {
        fun bind(
            countryItem: CountryModel,
            onItemClickListener: ((countryData: CountryModel) -> Unit)?
        ) {
            countryItemBinding.country = countryItem
            itemView.setOnClickListener { onItemClickListener?.invoke(countryItem) }
        }
    }


}