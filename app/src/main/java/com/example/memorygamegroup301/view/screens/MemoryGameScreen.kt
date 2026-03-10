package com.example.memorygamegroup301.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygamegroup301.components.MemoryCardView
import com.example.memorygamegroup301.components.VictoryOverlay
import com.example.memorygamegroup301.viewmodel.GameViewModel


//PANTALLA PRINCIPAL DEL JUEGO

//Indica que esta función puede ser llamada por Compose
//Las funciones @Composable solo pueden llamarse desde otras @Composable
@Composable
fun MemoryGameScreen(viewModel: GameViewModel) { // Observamos los estados del ViewModel

    // collectAsState(): Convierte un StateFlow en un State de Compose
    //Cuando el StateFlow cambia, la UI se recompondrá automáticamente
    val cards by viewModel.cards.collectAsState() // Lista de cartas
    val moves by viewModel.moves.collectAsState() // Contador de movimientos
    val gameWon by viewModel.gameWon.collectAsState() // Estado de victoria

    //color morado personalizado
    val colorMorado = Color(0xFF9C27B0)

    // Box: Layout que puede mostrar el mensaje encima del tablero
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            // Contador de movimientos
            Text(
                text = "Movements: $moves", //texto dinámico
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // TABLERO DE JUEGO
            LazyVerticalGrid(
                columns = GridCells.Fixed(3), //3 columnas fijas
                modifier = Modifier
                    .weight(1f) //ocupa to espacio disponible
                    .padding(16.dp) //margen
            ) {

                //items: Itera sobre la lista de cartas
                items(cards.size) { index ->

                    val card = cards[index] //carta actual

                    //Componente individual de carta
                    MemoryCardView(
                        card = card,
                        onClick = { viewModel.onCardClicked(card) } //evento click
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de reinicio
            Button(
                onClick = { viewModel.resetGame() },//Acción al hacer clic
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorMorado
                )
            ) {
                //texto dinamico: cambia según el estado del juego
                Text(
                    text = if (gameWon) " Play Again " else "Restart",
                    fontSize = if (gameWon) 14.sp else 12.sp
                )
            }
        }

        // Mensaje de victoria
        //Sólo se muestra si gameWon es true
        if (gameWon) {
            VictoryOverlay(
                onPlayAgain = { viewModel.resetGame() }, //Reiniciar al hacer clic
                buttonColor = colorMorado
            )
        }
    }
}