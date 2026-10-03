
package com.example.kitabu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kitabu.data.entity.BookEntity
import com.example.kitabu.ui.theme.AvailableGreen
import com.example.kitabu.ui.theme.AvailableGreenLight
import com.example.kitabu.ui.theme.BorrowedOrange
import com.example.kitabu.ui.theme.BorrowedOrangeLight
import com.example.kitabu.ui.theme.KitabuLightPink
import com.example.kitabu.ui.theme.KitabuPink
import com.example.kitabu.ui.theme.KitabuSecondaryText
import com.example.kitabu.viewmodel.LibraryViewModel

@Composable
fun AdminBooksScreen(
    viewModel: LibraryViewModel
) {
    val books by viewModel.books.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var bookToEdit by remember { mutableStateOf<BookEntity?>(null) }
    var bookToDelete by remember { mutableStateOf<BookEntity?>(null) }

    ScaffoldWithAdminContent(
        onAddBook = {
            showAddDialog = true
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            // HEADER
            Text(
                text = "Manage Books",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = KitabuPink
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Add, edit and remove books from the library.",
                style = MaterialTheme.typography.bodyMedium,
                color = KitabuSecondaryText
            )

            Spacer(modifier = Modifier.height(16.dp))

            // SUMMARY CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = KitabuLightPink
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(KitabuPink),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(14.dp))

                    Column {
                        Text(
                            text = books.size.toString(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = KitabuPink
                        )

                        Text(
                            text = "Books in library",
                            color = KitabuSecondaryText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            if (books.isEmpty()) {

                EmptyBooksState(
                    onAddBook = {
                        showAddDialog = true
                    }
                )

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(
                        items = books,
                        key = { it.bookId }
                    ) { book ->

                        AdminBookCard(
                            book = book,
                            onEdit = {
                                bookToEdit = book
                            },
                            onDelete = {
                                bookToDelete = book
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(90.dp))
                    }
                }
            }
        }
    }

    // ADD BOOK
    if (showAddDialog) {

        BookDialog(
            title = "Add New Book",
            onDismiss = {
                showAddDialog = false
            },
            onSave = { title, author, category ->

                viewModel.addBook(
                    title = title,
                    author = author,
                    category = category
                )

                showAddDialog = false
            }
        )
    }

    // EDIT BOOK
    bookToEdit?.let { book ->

        BookDialog(
            title = "Edit Book",
            existingBook = book,
            onDismiss = {
                bookToEdit = null
            },
            onSave = { title, author, category ->

                viewModel.updateBook(
                    book.copy(
                        title = title,
                        author = author,
                        category = category
                    )
                )

                bookToEdit = null
            }
        )
    }

    // DELETE BOOK
    bookToDelete?.let { book ->

        AlertDialog(
            onDismissRequest = {
                bookToDelete = null
            },

            title = {
                Text(
                    text = "Delete Book",
                    fontWeight = FontWeight.Bold
                )
            },

            text = {
                Text(
                    "Are you sure you want to delete \"${book.title}\"?"
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        viewModel.deleteBook(book)

                        bookToDelete = null
                    }
                ) {
                    Text("Delete")
                }
            },

            dismissButton = {

                OutlinedButton(
                    onClick = {
                        bookToDelete = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun ScaffoldWithAdminContent(
    onAddBook: () -> Unit,
    content: @Composable (androidx.compose.foundation.layout.PaddingValues) -> Unit
) {
    androidx.compose.material3.Scaffold(
        floatingActionButton = {

            FloatingActionButton(
                onClick = onAddBook,
                containerColor = KitabuPink,
                contentColor = Color.White
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add book"
                )
            }
        }
    ) { paddingValues ->

        content(paddingValues)
    }
}

@Composable
private fun AdminBookCard(
    book: BookEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(KitabuLightPink),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Book,
                        contentDescription = null,
                        tint = KitabuPink,
                        modifier = Modifier.size(25.dp)
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = book.author,
                        style = MaterialTheme.typography.bodyMedium,
                        color = KitabuSecondaryText
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = book.category,
                        style = MaterialTheme.typography.labelMedium,
                        color = KitabuPink
                    )
                }

                IconButton(
                    onClick = onEdit
                ) {

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit ${book.title}",
                        tint = KitabuPink
                    )
                }

                IconButton(
                    onClick = onDelete
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete ${book.title}",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            AvailabilityBadge(
                isAvailable = book.isAvailable
            )
        }
    }
}

@Composable
private fun AvailabilityBadge(
    isAvailable: Boolean
) {
    val background =
        if (isAvailable) AvailableGreenLight
        else BorrowedOrangeLight

    val textColor =
        if (isAvailable) AvailableGreen
        else BorrowedOrange

    Surface(
        color = background,
        shape = RoundedCornerShape(50.dp)
    ) {

        Text(
            text = if (isAvailable) {
                "●  Available"
            } else {
                "●  Currently borrowed"
            },
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 7.dp
            ),
            color = textColor,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EmptyBooksState(
    onAddBook: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.MenuBook,
            contentDescription = null,
            tint = KitabuPink,
            modifier = Modifier.size(65.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "No books yet",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Add your first book to the library.",
            color = KitabuSecondaryText
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = onAddBook
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(6.dp))

            Text("Add Book")
        }
    }
}

@Composable
private fun BookDialog(
    title: String,
    existingBook: BookEntity? = null,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var bookTitle by remember {
        mutableStateOf(existingBook?.title ?: "")
    }

    var author by remember {
        mutableStateOf(existingBook?.author ?: "")
    }

    var category by remember {
        mutableStateOf(existingBook?.category ?: "")
    }

    val isValid =
        bookTitle.isNotBlank() &&
                author.isNotBlank() &&
                category.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = KitabuPink
            )
        },

        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                OutlinedTextField(
                    value = bookTitle,
                    onValueChange = {
                        bookTitle = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Book title")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = author,
                    onValueChange = {
                        author = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Author")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = {
                        category = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Category")
                    },
                    singleLine = true
                )
            }
        },

        confirmButton = {

            Button(
                enabled = isValid,
                onClick = {

                    onSave(
                        bookTitle.trim(),
                        author.trim(),
                        category.trim()
                    )
                }
            ) {
                Text("Save Book")
            }
        },

        dismissButton = {

            OutlinedButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}
