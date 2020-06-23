
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
import com.example.ad340.*
import com.example.ad340.api.CurrentWeather
import com.google.android.material.floatingactionbutton.FloatingActionButton

 /**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment : Fragment() {

     private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
     private lateinit var locationRepository: LocationRepository
     private  val forecastRepository = ForecastRepository()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forcast, container, false)
        val locationName=view.findViewById<TextView>(R.id.locationName)
        val  tempText  = view.findViewById<TextView>(R.id.tempText)
        val emptyText = view.findViewById<TextView>(R.id.emptyText)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

       // val zipcode = arguments?.getString(KEY_Zipcode) ?: ""

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())



        // create the observer which update the UI in response to forecast updates
        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
           emptyText.visibility =View.GONE
            progressBar.visibility = View.GONE
            locationName.visibility = View.VISIBLE
            tempText.visibility = View.VISIBLE

            locationName.text = weather.name
            tempText.text= formatForDisplay(weather.forecast.temp,tempDisplaySettingManager.getTempDisplaySetting())
        }
        forecastRepository.currentWeather.observe(viewLifecycleOwner,currentWeatherObserver)

        val locationEntryButton: FloatingActionButton =view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener{
            showLocationEntry()
        }

        locationRepository= LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when (savedLocation) {
                is Location.Zipcode -> {
                    progressBar.visibility = View.VISIBLE

                }
            }
        }

        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)


        return view
    }

   private fun showLocationEntry(){
       val action =CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
       findNavController().navigate(action)

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
