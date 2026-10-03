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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kitabu.ui.theme.KitabuBackground
import com.example.kitabu.ui.theme.KitabuPink
import com.example.kitabu.ui.theme.KitabuSecondaryText
import com.example.kitabu.ui.theme.KitabuText
import com.example.kitabu.ui.theme.KitabuWhite
import com.example.kitabu.viewmodel.LibraryViewModel


@Composable
fun HomeScreen(
    viewModel: LibraryViewModel,
    onOpenCatalog: () -> Unit = {},
    onOpenReservations: () -> Unit = {},
    onAdminLogin: () -> Unit = {}
) {

    // Get books from the ViewModel
    val books by viewModel.books.collectAsState()

    // Show the last 2 books that were added
    val popularBooks = books.takeLast(2)

    Scaffold(
        containerColor = KitabuBackground
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // Header
            Header(
                onAdminLogin = onAdminLogin
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Search
            SearchBox(
                onSearch = onOpenCatalog
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Welcome card
            WelcomeCard(
                onBrowseBooks = onOpenCatalog
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Explore Library title
            Text(
                text = "Explore Library",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KitabuText
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Categories
            Categories()

            Spacer(modifier = Modifier.height(24.dp))

            // Popular Books title
            SectionTitle(
                onSeeAll = onOpenCatalog
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Popular Books
            // Display the two books side-by-side
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                popularBooks.forEach { book ->

                    Card(
                        modifier = Modifier
                            .weight(1f),

                        shape = RoundedCornerShape(18.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = KitabuWhite
                        ),

                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = book.title,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = KitabuText
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = book.author,
                                fontSize = 13.sp,
                                color = KitabuSecondaryText
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = book.category,
                                fontSize = 12.sp,
                                color = KitabuPink
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}



   //HEADER


@Composable
fun Header(
    onAdminLogin: () -> Unit = {}
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            Text(
                text = "Kitabu",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = KitabuPink
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Your Digital Library",
                fontSize = 13.sp,
                color = KitabuSecondaryText
            )
        }

        TextButton(
            onClick = onAdminLogin
        ) {

            Text(
                text = "Admin Login",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = KitabuPink
            )
        }
    }
}



   //SEARCH BOX


@Composable
fun SearchBox(
    onSearch: () -> Unit = {}
) {

    var searchText by remember {
        mutableStateOf("")
    }

    TextField(
        value = searchText,

        onValueChange = {
            searchText = it
        },

        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),

        placeholder = {
            Text(
                text = "Search books..."
            )
        },

        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },

        trailingIcon = {

            IconButton(
                onClick = onSearch
            ) {

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Open catalog"
                )
            }
        },

        singleLine = true,

        colors = TextFieldDefaults.colors(
            focusedContainerColor = KitabuWhite,
            unfocusedContainerColor = KitabuWhite,
            disabledContainerColor = KitabuWhite,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}



   //WELCOME CARD


@Composable
fun WelcomeCard(
    onBrowseBooks: () -> Unit = {}
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = KitabuPink
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "Welcome to Kitabu",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = KitabuWhite
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Find textbooks, browse your modules and manage your library.",
                fontSize = 14.sp,
                color = KitabuWhite
            )

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.Button(
                onClick = onBrowseBooks,

                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = KitabuWhite,
                    contentColor = KitabuPink
                )
            ) {

                Text(
                    text = "Browse Books",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}



   //CATEGORIES


@Composable
fun Categories() {

    Row(
        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.spacedBy(
            10.dp
        )
    ) {

        CategoryItem(
            title = "Textbooks",
            modifier = Modifier.weight(1f)
        )

        CategoryItem(
            title = "Modules",
            modifier = Modifier.weight(1f)
        )

        CategoryItem(
            title = "New",
            modifier = Modifier.weight(1f)
        )
    }
}



   //CATEGORY ITEM

@Composable
fun CategoryItem(
    title: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = KitabuWhite
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = KitabuText
            )
        }
    }
}



   //SECTION TITLE


@Composable
fun SectionTitle(
    onSeeAll: () -> Unit = {}
) {

    Row(
        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Popular Books",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = KitabuText
        )

        TextButton(
            onClick = onSeeAll
        ) {

            Text(
                text = "See all",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = KitabuPink
            )
        }
    }
}