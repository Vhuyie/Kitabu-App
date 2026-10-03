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


        // HOME SCREEN

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


        // CATALOG / MANAGE BOOKS

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


        // RENTALS
        // Student only

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


            // ADMIN LOGIN

            NavigationBarItem(
                selected = currentRoute == KitabuRoute.AdminLogin.route,

                onClick = {
                    navController.navigate(
                        KitabuRoute.AdminLogin.route
                    ) {
                        launchSingleTop = true
                    }
                },

                icon = {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin Login"
                    )
                },

                label = {
                    Text("Admin Login")
                }
            )
        }
    }
}