package com.example.bookshelf.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BookshelfUiState {
    data class Success(val books: List<Book>) : BookshelfUiState
    object Error : BookshelfUiState
    object Loading : BookshelfUiState
}

class BookshelfViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {
    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Loading)
        private set

    var query: String by mutableStateOf("")
        private set

    var searchStarted: Boolean by mutableStateOf(false)
        private set

    var favoriteBooks: MutableList<Book> by mutableStateOf(mutableListOf<Book>())
        private set

    init {

    }

    fun updateQuery(newQuery: String) {
        query = newQuery
    }

    fun getBooksForQuery() {
        searchStarted = true
        viewModelScope.launch {
            bookshelfUiState = BookshelfUiState.Loading
            bookshelfUiState = try {
                BookshelfUiState.Success(booksRepository.getBooks(query))
            } catch (e: IOException) {
                BookshelfUiState.Error
            } catch (e: HttpException) {
                BookshelfUiState.Error
            }
        }
    }

    fun addFavoriteBook(book: Book) {
        if (!isBookFavorite(book)) {
            favoriteBooks.add(book)
        }
    }

    fun isBookFavorite(book: Book) : Boolean {
        return !favoriteBooks.filter { x -> x.id == book.id }.isEmpty();
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun removeFavoriteBook(book: Book) {
        favoriteBooks.removeIf { it.id == book.id }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val booksRepository = application.container.booksRepository
                BookshelfViewModel(booksRepository = booksRepository)
            }
        }
    }
}