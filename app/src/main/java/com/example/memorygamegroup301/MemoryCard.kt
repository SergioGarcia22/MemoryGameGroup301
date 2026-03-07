package com.example.memorygamegroup301

data class MemoryCard(
    val id: Int,             // Identificador único para saber qué carta es
    val imageResId: Int, // Aquí se guarda el ID de la imagen
    var isFaceUp: Boolean = false,   // Verifica si boca arriba
    var isMatched: Boolean = false   // Verifica si fue emparejada con éxito
)
