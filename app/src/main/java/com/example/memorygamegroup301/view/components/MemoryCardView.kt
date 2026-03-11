package com.example.memorygamegroup301.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.memorygamegroup301.model.MemoryCard
import com.example.memorygamegroup301.R


//Componente reutilizable para representar una carta individual
//@param card La carta a mostrar (datos del modelo)
//@param onClick Acción a ejecutar cuando se hace clic en la carta

//Este componente es "stateless" (sin estado)
     //Recibe todo lo que necesita por parámetros
     //No mantiene estado interno
     //Solo renderiza basado en los parámetros
@Composable
fun MemoryCardView(
    card: MemoryCard,
    onClick: () -> Unit
) {

    // Box: Layout que centra su contenido
    Box(
        modifier = Modifier
            .padding(8.dp) // Margen entre cartas
            .size(90.dp) //// Tamaño fijo 90x90 dp
            .clickable { onClick() }, //// Hace el elemento clickeable
        contentAlignment = Alignment.Center
    ) {

        // if-else: Renderizado condicional
        if (card.isFaceUp || card.isMatched) {
            // CARTA VOLTEADA: Mostramos la imagen
            Image(
                painter = painterResource(id = card.imageResId), // Carga la imagen
                contentDescription = null, // // Sin descripción (decorativo)
                modifier = Modifier.fillMaxSize() //// Ocupa todo el espacio
            )

        } else {
// CARTA BOCA ABAJO: Mostramos un la imagen card_back en una tarjeta
            Image(
                painter = painterResource(id = R.drawable.card_back),
                contentDescription = "Card Back",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}