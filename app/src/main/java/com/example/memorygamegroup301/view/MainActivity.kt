package com.example.memorygamegroup301.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.memorygamegroup301.view.screens.MemoryGameScreen
import com.example.memorygamegroup301.viewmodel.GameViewModel

//Actividad principal de la app
//En MVVM, la Activity sólo configura la UI
//Crea el ViewModel
//Establece el contenido con Compose
//ComponenteActivity es la version de Activity optimizada para Compose
//viewModels: delegado que crea y mantiene el ViewModel
class MainActivity : ComponentActivity() {

// viewModels() es una función delegada que:
    // - Crea el ViewModel la primera vez
    // - Mantiene la misma instancia durante toda la vida de la Activity
    // - Lo destruye automáticamente cuando la Activity finaliza

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent: Función de Compose que establece la UI
        // Toda la interfaz se define con funciones @Composable
        setContent {
            // Pasamos el ViewModel a la pantalla
            // La pantalla observará los cambios en el ViewModel
            MemoryGameScreen(viewModel = viewModel)
        }
    }
}