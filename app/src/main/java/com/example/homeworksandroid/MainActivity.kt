package com.example.homeworksandroid

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.max


class MainActivity : AppCompatActivity() {

    var isColorChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowHide.setOnClickListener { changeHiddenButtonVisibility() }
        btnHidden.setOnClickListener { changeBackground() }
    }

    private fun changeHiddenButtonVisibility() {
        if (btnHidden.isVisible) {
            applyHiddenBtnRotation(360f).apply {
                doOnEnd { btnHidden.visibility = View.GONE }
                start()
            }
        } else {
            applyHiddenBtnRotation(-360f).apply {
                doOnStart { btnHidden.visibility = View.VISIBLE }
                start()
            }
        }
    }

    private fun applyHiddenBtnRotation(degrees: Float) = ValueAnimator.ofFloat(degrees).apply {
        duration = 1000
        addUpdateListener { animation ->
            btnHidden.rotation = animation.animatedValue as Float
        }

    }

    private fun changeBackground() {
        val viewRoot = findViewById<ViewGroup>(android.R.id.content).rootView
        val locationOfButton = intArrayOf(0, 0)
        btnHidden.getLocationOnScreen(locationOfButton)
        val cx: Int = locationOfButton[0] + btnHidden.width / 2
        val cy: Int = locationOfButton[1] + btnHidden.height / 2
        val finalRadius: Int = max(viewRoot.width, viewRoot.height)

        val anim =
            ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0f, finalRadius.toFloat())
                .apply {
                    duration = 1000
                }
        if (isColorChanged) {
            viewRoot.setBackgroundColor(getColor(R.color.design_default_color_background))
        } else {
            viewRoot.setBackgroundColor(getColor(R.color.colorPrimaryDark))
        }
        anim.start()
        isColorChanged = !isColorChanged
    }
}
