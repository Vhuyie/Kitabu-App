package com.example.kitabu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kitabu.data.entity.BookingWithBook
import com.example.kitabu.ui.theme.KitabuBackground
import com.example.kitabu.ui.theme.KitabuLightPink
import com.example.kitabu.ui.theme.KitabuPink
import com.example.kitabu.ui.theme.KitabuSecondaryText
import com.example.kitabu.ui.theme.KitabuText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun ReservationsScreen(
    viewModel: com.example.kitabu.viewmodel.LibraryViewModel
) {

    val bookings by viewModel.activeBookings
        .collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KitabuBackground)
            .padding(20.dp)
    ) {

        Text(
            text = "My Reservations",
            style = MaterialTheme.typography.headlineMedium,
            color = KitabuText
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Manage your borrowed books",
            color = KitabuSecondaryText
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (bookings.isEmpty()) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = "No reservations",
                    tint = KitabuPink
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "No active reservations",
                    color = KitabuText,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Reserve a book from the catalog.",
                    color = KitabuSecondaryText
                )
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(
                    items = bookings,
                    key = { it.booking.bookingId }
                ) { reservation ->

                    ReservationCard(
                        reservation = reservation,
                        onRenew = {
                            viewModel.renewBooking(
                                reservation.booking.bookingId
                            )
                        },
                        onReturn = {
                            viewModel.returnBook(
                                reservation.booking.bookingId
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ReservationCard(
    reservation: BookingWithBook,
    onRenew: () -> Unit,
    onReturn: () -> Unit
) {

    val booking = reservation.booking
    val book = reservation.book

    val daysRemaining = maxOf(
        0,
        TimeUnit.MILLISECONDS.toDays(
            booking.returnDeadline - System.currentTimeMillis()
        ).toInt()
    )

    val dateFormat = SimpleDateFormat(
        "dd MMM yyyy",
        Locale.getDefault()
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = "Book",
                    tint = KitabuPink
                )

                Spacer(modifier = Modifier.padding(6.dp))

                Column {

                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = KitabuText
                    )

                    Text(
                        text = book.author,
                        color = KitabuSecondaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Category: ${book.category}",
                color = KitabuSecondaryText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Return date",
                    tint = KitabuPink
                )

                Spacer(modifier = Modifier.padding(4.dp))

                Text(
                    text = "Return: ${
                        dateFormat.format(
                            Date(booking.returnDeadline)
                        )
                    }",
                    color = KitabuText
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$daysRemaining days remaining",
                color = KitabuPink,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = onRenew,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KitabuPink
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Renew"
                    )

                    Spacer(modifier = Modifier.padding(2.dp))

                    Text("Renew")
                }

                Button(
                    onClick = onReturn,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KitabuLightPink,
                        contentColor = KitabuPink
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Undo,
                        contentDescription = "Return"
                    )

                    Spacer(modifier = Modifier.padding(2.dp))

                    Text("Return")
                }
            }
        }
    }
}