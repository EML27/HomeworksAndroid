package com.example.homeworksandroid

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowHide.setOnClickListener { changeHiddenButtonVisibility() }
    }

    private fun changeHiddenButtonVisibility() {
        if (btnHidden.isVisible) {
            btnHidden.visibility = View.GONE
        } else {
            btnHidden.visibility = View.VISIBLE
        }
    }
}