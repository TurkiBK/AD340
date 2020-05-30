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
import com.example.ad340.forecast.CurrentForecastFragment
import com.example.ad340.location.LocationEntryFragment
import java.util.*

class MainActivity : AppCompatActivity() ,AppNavigator{


   private lateinit var tempDisplaySettingManager: TempDisplaySettingManager


    // region Set up Metods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    tempDisplaySettingManager = TempDisplaySettingManager(this)


        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer,LocationEntryFragment())
            .commit()
    }

    override fun navigateToCurrentForecast(zipcode: String) {
             supportFragmentManager
             .beginTransaction()
             .replace(R.id.fragmentContainer,CurrentForecastFragment.newInstance(zipcode))
             .commit()

    }

    override fun navigateToLocationEntry() {

            supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,LocationEntryFragment())
            .commit()
    }
}


