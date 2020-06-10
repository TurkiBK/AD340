 package com.example.ad340.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*

import com.example.ad340.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

 /**
 * A simple [Fragment] subclass.
 */
class WeeklyForecastFragment : Fragment() {

     private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
     private  val forecastRepository = ForecastRepository()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forcast, container, false)

        val zipcode = arguments?.getString(KEY_Zipcode) ?: ""

        val locationEntryButton: FloatingActionButton =view.findViewById(R.id.locationEntryButton)
            locationEntryButton.setOnClickListener{
                showLocationEntry()
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

     private fun showLocationEntry(){
         val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
         findNavController().navigate(action)


     }
     private fun showForecastDetails(forecast: DailyForecast){

      val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment((forecast.temp, forecast.description))
        findNavController().navigate(action)

     }

     companion object{
         const val KEY_Zipcode ="key_zipcode"

         fun newInstance(zipcode: String) :WeeklyForecastFragment{
             val fragment = WeeklyForecastFragment()

             val  args =  Bundle()
             args.putString(KEY_Zipcode, zipcode)
             fragment.arguments =args
             return fragment
         }
     }

}
