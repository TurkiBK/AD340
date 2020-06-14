package com.example.ad340

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

sealed class Location{
    data class Zipcode(val zipcode: String): Location()
}
private const val KEY_ZIPCODE ="key_zipcode"


class LocationRepository (context: Context) {
    private val preferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE)

    private val _savedLocation: MutableLiveData<Location> = MutableLiveData()
    val savedLocation: LiveData<Location> = _savedLocation

    init {
        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key != KEY_ZIPCODE) return@registerOnSharedPreferenceChangeListener
            broadcastSaveZipcode()
        }

        broadcastSaveZipcode()

    }

    fun savedLocation(location: Location) {
        when (location) {
            is Location.Zipcode -> preferences.edit().putString(
                KEY_ZIPCODE,
                location.zipcode
            ).apply()

        }
    }

    private fun broadcastSaveZipcode() {

        val zipcode = preferences.getString(KEY_ZIPCODE, "")
        if (zipcode != null && zipcode.isNotBlank()) {
            _savedLocation.value = Location.Zipcode(zipcode)

        }
    }


}