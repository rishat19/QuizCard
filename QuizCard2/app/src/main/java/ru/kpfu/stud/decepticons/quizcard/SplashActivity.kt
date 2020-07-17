package ru.kpfu.stud.decepticons.quizcard

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private val APP_PREFERENCES = "settings"
    private val APP_PREFERENCES_SAVED_CARDS = "savedCards"
    private var savedCards = hashSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        var id = 0
        if (pref.contains(APP_PREFERENCES_SAVED_CARDS)) {
            savedCards = (pref.getStringSet(APP_PREFERENCES_SAVED_CARDS, null) as HashSet<String>?)!!
            savedCards.forEach {
                CardsRepository.cards.add(Card(id, it.split('~')[0], it.split('~')[1]))
                id++
            }
            CardsRepository.cards.sort()
        }
        if (id == 0) {
            CardsRepository.cards.add(Card(0, "Shark", "Акула"))
            CardsRepository.cards.add(Card(1, "Squirrel", "Белка"))
            CardsRepository.cards.add(Card(2, "Bull", "Бык"))
            CardsRepository.cards.add(Card(3, "Camel", "Верблюд"))
            CardsRepository.cards.add(Card(4, "Wolf", "Волк"))
            CardsRepository.cards.add(Card(5, "Raccoon", "Енот"))
            CardsRepository.cards.add(Card(6, "Giraffe", "Жираф"))
            CardsRepository.cards.add(Card(7, "Snake", "Змея"))
            CardsRepository.cards.add(Card(8, "Kangaroo", "Кенгуру"))
            CardsRepository.cards.add(Card(9, "Whale", "Кит"))
            CardsRepository.cards.add(Card(10, "Cow", "Корова"))
            CardsRepository.cards.add(Card(11, "Cat", "Кошка"))
            CardsRepository.cards.add(Card(12, "Rabbit", "Кролик"))
            CardsRepository.cards.add(Card(13, "Chicken", "Курица"))
            CardsRepository.cards.add(Card(14, "Lion", "Лев"))
            CardsRepository.cards.add(Card(15, "Frog", "Лягушка"))
            CardsRepository.cards.add(Card(16, "Bear", "Медведь"))
            CardsRepository.cards.add(Card(17, "Mouse", "Мышь"))
            CardsRepository.cards.add(Card(18, "Monkey", "Обезьяна"))
            CardsRepository.cards.add(Card(19, "Sheep", "Овца"))
            CardsRepository.cards.add(Card(20, "Octopus", "Осьминог"))
            CardsRepository.cards.add(Card(21, "Penguin", "Пингвин"))
            CardsRepository.cards.add(Card(22, "Elephant", "Слон"))
            CardsRepository.cards.add(Card(23, "Dog", "Собака"))
            CardsRepository.cards.add(Card(24, "Tiger", "Тигр"))
            CardsRepository.cards.add(Card(25, "Seal", "Тюлень"))
            CardsRepository.cards.add(Card(26, "Duck", "Утка"))
            CardsRepository.cards.add(Card(27, "Tortoise", "Черепаха"))
            CardsRepository.cards.sort()
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
