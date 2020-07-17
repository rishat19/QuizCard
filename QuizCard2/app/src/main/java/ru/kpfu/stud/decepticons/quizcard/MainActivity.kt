package ru.kpfu.stud.decepticons.quizcard

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private val APP_PREFERENCES = "settings"
    private val APP_PREFERENCES_SAVED_CARDS = "savedCards"
    private var savedCards = hashSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_logo.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        button_for_list_of_cards.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        button_for_quiz.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")

        button_for_list_of_cards.setOnClickListener {
            val intent = Intent(this, ListOfCardsActivity::class.java)
            startActivity(intent)
        }

        button_for_quiz.setOnClickListener {
            val intent = Intent(this, QuizLanguageSelectionActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStop() {
        super.onStop()
        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val editor = pref.edit()
        CardsRepository.cards.forEach {
            savedCards.add(it.toString())
        }
        editor.putStringSet(APP_PREFERENCES_SAVED_CARDS, savedCards)
        editor.apply()
    }

}
