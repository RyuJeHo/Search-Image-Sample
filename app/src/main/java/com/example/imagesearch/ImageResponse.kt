package com.example.imagesearch

data class ImageResponse(
    val meta: MetaResponse,
    val documents: MutableList<DocumentsResponse>
)

data class MetaResponse(
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean
)

data class DocumentsResponse(
    val image_url: String
)