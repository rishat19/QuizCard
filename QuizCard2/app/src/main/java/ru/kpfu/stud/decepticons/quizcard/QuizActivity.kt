package ru.kpfu.stud.decepticons.quizcard

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.*

class QuizActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private val APP_PREFERENCES = "settings"
    private val APP_PREFERENCES_SAVED_CARDS = "savedCards"
    private var savedCards = hashSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        tv_translated.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        tv_card_text.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        et_translation.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        tv_language_left.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")
        tv_language_right.typeface = Typeface.createFromAsset(assets, "fonts/futura_pt_bold.ttf")

        val flag = intent.getBooleanExtra("FLAG", true)
        var quizQueue: Queue<Card> = LinkedList(CardsRepository.cards.shuffled().take(10))
        var count = 0
        var numberOfcards = 10
        if (CardsRepository.cards.size < numberOfcards) {
            numberOfcards = CardsRepository.cards.size
        }
        var countText = "Переведено $count/$numberOfcards"
        tv_translated.text = countText
        if (flag) {
            tv_card_text.text = quizQueue.peek().engText
            et_translation.setHint(R.string.translate2)
        } else {
            tv_card_text.text = quizQueue.peek().ruText
            et_translation.setHint(R.string.translate3)
            tv_language_left.setBackgroundResource(R.drawable.text_view_green)
            tv_language_left.setText(R.string.russian)
            tv_language_right.setBackgroundResource(R.drawable.text_view_red)
            tv_language_right.setText(R.string.english)
        }

        button_for_check.setOnClickListener {
            if (flag) {
                if (et_translation.text.toString().toLowerCase(Locale.ROOT) == quizQueue.peek().ruText.toLowerCase(Locale.ROOT)) {
                    count++
                    quizQueue.remove()
                    Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show()
                } else {
                    quizQueue.add(quizQueue.poll())
                    Toast.makeText(this, "Ошибка!", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (et_translation.text.toString().toLowerCase(Locale.ROOT) == quizQueue.peek().engText.toLowerCase(Locale.ROOT)) {
                    count++
                    quizQueue.remove()
                    Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show()
                } else {
                    quizQueue.add(quizQueue.poll())
                    Toast.makeText(this, "Ошибка!", Toast.LENGTH_SHORT).show()
                }
            }
            if (quizQueue.isEmpty()) {
                val intent = Intent(this, QuizFinishActivity::class.java)
                startActivity(intent)
            } else {
                countText = "Переведено $count/$numberOfcards"
                tv_translated.text = countText
                et_translation.text.clear()
                if (flag) {
                    tv_card_text.text = quizQueue.peek().engText
                } else {
                    tv_card_text.text = quizQueue.peek().ruText
                }
            }
        }

        button_for_translate.setOnClickListener {
            if (flag) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    quizQueue.peek().ruText,
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    quizQueue.peek().engText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
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
