package com.example.homeworksandroid

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var n1 = 0
    var n2 = 0

    private val UI_MODE_KEY = "uiMode"
    private val UI_MODE_AUTO_VALUE = "AUTO"
    private val UI_MODE_DAY_VALUE = "DAY"
    private val UI_MODE_NIGHT_VALUE = "NIGHT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sp = getSharedPreferences("APP", Context.MODE_PRIVATE)

        btnPlus.setOnClickListener {
            checkValues()
            tietResult.text?.clear()
            tietResult.text?.append((n1 + n2).toString())
        }

        btnMinus.setOnClickListener {
            checkValues()
            tietResult.text?.clear()
            tietResult.text?.append((n1 - n2).toString())
        }

        btnMultiply.setOnClickListener {
            checkValues()
            tietResult.text?.clear()
            tietResult.text?.append((n1 * n2).toString())
        }

        btnDivide.setOnClickListener {
            checkValues()
            if (n2 == 0) {
                Toast.makeText(
                    this,
                    getString(R.string.main_activity_zero_divide_warning),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            tietResult.text?.clear()
            tietResult.text?.append((n1 / n2).toString())
        }

        when (sp.getString(UI_MODE_KEY, "AUTO")) {
            UI_MODE_AUTO_VALUE -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
            UI_MODE_DAY_VALUE -> setDefaultNightMode(MODE_NIGHT_NO)
            UI_MODE_NIGHT_VALUE -> setDefaultNightMode(MODE_NIGHT_YES)
        }

        when (getDefaultNightMode()) {
            MODE_NIGHT_YES -> mbtgDayNight.check(btnNight.id)
            MODE_NIGHT_NO -> mbtgDayNight.check(btnDay.id)
            else -> mbtgDayNight.check(btnAuto.id)
        }

        mbtgDayNight.addOnButtonCheckedListener { _, id, _ ->
            when (id) {
                btnDay.id -> {
                    setDefaultNightMode(MODE_NIGHT_NO)
                    sp.edit().apply {
                        putString(UI_MODE_KEY, UI_MODE_DAY_VALUE)
                        apply()
                    }
                }
                btnNight.id -> {
                    setDefaultNightMode(MODE_NIGHT_YES)
                    sp.edit().apply {
                        putString(UI_MODE_KEY, UI_MODE_NIGHT_VALUE)
                        apply()
                    }
                }
                btnAuto.id -> {
                    setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                    sp.edit().apply {
                        putString(UI_MODE_KEY, UI_MODE_AUTO_VALUE)
                        apply()
                    }
                }
            }
        }
    }

    private fun checkValues() {
        n1 = if (tietFirstNumber.text.toString() == "") {
            0
        } else
            tietFirstNumber.text?.toString()?.toInt() ?: 0
        n2 = if (tietSecondNumber.text.toString() == "") {
            0
        } else
            tietSecondNumber.text?.toString()?.toInt() ?: 0
    }
}