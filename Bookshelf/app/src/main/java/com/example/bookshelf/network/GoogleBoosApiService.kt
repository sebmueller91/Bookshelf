package com.example.bookshelf.network

import com.example.bookshelf.model.Book
import retrofit2.http.GET
import retrofit2.http.Path

interface GoogleBoosApiService {
    @GET("volumes?q={query}")
    suspend fun getBookList(@Path("query") searchQuery: String) : List<Book>

    @GET("volumes/{id}")
    suspend fun getBookById(@Path("id") bookId: String) : Book
}