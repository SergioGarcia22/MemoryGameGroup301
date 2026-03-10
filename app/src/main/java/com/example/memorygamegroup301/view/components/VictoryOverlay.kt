package com.example.memorygamegroup301.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Componente que muestra un mensaje de felicitación cuando el jugador gana
//@param onPlayAgain Acción para jugar de nuevo (reiniciar)
//El overlay se superpone al tablero con un fondo semitransparente

@Composable
fun VictoryOverlay(
    onPlayAgain: () -> Unit,
    buttonColor: Color = Color(0xFF9C27B0)  // Color morado personalizado

) {
    // Box que cubre toda la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)), // Fondo negro semitransparente
        contentAlignment = Alignment.Center // Centra el contenido
    ) {
        // Tarjeta blanca para el mensaje
        Card(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            // Columna con todos los elementos del mensaje
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = " ¡Congratulations! ",
                    fontSize = 24.sp,
                    color = Color(0xFF9C27B0)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You have found all the matches.",
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = " ¡YOU WON! ",
                    fontSize = 30.sp,
                    color = Color(0xFF9C27B0)
                )

                Spacer(modifier = Modifier.height(24.dp))

                //BOTÓN DE JUGAR DE NUEVO
                Button(
                    onClick = onPlayAgain, // Acción para reiniciar
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor
                    )
                ) {
                    Text(
                        text = "PlAY AGAIN",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}