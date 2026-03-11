package com.example.memorygamegroup301.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.memorygamegroup301.view.screens.MemoryGameScreen
import com.example.memorygamegroup301.viewmodel.GameViewModel

//Main app activity
//In MVVM, the Activity only configures the UI
//Creates the ViewModel
//Sets the content with Compose
//ComponentActivity is the version of Activity optimized for Compose
//viewModels: delegate that creates and maintains the ViewModel
class MainActivity : ComponentActivity() {

// viewModels() is a delegated function that:
    //- Creates the ViewModel the first time
    //- Maintains the same instance throughout the lifetime of the Activity
    //- Automatically destroys it when the Activity ends

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent: Compose function that sets the UI
        // The entire interface is defined with @Composable functions
        setContent {
            // We move the ViewModel to the screen
            // The screen will see the changes in the ViewModel
            MemoryGameScreen(viewModel = viewModel)
        }
    }
}