package com.example.memorygamegroup301.model

data class MemoryCard(
    val id: Int,  // It doesn't change during the game
    val imageResId: Int, //the image does not change
    var isFaceUp: Boolean = false,  //Variable that can change
    var isMatched: Boolean = false //Variable that can change
)