package com.example.bookshelf.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.theme.BookshelfTheme

@Composable
fun ScrollableBooksList(
    books: List<Book>,
    viewModel: BookshelfViewModel,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        items(books) { book ->
            BookCard(book = book, viewModel = viewModel)
        }
    }
}

@Composable
fun BookCard(
    book: Book,
    viewModel: BookshelfViewModel,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var favorite by remember { mutableStateOf(false) }
    favorite = viewModel.isBookFavorite(book)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(0.67f),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.getThumbnailAsHttps())
                    .crossfade(true)
                    .build(),
                contentDescription = book?.volumeInfo?.title,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
            )
            Row() {
                FavoritesButton(
                    favorite = favorite,
                    onClick = {
                        if (favorite) {
                            viewModel.removeFavoriteBook(book)
                        } else {
                            viewModel.addFavoriteBook(book)
                        }
                        favorite = !favorite
                    },
                    modifier = modifier.align(
                        Alignment.CenterVertically
                    )
                )
                ExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                    modifier = modifier.align(
                        Alignment.CenterVertically
                    )
                )
            }

            if (expanded) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    TextLine(
                        propertyName = stringResource(R.string.Title),
                        propertyValue = book?.volumeInfo?.title
                    )
                    if (book?.volumeInfo?.subtitle?.isNullOrEmpty() == false) {
                        TextLine("Subtitle", propertyValue = book?.volumeInfo?.subtitle)
                    }
                    TextLine("Authors", propertyValue = book.getFirstThreeAuthors())
                    if (!book.getPrice().isEmpty()) {
                        TextLine("Price", propertyValue = book.getPrice())
                    }
                    //TextLine("Description", propertyValue = book.description)
                }
            }
        }
    }
}

@Composable
fun TextLine(propertyName: String, propertyValue: String?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "${propertyName}: ",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "${propertyValue ?: stringResource(R.string.Unknown)}",
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun FavoritesButton(
    favorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (favorite) Icons.Filled.Favorite else Icons.Filled.Favorite,
            tint = if (favorite) Color.Red else Color.LightGray,
            contentDescription = stringResource(R.string.ExpandButtonContentDescription)
        )
    }
}

@Composable
fun ExpandButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = Color.DarkGray,
            contentDescription = stringResource(R.string.ExpandButtonContentDescription)
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview
@Composable
fun TextLinePreview() {
    BookshelfTheme(darkTheme = false) {
        TextLine("Title", "Cicero")
    }
}