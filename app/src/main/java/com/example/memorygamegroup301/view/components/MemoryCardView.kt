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


//Reusable component to represent an individual card
//@param card The card to display (model data)
//@param onClick Action to execute when the card is clicked

//This component is stateless
    //It receives everything it needs as parameters
    //It does not maintain internal state
    //It only renders based on the parameters
@Composable
fun MemoryCardView(
    card: MemoryCard,
    onClick: () -> Unit
) {

    // Box: Layout that centers its content
    Box(
        modifier = Modifier
            .padding(8.dp) // Margin between cards
            .size(90.dp) //// Fixed size 90x90 dp
            .clickable { onClick() }, //// Makes the element clickable
        contentAlignment = Alignment.Center
    ) {

        // if-else: Conditional rendering
        if (card.isFaceUp || card.isMatched) {
            // REVERSED CARD: We show the image
            Image(
                painter = painterResource(id = card.imageResId), // Load image
                contentDescription = null, // // No description (decorative)
                modifier = Modifier.fillMaxSize() //// It occupies all the space
            )

        } else {
// FACE DOWN CARD: We show a ? on a card
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "?",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }
    }
}