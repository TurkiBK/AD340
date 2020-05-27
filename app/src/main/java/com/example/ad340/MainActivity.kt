package com.example.ad340

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
//import androidx.appcompat.app.
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.details.ForecastDetailsActivity
import java.util.*

class MainActivity : AppCompatActivity() {

   private  val forecastRepository = ForecastRepository()
   private lateinit var tempDisplaySettingManager: TempDisplaySettingManager


    // region Set up Metods
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    tempDisplaySettingManager = TempDisplaySettingManager(this)

    val enterButton: Button = findViewById(R.id.enterButton)
    val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)

    enterButton.setOnClickListener {
      val enteredMsg = zipcodeEditText.text.toString()
      //  Toast.makeText( this ,"Button was Clicked !!",Toast.LENGTH_SHORT).show()
      // Toast.makeText( this ,"User enterd: $enteredMsg",Toast.LENGTH_SHORT).show()

      if (enteredMsg.length != 5) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()

      } else {

        //Toast.makeText(this, enteredMsg, Toast.LENGTH_SHORT).show()
          forecastRepository.loadForecast(enteredMsg)

      }

    }

        val forecastList: RecyclerView = findViewById(R.id.forecastList) //chang to dailyForecast
             forecastList.layoutManager = LinearLayoutManager(this )
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) {forecast->
            showForecastDetails(forecast)

        }
            forecastList.adapter =dailyForecastAdapter

         // create the observer which update the UI in response to forecast updates
      val weeklyForecastObserver =Observer<List<DailyForecast>>{forecastItems ->
          // update our list adaptor
        //Toast.makeText(this,"Loaded Items", Toast.LENGTH_SHORT).show()
          dailyForecastAdapter.submitList(forecastItems)

      }
      forecastRepository.weeklyForecast.observe(this ,weeklyForecastObserver)
    }

    private fun showForecastDetails(forecast: DailyForecast){

        val forecastDetailsIntent = Intent(this, ForecastDetailsActivity :: class.java)
            forecastDetailsIntent.putExtra("key_temp", forecast.temp)
            forecastDetailsIntent.putExtra("key_description", forecast.description)
            startActivity(forecastDetailsIntent)

    }

}


