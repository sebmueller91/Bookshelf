package com.example.bookshelf.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.ui.screens.BookshelfViewModel
import com.example.bookshelf.ui.screens.MenuScreen
import com.example.bookshelf.ui.screens.FavoritesScreen
import com.example.bookshelf.ui.screens.QueryScreen


enum class BookshelfScreen(@StringRes val title: Int) {
    Menu(title = R.string.app_name),
    Query(title = R.string.QueryScreenName),
    Favorites(title = R.string.FavoritesScreenName),
}

@Composable
fun BookshelfAppBar(
    currentScreen: BookshelfScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun BookshelfApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = BookshelfScreen.valueOf(
        backStackEntry?.destination?.route ?: BookshelfScreen.Menu.name
    )

    val viewModel: BookshelfViewModel =
                viewModel(factory = BookshelfViewModel.Factory)

    Scaffold(
        topBar = {
            BookshelfAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BookshelfScreen.Menu.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = BookshelfScreen.Menu.name) {
                MenuScreen(
                    onQueryButtonClickedAction = { navController.navigate(BookshelfScreen.Query.name) },
                    onFavoritesButtonClickedAction = { navController.navigate(BookshelfScreen.Favorites.name) }
                )
            }

            composable(route = BookshelfScreen.Query.name) {
                QueryScreen(
                    viewModel = viewModel,
                    bookshelfUiState = viewModel.bookshelfUiState,
                    retryAction = viewModel::getBooksForQuery
                )
            }

            composable(route = BookshelfScreen.Favorites.name) {
                FavoritesScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

//@Composable
//fun BookshelfApp(modifier: Modifier = Modifier) {
//    Scaffold(
//        modifier = modifier.fillMaxSize(),
//        topBar = { TopAppBar(title = { Text("Bookshelf") }) } // TOOD: Why cant the app name be used here
//    ) {
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(it),
//            color = MaterialTheme.colors.background
//        ) {
//            val queryViewModel: QueryViewModel =
//                viewModel(factory = QueryViewModel.Factory)
//            QueryScreen(
//                viewModel = queryViewModel,
//                bookshelfUiState = queryViewModel.bookshelfUiState,
//                retryAction = queryViewModel::getBooksForQuery
//            )
//        }
//    }
//}
