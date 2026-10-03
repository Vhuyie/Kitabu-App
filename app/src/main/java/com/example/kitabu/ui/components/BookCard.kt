package com.example.kitabu.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kitabu.ui.model.Book
import com.example.kitabu.ui.theme.AvailableGreen
import com.example.kitabu.ui.theme.AvailableGreenLight
import com.example.kitabu.ui.theme.BorrowedOrange
import com.example.kitabu.ui.theme.BorrowedOrangeLight
import com.example.kitabu.ui.theme.KitabuLightPink
import com.example.kitabu.ui.theme.KitabuPink
import com.example.kitabu.ui.theme.KitabuSecondaryText
import com.example.kitabu.ui.theme.KitabuText
import com.example.kitabu.ui.theme.KitabuWhite

@Composable
fun BookCard(
    book: Book,
    onReserveClick: (Book) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = KitabuWhite
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            // Book cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .background(KitabuLightPink),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = "Book cover",
                        tint = KitabuPink,
                        modifier = Modifier.size(55.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "KITABU",
                        color = KitabuPink,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = book.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = KitabuText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = book.author,
                fontSize = 13.sp,
                color = KitabuSecondaryText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = book.category,
                fontSize = 11.sp,
                color = KitabuPink,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            AvailabilityBadge(
                isAvailable = book.isAvailable
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    if (book.isAvailable) {
                        onReserveClick(book)
                    }
                },
                enabled = book.isAvailable,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KitabuPink,
                    contentColor = KitabuWhite,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray
                )
            ) {

                Text(
                    text = if (book.isAvailable) {
                        "Reserve Book"
                    } else {
                        "Currently Borrowed"
                    },
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun AvailabilityBadge(
    isAvailable: Boolean
) {

    val background =
        if (isAvailable) AvailableGreenLight
        else BorrowedOrangeLight

    val textColor =
        if (isAvailable) AvailableGreen
        else BorrowedOrange

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(background)
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(textColor)
            )

            Spacer(
                modifier = Modifier.size(6.dp)
            )

            Text(
                text = if (isAvailable) {
                    "Available"
                } else {
                    "Borrowed"
                },
                color = textColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}