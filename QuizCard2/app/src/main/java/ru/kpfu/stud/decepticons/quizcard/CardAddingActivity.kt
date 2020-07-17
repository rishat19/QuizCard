package ru.kpfu.stud.decepticons.quizcard

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_card_adding.*

class CardAddingActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private val APP_PREFERENCES = "settings"
    private val APP_PREFERENCES_SAVED_CARDS = "savedCards"
    private var savedCards = hashSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_adding)

        tv_new_card.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        tv_english_text.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        tv_russian_text.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        et_english_text.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        et_russian_text.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")

        val englishTextRegex = Regex(pattern = "^(?![\\s\\-,!?.])[A-Za-z\\s\\-,!?.]+(?<![\\s\\-,])\$")
        val russianTextRegex = Regex(pattern = "^(?![\\s\\-,!?.])[А-Яа-я\\s\\-,!?.]+(?<![\\s\\-,])\$")

        button_for_save.setOnClickListener {
            if (englishTextRegex.containsMatchIn(et_english_text.text) && russianTextRegex.containsMatchIn(et_russian_text.text)) {
                if (et_english_text.text.length <= 100 && et_russian_text.text.length <= 120) {
                    val card = Card(CardsRepository.cards.size, et_english_text.text.toString(), et_russian_text.text.toString())
                    if (!CardsRepository.cards.contains(card)) {
                        CardsRepository.cards.add(card)
                        CardsRepository.cards.sort()
                        et_english_text.text.clear()
                        et_russian_text.text.clear()
                        Toast.makeText(this, "Новая карточка успешно сохранена", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Ошибка! Такая карточка уже существует", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Ошибка! Слишком длинный текст. Допустимо не более 100 символов", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ошибка! Текст не введён или введён некорректно", Toast.LENGTH_SHORT).show()
            }
        }

        button_back.setOnClickListener {
            val intent = Intent(this, ListOfCardsActivity::class.java)
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
