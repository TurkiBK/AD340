 package com.example.ad340.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*
import com.example.ad340.api.DailyForecast
import com.example.ad340.api.WeeklyForecast
import com.google.android.material.floatingactionbutton.FloatingActionButton

 /**
 * A simple [Fragment] subclass.
 */
class WeeklyForecastFragment : Fragment() {

     private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
     private lateinit var locationRepository: LocationRepository
     private  val forecastRepository = ForecastRepository()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forcast, container, false)
        val emptyText = view.findViewById<TextView>(R.id.emptyText)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)


        val zipcode = arguments?.getString(KEY_Zipcode) ?: ""


        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())



        val forecastList: RecyclerView = view.findViewById(R.id.forecastList) //chang to dailyForecast
        forecastList.layoutManager = LinearLayoutManager(requireContext() )
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) {forecast->
            showForecastDetails(forecast)
        }
        forecastList.adapter =dailyForecastAdapter

        // create the observer which update the UI in response to forecast updates
        val weeklyForecastObserver = Observer<WeeklyForecast>{ weeklyForecast ->
            emptyText.visibility =View.GONE
            progressBar.visibility = View.GONE


            // update our list adaptor
            dailyForecastAdapter.submitList(weeklyForecast.daily)
        }
        forecastRepository.weeklyForecast.observe(viewLifecycleOwner ,weeklyForecastObserver)

        val locationEntryButton: FloatingActionButton =view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener{
            showLocationEntry()
        }

      locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location>{savedLocation ->
            when(savedLocation){
                is Location.Zipcode ->{
                    progressBar.visibility = View.VISIBLE

                    forecastRepository.loadWeeklyForecast(savedLocation.zipcode)
            }

            }

        }
        locationRepository.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)

        return view
    }

     private fun showLocationEntry(){
         val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
         findNavController().navigate(action)


     }
     private fun showForecastDetails(forecast: DailyForecast){
    val temp = forecast.temp.max
    val description =forecast.weather[0].description
    val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(temp,description)
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
