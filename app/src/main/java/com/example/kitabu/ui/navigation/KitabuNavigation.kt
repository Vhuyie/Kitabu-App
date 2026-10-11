package com.example.kitabu.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kitabu.ui.screens.CatalogScreen
import com.example.kitabu.ui.screens.HomeScreen
import com.example.kitabu.ui.screens.ReservationsScreen
import com.example.kitabu.ui.screens.StudentRegisterScreen
import com.example.kitabu.viewmodel.LibraryViewModel

sealed class KitabuRoute(
    val route: String,
    val label: String )
{
    data object Home : KitabuRoute("home", "Home")
    data object Catalog : KitabuRoute("catalog", "Catalog")
    data object Reservations : KitabuRoute("reservations", "Rentals")

}

@Composable
fun KitabuNavigation(viewModel: LibraryViewModel) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            KitabuBottomNavigation ( navController = navController ) }
    ) {
        paddingValues -> NavHost( navController = navController, startDestination = KitabuRoute.Home.route,
        modifier = Modifier.padding(paddingValues)
        ) {

            // STUDENT HOME SCREEN
            composable(KitabuRoute.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onOpenCatalog = {
                        navController.navigate(KitabuRoute.Catalog.route)
                    },
                    onOpenReservations = {
                        navController.navigate(KitabuRoute.Reservations.route)
                    },
                )
            }

            // CATALOG

            composable(KitabuRoute.Catalog.route) {
                CatalogScreen(
                    viewModel = viewModel
                )
            }

            // RENTALS
            composable(KitabuRoute.Reservations.route) {
                ReservationsScreen(
                    viewModel = viewModel
                )
            }

            // REGISTER

            composable("student_register") {
                StudentRegisterScreen(
                    viewModel = viewModel,
                    onRegistrationSuccess = {
                        navController.navigate(KitabuRoute.Home.route) {
                            popUpTo("student_register") {
                                inclusive = true
                            }
                        }
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}