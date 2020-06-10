package com.example.ad340.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.ad340.R
import com.example.ad340.TempDisplaySettingManager
import com.example.ad340.formatForDisplay

class ForecastDetailsFragment : Fragment() {

    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout=  inflater.inflate(R.layout.fragment_forecast_details, container,false)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val tempText =layout.findViewById<TextView>(R.id.tempText)
        val descriptionText =layout.findViewById<TextView>(R.id.descriptionText)


        tempText.text = formatForDisplay(args.temp,tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = args.description

        return layout
    }

    }

