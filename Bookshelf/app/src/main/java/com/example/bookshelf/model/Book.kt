package com.example.bookshelf.model

@kotlinx.serialization.Serializable
data class Book(
    val id: String,
    val description: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
) {
    fun getThumbnailAsHttps(): String? {
        return volumeInfo?.imageLinks?.thumbnail?.replace("http", "https")
    }

    fun getPrice() : String {
        if (saleInfo == null || saleInfo.listPrice == null) {
            return ""
        }
        return "${saleInfo.listPrice.amount} ${saleInfo.listPrice.currency}"
    }

    fun getFirstThreeAuthors(): String {
        var authors = "${getAuthorAtIndexOrEmpty(0)}, ${getAuthorAtIndexOrEmpty(1)}, ${getAuthorAtIndexOrEmpty(2)}"
        return authors
    }

    private fun getAuthorAtIndexOrEmpty(index: Int): String {
        if (volumeInfo == null || volumeInfo.authors == null || volumeInfo.authors.size <= index) {
            return ""
        }
        return volumeInfo.authors[index]
    }
}

@kotlinx.serialization.Serializable
data class VolumeInfo(
    val title: String,
    val subtitle: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val imageLinks: ImageLinks
)

@kotlinx.serialization.Serializable
data class ImageLinks(
    val thumbnail: String
)

@kotlinx.serialization.Serializable
data class SaleInfo(
    val country: String,
    val isEbook: Boolean,
    val listPrice: ListPrice
)

@kotlinx.serialization.Serializable
data class ListPrice(
    val amount: Float,
    val currency: String
)