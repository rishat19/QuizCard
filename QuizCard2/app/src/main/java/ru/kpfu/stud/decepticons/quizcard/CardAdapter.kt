package ru.kpfu.stud.decepticons.quizcard

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(
    private val list: List<Card>,
    private val action: (Int) -> Unit
) : RecyclerView.Adapter<CardItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItem =
        CardItem.create(parent, action)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CardItem, position: Int) {
        holder.bind(list[position])
    }
}