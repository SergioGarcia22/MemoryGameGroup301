package com.example.memorygamegroup301.viewmodel

import androidx.lifecycle.ViewModel
import com.example.memorygamegroup301.MemoryCard
import com.example.memorygamegroup301.R
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
class GameViewModel : ViewModel() {

    val cards = mutableStateListOf<MemoryCard>()

    var moves by mutableIntStateOf(0)
        private set

    private var firstSelectedCard: MemoryCard? = null
    private var secondSelectedCard: MemoryCard? = null

    private var isChecking = false

    init {
        resetGame()
    }

    fun resetGame() {
        cards.clear()
        moves = 0
        firstSelectedCard = null
        secondSelectedCard = null

        val icons = listOf(
            R.drawable.ic_dead,
            R.drawable.ic_fool,
            R.drawable.ic_high_priestess,
            R.drawable.ic_moon,
            R.drawable.ic_tower,
            R.drawable.ic_world
        )

        val pairList = icons + icons

        pairList.forEachIndexed { index, icon ->
            cards.add(MemoryCard(index, icon))
        }

        cards.shuffle()
    }

    fun onCardClicked(card: MemoryCard) {

        if (card.isFaceUp || card.isMatched || isChecking) return

        if (firstSelectedCard != null && secondSelectedCard != null) {
            return
        }

        card.isFaceUp = true

        if (firstSelectedCard == null) {
            firstSelectedCard = card
        } else {
            secondSelectedCard = card
            moves++

            checkForMatch()
        }
    }

    private fun checkForMatch() {

        val first = firstSelectedCard
        val second = secondSelectedCard

        if (first != null && second != null) {

            if (first.imageResId == second.imageResId) {

                first.isMatched = true
                second.isMatched = true

                firstSelectedCard = null
                secondSelectedCard = null

            } else {

                isChecking = true

                viewModelScope.launch {

                    delay(1000)

                    first.isFaceUp = false
                    second.isFaceUp = false

                    firstSelectedCard = null
                    secondSelectedCard = null

                    isChecking = false
                }
            }
        }
    }

    fun hasWon(): Boolean {
        return cards.all { it.isMatched }
    }
}