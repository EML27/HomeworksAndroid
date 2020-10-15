package com.example.homeworksandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.homeworks.common.Constants
import com.example.homeworks.data.CalculatorApi
import com.example.homeworks.store.*
import com.example.homeworks.store.siteEffects.WriteNumberSiteEffect
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG_PROGRAMMATICALLY = "programmatically"
        private const val TAG_EMPTY = ""
    }

    private val relay = PublishRelay.create<MainActivityNews>()


    private val store =
        MainActivityStore(
            listOf(WriteNumberSiteEffect(CalculatorApi(), relay)),
            relay
        )

    private var TAG: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        store.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::renderPage)

        store.newsRelay
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::renderNews)

        setupTextChangedListeners()
    }

    private fun renderPage(state: MainActivityState) {
        state.values?.let {
            TAG = TAG_PROGRAMMATICALLY

            if (tietNum1.text.toString() != it.first) {
                tietNum1.setText(it.first)
            }
            if (tietNum2.text.toString() != it.second) {
                tietNum2.setText(it.second)
            }
            tietSum.setText(it.third.toString())

            TAG = TAG_EMPTY
        }

        progressBar.isVisible = state.isLoading
    }

    private fun renderNews(news: MainActivityNews) {
        when (news) {
            is ShowComputationError -> buildAlertDialog(news.error)
        }
    }

    private fun setupTextChangedListeners() {
        tietNum1.doAfterTextChanged { text ->
            updateValues(Pair(Constants.FIRST_TERM, text.toString()))
        }

        tietNum2.doAfterTextChanged { text ->
            updateValues(Pair(Constants.SECOND_TERM, text.toString()))
        }

        tietSum.doAfterTextChanged { text ->
            updateValues(Pair(Constants.SUM, text.toString()))
        }
    }

    private fun updateValues(newValue: Pair<String, String>) {
        if (TAG != TAG_PROGRAMMATICALLY) {
            store.actionRelay.onNext(
                WriteValues(newValue)
            )
        }
    }

    private fun buildAlertDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Ошибка!")
            .setMessage(message)
            .setPositiveButton("Ясно") { _, _ -> }
            .show()
    }
}