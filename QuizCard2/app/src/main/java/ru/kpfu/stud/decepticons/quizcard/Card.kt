package ru.kpfu.stud.decepticons.quizcard

class Card(val id: Int, val engText: String, val ruText: String) : Comparable<Card>{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        val card = other as Card
        if (engText != card.engText) return false
        return true
    }

    override fun hashCode(): Int {
        var result = engText.hashCode()
        result = 31 * result + ruText.hashCode()
        return result
    }

    override fun toString(): String {
        return "$engText~$ruText"
    }

    override fun compareTo(other: Card): Int {
        return this.engText.compareTo(other.engText)
    }

}

