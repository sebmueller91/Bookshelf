package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.bookshelf.R


@Composable
fun MenuScreen(
    onQueryButtonClickedAction: () -> Unit,
    onFavoritesButtonClickedAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onQueryButtonClickedAction
        ) {
            Text(
                text = stringResource(R.string.SearchOnlineBookLibraryButtonText)
            )
        }
        Button(
            onClick = onFavoritesButtonClickedAction
        ) {
            Text(
                text = stringResource(R.string.ShowFavoriteListButtonText)
            )
        }
    }
}
