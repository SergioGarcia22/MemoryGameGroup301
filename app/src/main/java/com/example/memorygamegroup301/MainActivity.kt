package com.example.memorygamegroup301

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.memorygamegroup301.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MemoryGameScreen(viewModel)
        }
    }
}

@Composable
fun MemoryGameScreen(viewModel: GameViewModel) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Movimientos: ${viewModel.moves}")

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(16.dp)
        ) {

            items(viewModel.cards.size) { index ->

                val card = viewModel.cards[index]

                MemoryCardView(card) {

                    viewModel.onCardClicked(card)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.resetGame() }) {
            Text("Reiniciar")
        }
    }
}
@Composable
fun MemoryCardView(card: MemoryCard, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(90.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        if (card.isFaceUp || card.isMatched ) {

            Image(
                painter = painterResource(card.imageResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

        } else {

            Card(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text("?")
                }
            }
        }
    }
}