package com.example.kitabu.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kitabu.ui.model.Book
import com.example.kitabu.ui.theme.KitabuPink
import com.example.kitabu.ui.theme.KitabuSecondaryText
import com.example.kitabu.ui.theme.KitabuText
import com.example.kitabu.ui.theme.KitabuWhite

@Composable
fun ReservationSheet(
    book: Book,
    onDismiss: () -> Unit,
    onReserve: (Int) -> Unit
) {
    // Start at 1 day
    var selectedDuration by remember {
        mutableIntStateOf(1)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {

        Text(
            text = "Reserve Book",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = KitabuText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = book.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = KitabuPink
        )

        Text(
            text = "by ${book.author}",
            fontSize = 14.sp,
            color = KitabuSecondaryText
        )

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Select rental duration",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = KitabuText
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 1 day
        DurationOption(
            duration = 1,
            selected = selectedDuration == 1,
            onClick = {
                selectedDuration = 1
            }
        )

        // 2 days
        DurationOption(
            duration = 2,
            selected = selectedDuration == 2,
            onClick = {
                selectedDuration = 2
            }
        )

        // 3 days
        DurationOption(
            duration = 3,
            selected = selectedDuration == 3,
            onClick = {
                selectedDuration = 3
            }
        )

        // 4 days
        DurationOption(
            duration = 4,
            selected = selectedDuration == 4,
            onClick = {
                selectedDuration = 4
            }
        )

        // 5 days
        DurationOption(
            duration = 5,
            selected = selectedDuration == 5,
            onClick = {
                selectedDuration = 5
            }
        )

        // 6 days
        DurationOption(
            duration = 6,
            selected = selectedDuration == 6,
            onClick = {
                selectedDuration = 6
            }
        )

        // 7 days
        DurationOption(
            duration = 7,
            selected = selectedDuration == 7,
            onClick = {
                selectedDuration = 7
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onReserve(selectedDuration)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = KitabuPink,
                contentColor = KitabuWhite
            )
        ) {
            Text(
                text = "Reserve for $selectedDuration days",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun DurationOption(
    duration: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$duration days",
            fontSize = 16.sp,
            color = KitabuText
        )

        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}