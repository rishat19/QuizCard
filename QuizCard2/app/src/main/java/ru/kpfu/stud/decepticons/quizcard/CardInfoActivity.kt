package ru.kpfu.stud.decepticons.quizcard

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_card_info.*

class CardInfoActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private val APP_PREFERENCES = "settings"
    private val APP_PREFERENCES_SAVED_CARDS = "savedCards"
    private var savedCards = hashSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_info)

        tv_language.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        tv_card_text.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        button_for_translate.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")

        val id = intent?.extras?.getInt("ID", -1) ?: 0
        val card = CardsRepository.cards.find { it.id == id }
        showEnglishVersion(card)
        var languageFlag = false

        button_for_translate.setOnClickListener {
            languageFlag = !languageFlag
            if (languageFlag) {
                showRussianVersion(card)
            } else {
                showEnglishVersion(card)
            }
        }

        button_back.setOnClickListener {
            val intent = Intent(this, ListOfCardsActivity::class.java)
            startActivity(intent)
        }
        
        button_for_delete.setOnClickListener {
            CardsRepository.cards.remove(card)
            Toast.makeText(this, "Карточка удалена", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListOfCardsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showEnglishVersion(card: Card?) {
        tv_language.setText(R.string.english)
        tv_language.setBackgroundResource(R.drawable.text_view_red)
        tv_card_text.text = card?.engText
    }

    private fun showRussianVersion(card: Card?) {
        tv_language.setText(R.string.russian)
        tv_language.setBackgroundResource(R.drawable.text_view_green)
        tv_card_text.text = card?.ruText
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
