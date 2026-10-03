
package com.example.kitabu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kitabu.ui.theme.KitabuBackground
import com.example.kitabu.ui.theme.KitabuPink
import com.example.kitabu.ui.theme.KitabuSecondaryText
import com.example.kitabu.ui.theme.KitabuText

@Composable
fun ProfileScreen(
    onOpenAdminLogin: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KitabuBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // PAGE TITLE
        Text(
            text = "My Profile",
            style = MaterialTheme.typography.headlineMedium,
            color = KitabuText
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        // PROFILE CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // PROFILE ICON
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = KitabuPink
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // USER NAME
                Text(
                    text = "Kitabu Student",
                    style = MaterialTheme.typography.titleLarge,
                    color = KitabuText
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                // USER TYPE
                Text(
                    text = "Library Member",
                    color = KitabuSecondaryText
                )

                Spacer(
                    modifier = Modifier.height(25.dp)
                )

                // ADMIN BUTTON
                Button(
                    onClick = {
                        onOpenAdminLogin()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Admin Login"
                    )
                }
            }
        }
    }
}

