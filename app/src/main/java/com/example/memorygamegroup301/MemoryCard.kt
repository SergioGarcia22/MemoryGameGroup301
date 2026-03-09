package com.example.memorygamegroup301

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class MemoryCard(
    val id: Int,
    val imageResId: Int
) {

    var isFaceUp by mutableStateOf(false)
    var isMatched by mutableStateOf(false)

}
