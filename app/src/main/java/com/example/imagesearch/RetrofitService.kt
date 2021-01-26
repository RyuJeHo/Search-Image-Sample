package com.example.imagesearch

import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    companion object {
        const val BASE_URL = "https://dapi.kakao.com"
        const val VERSION = "v2"
        const val REST_API_KEY = "dae6a385d17596490eb615bb233d9ad0"
    }

    @Headers(
        "accept: application/json",
        "content-type: application/json",
        "Authorization: KakaoAK $REST_API_KEY"
    )

    @GET("/$VERSION/search/image")
    fun searchWeb(
        @QueryMap serviceKey: HashMap<String, Any>
    ): Call<ImageResponse>

}