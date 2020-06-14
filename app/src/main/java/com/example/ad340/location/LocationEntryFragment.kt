package com.example.ad340.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ad340.Location
import com.example.ad340.LocationRepository
import com.example.ad340.R

/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {


    private lateinit var  locationRepository: LocationRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationRepository = LocationRepository(requireContext())
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_locationentry_entry, container, false)

        val enterButton: Button = view.findViewById(R.id.enterButton)
        val zipcodeEditText: EditText = view.findViewById(R.id.zipcodeEditText)

        enterButton.setOnClickListener {
            val enteredMsg = zipcodeEditText.text.toString()
            if (enteredMsg.length != 5) {
                Toast.makeText(requireContext(), "Pleas enter vaild zipcode", Toast.LENGTH_SHORT).show()
            } else {
                locationRepository.savedLocation(Location.Zipcode(enteredMsg ))
                findNavController().navigateUp()


                }

        }

        return view

    }

}
