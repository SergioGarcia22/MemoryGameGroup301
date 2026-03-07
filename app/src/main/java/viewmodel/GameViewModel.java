package com.example.memorygamegroup301.viewmodel

import androidx.lifecycle.ViewModel
import com.example.memorygamegroup301.MemoryCard

class GameViewModel : ViewModel() {

    // Esta es la lista que mantendrá el estado del tablero
    private val _cards = mutableListOf<MemoryCard>()
    val cards: List<MemoryCard> get() = _cards

    init {
        // Aquí  se llama a una función para inicializar el tablero
        resetGame()
    }

    fun resetGame() {
        _cards.clear()

        // 1. Las 6 imagenes en la carpeta drawable
        val icons = listOf(
                R.drawable.ic_dead, R.drawable.ic_fool,
                R.drawable.ic_high_priestess, R.drawable.ic_moon,
                R.drawable.ic_tower, R.drawable.ic_world
        )

        // 2. Duplica los iconos para tener los pares
        val pairList = icons + icons

        // 3. Crea las cartas con un ID único y las añade a la lista
        pairList.forEachIndexed { index, iconId ->
                _cards.add(MemoryCard(id = index, imageResId = iconId))
        }

        // 4. Mezcla (shuffle) para que el tablero sea aleatorio cada vez
        _cards.shuffle()
    }

    fun onCardClicked(card: MemoryCard) {
        // Lógica de "qué pasa cuando toca una carta"
    }
}