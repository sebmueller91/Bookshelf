package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.bookshelf.R

@Composable
fun FavoritesScreen(
    viewModel: BookshelfViewModel,
    bookshelfUiState: BookshelfUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {


    Column() {
        if (!viewModel.favoriteBooks.isEmpty()) {
            when (bookshelfUiState) {
                is BookshelfUiState.Loading -> LoadingScreen(modifier)
                is BookshelfUiState.Success -> ScrollableBooksList(
                    bookshelfUiState.books,
                    viewModel,
                    modifier
                )
                else -> ErrorScreen(retryAction, modifier)
            }
        } else {
            Box(modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.NoFavoriteBooksText))
            }
        }
    }
}