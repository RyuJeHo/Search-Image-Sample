package com.example.imagesearch

import android.util.Log
import com.example.hyunsikyoo.retrofit_example_kotlin.retrofit.RetrofitCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageDataSource {
    fun imageLoad(param: HashMap<String, Any>, onResponse: (Response<ImageResponse>) -> Unit, onFailure: (Throwable) -> Unit) {
        RetrofitCreator.getService().searchWeb(param).enqueue(object : Callback<ImageResponse> {
            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Log.e("jhjh", "t : " + t)
                onFailure(t)
            }

            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                Log.e("jhjh", "response.body() : " + response.body())
                onResponse(response)
            }
        })
    }
}