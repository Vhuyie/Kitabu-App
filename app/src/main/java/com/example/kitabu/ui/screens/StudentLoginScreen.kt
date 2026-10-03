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
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kitabu.ui.theme.KitabuBackground
import com.example.kitabu.ui.theme.KitabuPink
import com.example.kitabu.ui.theme.KitabuSecondaryText
import com.example.kitabu.ui.theme.KitabuText
import com.example.kitabu.viewmodel.LibraryViewModel

@Composable
fun StudentLoginScreen(
    viewModel: LibraryViewModel,
    onLoginSuccess: () -> Unit,
    onRegister: () -> Unit,
    onAdminLogin: () -> Unit
){

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KitabuBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // KITABU LOGO
        Icon(
            imageVector = Icons.Default.Book,
            contentDescription = "Kitabu",
            tint = KitabuPink,
            modifier = Modifier
                .height(75.dp)
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Kitabu",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = KitabuPink
        )

        Text(
            text = "Your smart library",
            style = MaterialTheme.typography.bodyLarge,
            color = KitabuSecondaryText
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {

                Text(
                    text = "Student Login",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = KitabuText
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "Sign in to browse and reserve library books.",
                    color = KitabuSecondaryText
                )

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                // USERNAME
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Username")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Username"
                        )
                    },
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                // PASSWORD
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Password")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password"
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // ERROR
                if (errorMessage.isNotEmpty()) {

                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // LOGIN
                Button(
                    onClick = {

                        if (
                            username.trim() == "student" &&
                            password == "student123"
                        ) {

                            onLoginSuccess()

                        } else {

                            errorMessage =
                                "Incorrect username or password."
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = username.isNotBlank() &&
                            password.isNotBlank()
                ) {
                    Text("Login")
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // REGISTER
                OutlinedButton(
                    onClick = onRegister,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create Student Account")
                }
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // ADMIN LOGIN
        Text(
            text = "Library administrator?",
            color = KitabuSecondaryText
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        OutlinedButton(
            onClick = onAdminLogin
        ) {
            Text("Admin Login")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Demo student: student / student123",
            style = MaterialTheme.typography.labelSmall,
            color = KitabuSecondaryText
        )
    }
}