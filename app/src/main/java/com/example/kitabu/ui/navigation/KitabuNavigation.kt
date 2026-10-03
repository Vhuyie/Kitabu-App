package com.example.kitabu.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kitabu.ui.screens.AdminBooksScreen
import com.example.kitabu.ui.screens.AdminLoginScreen
import com.example.kitabu.ui.screens.CatalogScreen
import com.example.kitabu.ui.screens.HomeScreen
import com.example.kitabu.ui.screens.ReservationsScreen
import com.example.kitabu.ui.screens.StudentRegisterScreen
import com.example.kitabu.viewmodel.LibraryViewModel

sealed class KitabuRoute(
    val route: String,
    val label: String
) {
    data object Home : KitabuRoute("home", "Home")
    data object Catalog : KitabuRoute("catalog", "Catalog")
    data object Reservations : KitabuRoute("reservations", "Rentals")
    data object AdminLogin : KitabuRoute("admin_login", "Admin Login")
    data object AdminBooks : KitabuRoute("admin_books", "Manage Books")
    data object Profile : KitabuRoute("profile", "Profile")
}

@Composable
fun KitabuNavigation(viewModel: LibraryViewModel) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val currentRoute =
                navController.currentBackStackEntryAsState()
                    .value?.destination?.route

            // Hide bottom navigation on Admin Login
            if (currentRoute != KitabuRoute.AdminLogin.route) {
                KitabuBottomNavigation(
                    navController = navController
                )
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = KitabuRoute.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {


            // STUDENT HOME

            composable(KitabuRoute.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onOpenCatalog = {
                        navController.navigate(KitabuRoute.Catalog.route)
                    },
                    onOpenReservations = {
                        navController.navigate(KitabuRoute.Reservations.route)
                    },
                    onAdminLogin = {
                        navController.navigate(KitabuRoute.AdminLogin.route)
                    }
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


            // ADMIN LOGIN

            composable(KitabuRoute.AdminLogin.route) {
                AdminLoginScreen(
                    onLoginSuccess = {
                        navController.navigate(
                            KitabuRoute.AdminBooks.route
                        ) {
                            popUpTo(KitabuRoute.AdminLogin.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }


            // ADMIN BOOK MANAGEMENT

            composable(KitabuRoute.AdminBooks.route) {
                AdminBooksScreen(
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