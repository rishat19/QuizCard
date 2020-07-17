package ru.kpfu.stud.decepticons.quizcard

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz_language_selection.*

class QuizLanguageSelectionActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private val APP_PREFERENCES = "settings"
    private val APP_PREFERENCES_SAVED_CARDS = "savedCards"
    private var savedCards = hashSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_language_selection)

        if (CardsRepository.cards.size == 0) {
            Toast.makeText(this, "Ошибка! У вас нет карточек", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        tv_quiz.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        tv_english_1.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        tv_english_2.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        tv_russian_1.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        tv_russian_2.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")

        button_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        button_eng_to_ru.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java).putExtra("FLAG", true)
            startActivity(intent)
        }

        button_ru_to_eng.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java).putExtra("FLAG", false)
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
