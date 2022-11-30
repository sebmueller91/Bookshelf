package com.example.bookshelf.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.ui.screens.QueryScreen
import com.example.bookshelf.ui.screens.QueryViewModel

@Composable
fun BookshelfApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Bookshelf") }) } // TOOD: Why cant the app name be used here
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            val queryViewModel: QueryViewModel =
                viewModel(factory = QueryViewModel.Factory)
            QueryScreen(
                viewModel = queryViewModel,
                bookshelfUiState = queryViewModel.bookshelfUiState,
                retryAction = queryViewModel::getBooksForQuery
            )
        }
    }
}
