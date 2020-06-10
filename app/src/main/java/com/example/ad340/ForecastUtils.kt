package com.example.ad340

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatForDisplay(temp: Float,tempDisplaySetting: TempDisplaySetting): String{

    return when (tempDisplaySetting){
        TempDisplaySetting.Fahrenheit -> String.format("%.2f F˚", temp)
        TempDisplaySetting.Celsius -> {
            val temp =(temp - 32f) * (5f/9f)
            String.format("%.2f C˚", temp)

        }
    }

}
fun showTempDisplaySettingDialog(context: Context, tempDisplaySettingManager :TempDisplaySettingManager) {

    val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setTitle("choose Display Units")
        .setMessage("choose which temperature units to use for the display")
        .setPositiveButton("F˚"){ _ , _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)

        }

        .setNeutralButton("C˚"){ _ , _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
        }

        .setOnDismissListener{

            Toast.makeText(context, "Setting will be take affect on app restart", Toast.LENGTH_SHORT).show()

        }
    dialogBuilder.show()


}