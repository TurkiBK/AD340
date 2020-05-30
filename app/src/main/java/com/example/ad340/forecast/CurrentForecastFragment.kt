 package com.example.ad340.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*

import com.example.ad340.details.ForecastDetailsActivity
import com.example.ad340.location.LocationEntryFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

 /**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment : Fragment() {

     private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
     private  val forecastRepository = ForecastRepository()

     private lateinit var appNavigator : AppNavigator

     override fun onAttach(context: Context) {
         super.onAttach(context)
         appNavigator = context as AppNavigator
     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val zipcode = arguments!!.getString(KEY_Zipcode) ?: ""

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forcast, container, false)

        val locationEntryButton: FloatingActionButton =view.findViewById(R.id.locationEntryButton)
            locationEntryButton.setOnClickListener{
                appNavigator.navigateToLocationEntry()
            }


        val forecastList: RecyclerView = view.findViewById(R.id.forecastList) //chang to dailyForecast
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

        forecastRepository.loadForecast(zipcode)


        return view
    }
     private fun showForecastDetails(forecast: DailyForecast){

         val forecastDetailsIntent = Intent(requireContext(), ForecastDetailsActivity :: class.java)
         forecastDetailsIntent.putExtra("key_temp", forecast.temp)
         forecastDetailsIntent.putExtra("key_description", forecast.description)
         startActivity(forecastDetailsIntent)

     }

     companion object{
         const val KEY_Zipcode ="key_zipcode"

         fun newInstance(zipcode: String) :CurrentForecastFragment{
             val fragment = CurrentForecastFragment()

             val  args =  Bundle()
             args.putString(KEY_Zipcode, zipcode)
             fragment.arguments =args
             return fragment
         }
     }

}
