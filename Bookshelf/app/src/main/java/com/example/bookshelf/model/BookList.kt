package com.example.bookshelf.model

@kotlinx.serialization.Serializable
data class BookList(
    val kind: String,
    val totalItems: Int,
    val items: List<Book>
)
