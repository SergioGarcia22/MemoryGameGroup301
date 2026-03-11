package com.example.memorygamegroup301.viewmodel

import androidx.lifecycle.ViewModel
import com.example.memorygamegroup301.model.MemoryCard
import com.example.memorygamegroup301.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.os.Handler
import android.os.Looper


//ViewModel: Game logic layer
// MVVM: The ViewModel acts as a bridge between the Model (data) and the View (UI)

//StateFlow: Observable that emits values when the state changes
//StateFlow: Observable that emits values when the state changes
//StateFlow: Read-only (public) version
class GameViewModel : ViewModel() {

    //GAME STATEMENT
    // List of all cards
    private val _cards = MutableStateFlow<List<MemoryCard>>(emptyList())
    val cards: StateFlow<List<MemoryCard>> = _cards.asStateFlow()

    // Move counter
    private val _moves = MutableStateFlow(0)
    val moves: StateFlow<Int> = _moves.asStateFlow()

    // Victory state - true when all cards are paired
    private val _gameWon = MutableStateFlow(false)
    val gameWon: StateFlow<Boolean> = _gameWon.asStateFlow()

    // Variables to control the game
    //Store the cards selected in the current turn
    private var firstSelectedCard: MemoryCard? = null
    private var secondSelectedCard: MemoryCard? = null
    // Flag to prevent clicks while comparing cards
    private var isProcessing = false

    // IDs of the images to be used (6 different ones)
    private val imageIds = listOf(
        R.drawable.ic_card_1,
        R.drawable.ic_card_2,
        R.drawable.ic_card_3,
        R.drawable.ic_card_4,
        R.drawable.ic_card_5,
        R.drawable.ic_card_6
    )

    //Init block: This runs when the viewmodel is created.
    //Starts the game by calling resetGame()
    init {
        resetGame()
    }

    //Restart the game with new random cards
    fun resetGame() {
        // Create pairs of cards (2 of each image)
        val cardList = mutableListOf<MemoryCard>()
        var id = 0 //Unique ID for each card

        // We create 2 copies of each image (the pairs)
        repeat(2) {

            //We go through all the images
            for (i in imageIds.indices) {
                cardList.add(
                    MemoryCard(
                        id = id++, //we assign ID and then increment
                        imageResId = imageIds[i] //corresponding image
                    )
                )
            }
        }

        // Shuffle the cards
        _cards.value = cardList.shuffled()

        //We reset all state variables
        _moves.value = 0
        _gameWon.value = false  // Reset victory state
        firstSelectedCard = null
        secondSelectedCard = null
        isProcessing = false
    }

    //Handles the event when the player clicks on a card
    fun onCardClicked(card: MemoryCard) {
        // Do not allow clicks if the game has already been won
        if (_gameWon.value) return

        // Check if we can flip the card
        if (isProcessing) return //waiting to turn the card over
        if (card.isMatched) return //already matched card
        if (card.isFaceUp) return //card already turned over


        //We copy the current modified list
        val currentCards = _cards.value.toMutableList()
        //We search for the index of the letter that was clicked by its ID
        val cardIndex = currentCards.indexOfFirst { it.id == card.id }

        // Flip the card
        //We use copy() which creates a new instance with the changes
        currentCards[cardIndex] = card.copy(isFaceUp = true)
        _cards.value = currentCards //We updated the status

        // Selection logic
        if (firstSelectedCard == null) { //first card of the turn
            firstSelectedCard = card.copy(isFaceUp = true)
        } else if (secondSelectedCard == null) { //second card of the turn
            secondSelectedCard = card.copy(isFaceUp = true)
            _moves.value += 1 //We increased movements
            checkMatch() //we check if they are the same
        }
    }

    //when checking if the two selected cards are the same
    //runs automatically when the second card is selected
    private fun checkMatch() {
        isProcessing = true  //We block new clicks

        val first = firstSelectedCard
        val second = secondSelectedCard

        //security validation (should never be null here)

        if (first != null && second != null) {
            //We compare by imageResId (same image = same card)
            if (first.imageResId == second.imageResId) {
                // They are the same, we mark them as paired
                markAsMatched(first.id, second.id)
                clearSelection()
                isProcessing = false

                //Check if the game has already been won
                checkGameWon()
            } else {
                // They're different, turn them over after a second
                Handler(Looper.getMainLooper()).postDelayed({
                    flipCardsBack(first.id, second.id)
                    clearSelection()
                    isProcessing = false
                }, 1000) //1000 milliseconds = 1 second
            }
        }
    }

    //Marks two cards as matched (isMatched = true)
    private fun markAsMatched(firstId: Int, secondId: Int) {
        val currentCards = _cards.value.toMutableList()
        currentCards.forEachIndexed { index, card ->
            if (card.id == firstId || card.id == secondId) {
                //We updated the letter in the list
                currentCards[index] = card.copy(isMatched = true)
            }
        }
        _cards.value = currentCards
    }

    //Turns over two cards (hides them) after a failed attempt
    private fun flipCardsBack(firstId: Int, secondId: Int) {
        val currentCards = _cards.value.toMutableList()
        currentCards.forEachIndexed { index, card ->
            if (card.id == firstId || card.id == secondId) {
                currentCards[index] = card.copy(isFaceUp = false)
            }
        }
        _cards.value = currentCards
    }

    //clears the selected cards for the next turn
    private fun clearSelection() {
        firstSelectedCard = null
        secondSelectedCard = null
    }

    // Check if all cards are matched
    //If so, update the win status
    private fun checkGameWon() {
        //all {condition} = true if all meet the condition
        val allMatched = _cards.value.all { it.isMatched }
        if (allMatched) {
            _gameWon.value = true //Player won
        }
    }
}