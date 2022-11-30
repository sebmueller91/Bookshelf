package com.example.bookshelf.data

import android.util.Log
import com.example.bookshelf.model.Book
import com.example.bookshelf.network.GoogleBooksApiService

private const val TAG: String = "BooksRespository"

interface BooksRepository {
    suspend fun getBooks(query: String): List<Book>
    suspend fun getBookById(bookId: String): Book

}

class DefaultBooksRepository(
    private val googleBooksApiService: GoogleBooksApiService
) : BooksRepository {
    override suspend fun getBooks(query: String): List<Book> {
        val formattedQuery = query.trim().replace(" ", "%20")

        if (formattedQuery.isEmpty()) {
            return listOf<Book>()
        } else {
            var retrievedBookList = googleBooksApiService.getBookList(formattedQuery)

            var deepListOfBooks = mutableListOf<Book>()

            retrievedBookList?.items?.take(10)?.forEach() {
                Log.d(TAG, "Iterating book for query <${query}>")
                var book = googleBooksApiService.getBookById(it.id)
                //var modifiedBook = book.copyWithDifferentThumbnail(book.volumeInfo.imageLinks.thumbnail.replace("http", "https"))
                deepListOfBooks.add(book)
            }

            return deepListOfBooks
        }
    }

    override suspend fun getBookById(bookId: String): Book {
        return getBookById(bookId)
    }

}