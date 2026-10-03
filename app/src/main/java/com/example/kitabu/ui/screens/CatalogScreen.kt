package com.example.kitabu.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kitabu.ui.components.BookCard
import com.example.kitabu.ui.components.ReservationSheet
import com.example.kitabu.ui.model.Book
import com.example.kitabu.ui.theme.KitabuBackground
import com.example.kitabu.ui.theme.KitabuPink
import com.example.kitabu.ui.theme.KitabuSecondaryText
import com.example.kitabu.ui.theme.KitabuText
import com.example.kitabu.ui.theme.KitabuWhite
import com.example.kitabu.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: LibraryViewModel
) {

    val books by viewModel.books.collectAsStateWithLifecycle()

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedBook by remember {
        mutableStateOf<Book?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Book Catalog",
            fontSize = 26.sp,
            color = KitabuText
        )

        Text(
            text = "Find something interesting to read",
            fontSize = 14.sp,
            color = KitabuText.copy(alpha = 0.65f),
            modifier = Modifier.padding(
                top = 4.dp,
                bottom = 12.dp
            )
        )

        /*
         * SEARCH
         *
         * The search value is kept inside the Composable.
         * The ViewModel performs the actual database search.
         */
        TextField(
            value = searchText,

            onValueChange = { query ->
                searchText = query
                viewModel.search(query)
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),

            placeholder = {
                Text(
                    text = "Search books or authors...",
                    color = KitabuSecondaryText
                )
            },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search books",
                    tint = KitabuPink
                )
            },

            singleLine = true,

            shape = RoundedCornerShape(16.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = KitabuWhite,
                unfocusedContainerColor = KitabuWhite,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        /*
         * BOOK GRID
         */
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),

            modifier = Modifier
                .fillMaxSize(),

            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 20.dp
            ),

            horizontalArrangement = Arrangement.spacedBy(12.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(
                items = books,
                key = { it.bookId }
            ) { bookEntity ->

                val book = Book(
                    id = bookEntity.bookId,
                    title = bookEntity.title,
                    author = bookEntity.author,
                    category = bookEntity.category,
                    isAvailable = bookEntity.isAvailable
                )

                BookCard(
                    book = book,

                    onReserveClick = {
                        selectedBook = it
                    }
                )
            }
        }
    }

    /*
     * RESERVATION BOTTOM SHEET
     */
    selectedBook?.let { book ->

        ModalBottomSheet(

            onDismissRequest = {
                selectedBook = null
            },

            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            )
        ) {

            ReservationSheet(

                book = book,

                onDismiss = {
                    selectedBook = null
                },

                onReserve = { duration ->

                    val entity = books.firstOrNull {
                        it.bookId == book.id
                    }

                    if (entity != null) {

                        viewModel.reserveBook(
                            book = entity,
                            userName = "Student",
                            durationDays = duration
                        )
                    }

                    selectedBook = null
                }
            )
        }
    }
}

