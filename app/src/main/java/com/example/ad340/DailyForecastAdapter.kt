package com.example.ad340

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class DailyForecastViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val  tempText : TextView = view.findViewById(R.id.tempText)
    private val descriptionText : TextView=view.findViewById(R.id.descriptionText)

    fun bind(dailyForecast: DailyForecast){  // bind method connect individual daily forecastItem to views
        // tempText.text = dailyForecast.temp.toString() // getting temp value and convert it to string, we change it to the bottom line format
        tempText.text = String.format("%.2f", dailyForecast.temp) // "%.2f" to convert temp to the 2 dismal ot hundret .
        descriptionText.text=dailyForecast.description

    }

}

class DailyForecastAdapter(
    private val clickHandler: (DailyForecast)-> Unit

) : ListAdapter <DailyForecast,DailyForecastViewHolder>(DIFF_CONFIG){

    companion object{

    val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyForecast>() {
        override fun areItemsTheSame(
            oldItem: DailyForecast,
            newItem: DailyForecast
        ): Boolean {

            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: DailyForecast,
            newItem: DailyForecast

        ): Boolean {

            return oldItem == newItem
        }

    }



}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast , parent,false)
        return DailyForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            clickHandler(getItem(position))

        }
    }
}

