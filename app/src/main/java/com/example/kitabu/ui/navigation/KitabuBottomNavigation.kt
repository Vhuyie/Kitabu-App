package com.example.kitabu.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun KitabuBottomNavigation(
    navController: NavHostController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Check if currently in admin section
    val isAdmin = currentRoute == KitabuRoute.AdminBooks.route

    NavigationBar {


        //landing page Home Screen

        NavigationBarItem(
            selected = currentRoute == KitabuRoute.Home.route,

            onClick = {
                // BOTH STUDENT AND ADMIN GO TO STUDENT HOME
                navController.navigate(KitabuRoute.Home.route) {
                    launchSingleTop = true
                }
            },

            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },

            label = {
                Text("Home")
            }
        )


        // Catalog to manage books.

        NavigationBarItem(
            selected = if (isAdmin) {
                currentRoute == KitabuRoute.AdminBooks.route
            } else {
                currentRoute == KitabuRoute.Catalog.route
            },

            onClick = {
                if (isAdmin) {
                    navController.navigate(KitabuRoute.AdminBooks.route) {
                        launchSingleTop = true
                    }
                } else {
                    navController.navigate(KitabuRoute.Catalog.route) {
                        launchSingleTop = true
                    }
                }
            },

            icon = {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = if (isAdmin) {
                        "Manage Books"
                    } else {
                        "Catalog"
                    }
                )
            },

            label = {
                Text(
                    if (isAdmin) {
                        "Manage Books"
                    } else {
                        "Catalog"
                    }
                )
            }
        )

        if (!isAdmin) {

            NavigationBarItem(
                selected = currentRoute == KitabuRoute.Reservations.route,

                onClick = {
                    navController.navigate(
                        KitabuRoute.Reservations.route
                    ) {
                        launchSingleTop = true
                    }
                },

                icon = {
                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = "Rentals"
                    )
                },

                label = {
                    Text("Rentals")
                }
            )


        }
    }
}