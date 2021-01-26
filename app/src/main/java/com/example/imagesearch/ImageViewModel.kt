package com.example.imagesearch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageViewModel : ViewModel() {

    val imageRepository = ImageRepository()
    val isSuccessNetwork = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun getImageList(keyword: String, page: Int) {

        isLoading.value = true
        if (page == 1) {
            imageRepository.documentList.clear()
            imageRepository.isEnd = false
        }

        val param: HashMap<String, Any> = hashMapOf()
        param.put("query", keyword)
        param.put("page", page)
        param.put("size", 10)
        Log.e("jhjh", "param : " + param)

        imageRepository.imageLoad(param,
            onResponse = {
                if (it.isSuccessful) {
                    Log.e("jhjh", "total_count : ${it.body()!!.meta.total_count}")
                    if (it.body()!!.meta.is_end) {
                        imageRepository.isEnd = true
                    }
                    imageRepository.docuSize = it.body()!!.documents.size
                    if (imageRepository.docuSize > 0) {
                        (0..imageRepository.docuSize - 1).forEach { i ->
                            imageRepository.documentList.add(it.body()!!.documents[i])
                        }
                        Log.e("jhjh", "documentList.size : ${imageRepository.documentList.size}")
                        isSuccessNetwork.value = true
                    } else {
                        isSuccessNetwork.value = false
                    }
                } else {
                    isSuccessNetwork.value = false
                }
                isLoading.value = false
            }, onFailure = {
                isSuccessNetwork.value = false
                isLoading.value = false
            })

    }

}