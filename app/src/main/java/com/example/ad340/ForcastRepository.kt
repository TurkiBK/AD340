package com.example.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {

        private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
        val weeklyForecast : LiveData<List<DailyForecast>> = _weeklyForecast


         fun loadForecast(zipcode: String){

            val randomValues = List (7){ Random.nextFloat().rem(100)*100}
            val forecastItems = randomValues.map { temp ->
                DailyForecast(temp, getTempDescription(temp))

            }
            _weeklyForecast.setValue(forecastItems)
}
            private fun getTempDescription(temp :Float) : String{
                   //     return if(temp <75) "It's cold" else "Its great"

                    return when (temp){
                        in Float.MIN_VALUE.rangeTo(0f)-> "anythimg below 0dosen't make sense"
                        in 0f.rangeTo(32f)->"Way too cold"
                        in 32f.rangeTo(55f)->"colder tha I woild prefer"
                        in 55f.rangeTo(65f)->"Getting better"
                        in 65f.rangeTo(80f)->"That's the sweet spot!"
                        in 80f.rangeTo(90f)->"Getting a littelwarm"
                        in 90f.rangeTo(100f)->"Where is the A/c?"
                        in 100f.rangeTo(Float.MAX_VALUE)->"What is this .Arizona"
                        else  -> "Does not copmpute"



                    }
}
}