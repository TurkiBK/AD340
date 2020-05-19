package com.example.ad340

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
import java.util.*

class MainActivity : AppCompatActivity() {

   private  val forecastRepository = ForecastRepository()


    // rrgion Set up Metods
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

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

        val forecastList :RecyclerView = findViewById(R.id.forecastList)
             forecastList.layoutManager = LinearLayoutManager(this )
        val dailyForecastAdapter = DailyForecastAdapter() {forecastItem ->
        val msg = getString(R.string.forecast_clicked_format, forecastItem.temp, forecastItem.description)
            Toast.makeText(this ,msg,Toast.LENGTH_SHORT).show()

        }
            forecastList.adapter =dailyForecastAdapter


      val weeklyForecastObserver =Observer<List<DailyForecast>>{forecastItems ->
          // update our list adaptor
        //Toast.makeText(this,"Loaded Items", Toast.LENGTH_SHORT).show()
          dailyForecastAdapter.submitList(forecastItems)

      }
      forecastRepository.weeklyForecast.observe(this ,weeklyForecastObserver)


  }

}


