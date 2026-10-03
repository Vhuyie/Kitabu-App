package com.example.kitabu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kitabu.data.database.AppDatabase
import com.example.kitabu.ui.navigation.KitabuNavigation
import com.example.kitabu.ui.theme.KitabuTheme
import com.example.kitabu.viewmodel.LibraryViewModel
import com.example.kitabu.viewmodel.LibraryViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)

        setContent {

            KitabuTheme {

                val libraryViewModel: LibraryViewModel = viewModel(
                    factory = LibraryViewModelFactory(database)
                )

                KitabuNavigation(
                    viewModel = libraryViewModel
                )
            }
        }
    }
}