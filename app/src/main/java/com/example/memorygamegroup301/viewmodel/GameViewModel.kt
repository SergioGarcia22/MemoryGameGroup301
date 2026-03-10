package com.example.memorygamegroup301.viewmodel

import androidx.lifecycle.ViewModel
import com.example.memorygamegroup301.model.MemoryCard
import com.example.memorygamegroup301.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.os.Handler
import android.os.Looper


//ViewModel: Capa de lógica del juego
// MVVM: El ViewModel actúa como puente entre el Modelo (datos) y la Vista (UI)

//StateFlow: Observable que emite valores cuando cambia el estado
//StateFlow: Observable que emite valores cuando cambia el estado
//StateFlow: Versión de solo lectura (pública)
class GameViewModel : ViewModel() {

  //ESTADO DE JUEGO
    // Lista de todas las cartas
    private val _cards = MutableStateFlow<List<MemoryCard>>(emptyList())
    val cards: StateFlow<List<MemoryCard>> = _cards.asStateFlow()

    // Contador de movimientos
    private val _moves = MutableStateFlow(0)
    val moves: StateFlow<Int> = _moves.asStateFlow()

    // Estado de victoria- true cuando todas las cartas están emparejadas
    private val _gameWon = MutableStateFlow(false)
    val gameWon: StateFlow<Boolean> = _gameWon.asStateFlow()

    // Variables para controlar el juego
    //Almacenan las cartas seleccionadas en el turno actual
    private var firstSelectedCard: MemoryCard? = null
    private var secondSelectedCard: MemoryCard? = null
    // Bandera para evitar clicks mientras se comparan cartas
    private var isProcessing = false

    // IDs de las imágenes que se van usar (6 diferentes)
    private val imageIds = listOf(
        R.drawable.ic_card_1,
        R.drawable.ic_card_2,
        R.drawable.ic_card_3,
        R.drawable.ic_card_4,
        R.drawable.ic_card_5,
        R.drawable.ic_card_6
    )

    //Bloque init: esto se ejecuta al crear el viewmodel
    //inicia el juego llamando a resetGame()
    init {
        resetGame()
    }

    //Reinicia el juego con nuevas cartas aleatorias
    fun resetGame() {
        // Crear pares de cartas (2 de cada imagen)
        val cardList = mutableListOf<MemoryCard>()
        var id = 0 //ID único para cada carta

        // creamos 2 copias de cada imagen (los pares)
        repeat(2) {

            //Recorremos todas las imagenes
            for (i in imageIds.indices) {
                cardList.add(
                    MemoryCard(
                        id = id++, //asignamos ID y luego incrementamos
                        imageResId = imageIds[i] //imagen correspondiente
                    )
                )
            }
        }

        // Mezclar las cartas
        _cards.value = cardList.shuffled()

        //Reiniciamos todas las variables de estado
        _moves.value = 0
        _gameWon.value = false  // Reiniciar estado de victoria
        firstSelectedCard = null
        secondSelectedCard = null
        isProcessing = false
    }

    //Maneja el evento cuando el jugador hace clic en una carta
    fun onCardClicked(card: MemoryCard) {
        // No permitir clicks si ya se ganó el juego
        if (_gameWon.value) return

        // Verificar si podemos voltear la carta
        if (isProcessing) return //esperando voltear la carta
        if (card.isMatched) return //carta ya emparejada
        if (card.isFaceUp) return //carta ya volteada


        //Copiamos la lista actual modificada
        val currentCards = _cards.value.toMutableList()
        //Buscamos el índice de la carta que se dio clic por su ID
        val cardIndex = currentCards.indexOfFirst { it.id == card.id }

        // Voltear la carta
        //Usamos copy() que crea una nueva instancia con los cambios
        currentCards[cardIndex] = card.copy(isFaceUp = true)
        _cards.value = currentCards //Actualizamos el estado

        // Lógica de selección
        if (firstSelectedCard == null) { //primera carta del turno
            firstSelectedCard = card.copy(isFaceUp = true)
        } else if (secondSelectedCard == null) { //segunda carta del turno
            secondSelectedCard = card.copy(isFaceUp = true)
            _moves.value += 1 //incrementamos movimientos
            checkMatch() //verificamos sin son iguales
        }
    }

    //cuando se verifica si las dos cartas seleccionadas son iguales
    //se ejecuta automáticamente al seleccionar la segunda carta
    private fun checkMatch() {
        isProcessing = true  //Bloqueamos nuevos clicks

        val first = firstSelectedCard
        val second = secondSelectedCard

        //validación de seguridad (nunca deberían ser null aquí)

        if (first != null && second != null) {
            //Comparamos por imageResId (misma imagen = misma carta)
            if (first.imageResId == second.imageResId) {
                // Son iguales, las marcamos como emparejada
                markAsMatched(first.id, second.id)
                clearSelection()
                isProcessing = false

                //Verificar si ya se ganó el juego
                checkGameWon()
            } else {
                // Son diferentes, voltearlas después de un segundo
                Handler(Looper.getMainLooper()).postDelayed({
                    flipCardsBack(first.id, second.id)
                    clearSelection()
                    isProcessing = false
                }, 1000) //1000 milisegundos = 1segundo
            }
        }
    }

    //Marca dos cartas como emparejadas (isMatched =true)
    private fun markAsMatched(firstId: Int, secondId: Int) {
        val currentCards = _cards.value.toMutableList()
        currentCards.forEachIndexed { index, card ->
            if (card.id == firstId || card.id == secondId) {
               //Actualizamos la carta en la lista
                currentCards[index] = card.copy(isMatched = true)
            }
        }
        _cards.value = currentCards
    }

    //Voltea dos cartas (las oculta) después de un intento fallido
    private fun flipCardsBack(firstId: Int, secondId: Int) {
        val currentCards = _cards.value.toMutableList()
        currentCards.forEachIndexed { index, card ->
            if (card.id == firstId || card.id == secondId) {
                currentCards[index] = card.copy(isFaceUp = false)
            }
        }
        _cards.value = currentCards
    }

    //limpia las cartas seleccionadas para el siguiente turno
    private fun clearSelection() {
        firstSelectedCard = null
        secondSelectedCard = null
    }

    // Verificar si todas las cartas están emparejadas
    //si es así, se actualiza el estado de victoria
    private fun checkGameWon() {
        //all {condition} = true si todas cumplen con la condicion
        val allMatched = _cards.value.all { it.isMatched }
        if (allMatched) {
            _gameWon.value = true //Jugados ganó
        }
    }
}