 package com.example.ad340.forecast

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.DailyForecast
import com.example.ad340.DailyForecastAdapter

import com.example.ad340.R
import com.example.ad340.details.ForecastDetailsActivity

 /**
 * A simple [Fragment] subclass.
 */
class CurrentForcastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forcast, container, false)


        val forecastList: RecyclerView = view.(R.id.forecastList) //chang to dailyForecast
        forecastList.layoutManager = LinearLayoutManager(requireContext() )
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) {forecast->
            showForecastDetails(forecast)

        }
        forecastList.adapter =dailyForecastAdapter

        // create the observer which update the UI in response to forecast updates
        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems ->
            // update our list adaptor
            //Toast.makeText(this,"Loaded Items", Toast.LENGTH_SHORT).show()
            dailyForecastAdapter.submitList(forecastItems)

        }
        forecastRepository.weeklyForecast.observe(this ,weeklyForecastObserver)


        return view
    }
     private fun showForecastDetails(forecast: DailyForecast){

         val forecastDetailsIntent = Intent(requireContext(), ForecastDetailsActivity :: class.java)
         forecastDetailsIntent.putExtra("key_temp", forecast.temp)
         forecastDetailsIntent.putExtra("key_description", forecast.description)
         startActivity(forecastDetailsIntent)

     }


}
