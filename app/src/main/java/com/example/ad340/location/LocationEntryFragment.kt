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
import com.example.ad340.R

/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_locationentry_entry, container, false)

        val enterButton: Button = view.findViewById(R.id.enterButton)
        val zipcodeEditText: EditText = view.findViewById(R.id.zipcodeEditText)

        enterButton.setOnClickListener {
            val enteredMsg = zipcodeEditText.text.toString()
            //  Toast.makeText( this ,"Button was Clicked !!",Toast.LENGTH_SHORT).show()
            // Toast.makeText( this ,"User entered: $enteredMsg",Toast.LENGTH_SHORT).show()

            if (enteredMsg.length != 5) {
                Toast.makeText(requireContext(), "Pleas enter vaild zipcode", Toast.LENGTH_SHORT).show()

            } else {
                //Toast.makeText(this, enteredMsg, Toast.LENGTH_SHORT).show()
               // forecastRepository.loadForecast(enteredMsg)
               findNavController().navigateUp()


                }

        }

        return view

    }

}
