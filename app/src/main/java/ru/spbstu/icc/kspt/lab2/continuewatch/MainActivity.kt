package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.lifecycle.whenStarted
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0

    companion object {
        val NUM = "number"
    }

    init {
        lifecycleScope.launch{
            whenStarted(){
                while (true) {
                    secondsElapsed++
                    delay(1000)
                    findViewById<TextView>(R.id.textSecondsElapsed).text =
                        "Seconds elapsed: " + secondsElapsed
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        loadSeconds()
    }

    override fun onStop() {
        super.onStop()
        saveSeconds()
    }

    private fun saveSeconds(){
        val sPref = getPreferences(Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = sPref.edit()
        ed.putInt(NUM, secondsElapsed)
        ed.commit()
    }

    private fun loadSeconds(){
        val sPref = getPreferences(Context.MODE_PRIVATE)
        secondsElapsed = sPref.getInt(NUM, 0)
    }
}
