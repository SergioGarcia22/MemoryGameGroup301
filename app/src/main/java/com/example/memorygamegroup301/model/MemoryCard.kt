package com.example.memorygamegroup301.model

data class MemoryCard(
    val id: Int,  // no cambia durante el juego
    val imageResId: Int, //la imagen no cambia
    var isFaceUp: Boolean = false,  //Variable que puede cambiar
    var isMatched: Boolean = false //Variable que puede cambiar
)