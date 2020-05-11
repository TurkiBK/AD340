package com.example.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val enterButton    : Button   = findViewById(R.id.enterButton)
        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)



        enterButton.setOnClickListener {
           val enteredMsg = zipcodeEditText.text.toString()
           //  Toast.makeText( this ,"Button was Clicked !!",Toast.LENGTH_SHORT).show()
           // Toast.makeText( this ,"User enterd: $enteredMsg",Toast.LENGTH_SHORT).show()

            if (enteredMsg.length !=5) {
             Toast.makeText(this ,"error",Toast.LENGTH_SHORT).show()

            }else {

                Toast.makeText(this ,enteredMsg,Toast.LENGTH_SHORT).show()

            }


        }







    }


}