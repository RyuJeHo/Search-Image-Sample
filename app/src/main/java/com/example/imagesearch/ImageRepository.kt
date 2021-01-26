package com.example.imagesearch

import retrofit2.Response

class ImageRepository {

    var documentList: MutableList<DocumentsResponse> = mutableListOf()
    var isEnd = false
    var docuSize = 0

    fun imageLoad(
        param: HashMap<String, Any>,
        onResponse: (Response<ImageResponse>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ImageDataSource().imageLoad(param, onResponse, onFailure)
    }

}