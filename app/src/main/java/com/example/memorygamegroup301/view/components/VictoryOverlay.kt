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

//Component that displays a congratulatory message when the player wins
//@param onPlayAgain Action to play again (reset)
//The overlay overlaps the board with a semi-transparent background

@Composable
fun VictoryOverlay(
    onPlayAgain: () -> Unit,
    buttonColor: Color = Color(0xFF9C27B0)  // Custom purple color

) {
    // Box that covers the entire screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)), // Semi-transparent black background
        contentAlignment = Alignment.Center // Center the content
    ) {
        // White card for message
        Card(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            // Column with all the message elements
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

                //PLAY AGAIN BUTTON
                Button(
                    onClick = onPlayAgain, // Action to restart
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