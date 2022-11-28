package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.network.GoogleBoosApiService

interface BooksRepository {
    suspend fun getBooks(query: String) : List<Book>
    suspend fun getBookById(bookId: String) : Book

}

class DefaultBooksRepository(
    private val googleBoosApiService: GoogleBoosApiService
) : BooksRepository{
    override suspend fun getBooks(query: String): List<Book> {
        val formattedQuery = query.trim().replace(" ", "%20")
        return googleBoosApiService.getBookList(formattedQuery)
    }

    override suspend fun getBookById(bookId: String): Book {
        return getBookById(bookId)
    }

}