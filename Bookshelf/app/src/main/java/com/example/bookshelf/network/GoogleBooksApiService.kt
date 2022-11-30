package com.example.bookshelf.network

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksApiService {
    @GET("volumes")
    suspend fun getBookList(@Query("q") searchQuery: String) : BookList

    @GET("volumes/{id}")
    suspend fun getBookById(@Path("id") bookId: String) : Book
}