package com.angelinaandronova.myimagedownloadapplication.network.service

import com.angelinaandronova.myimagedownloadapplication.network.model.ImageResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ImageService {

    companion object {
        const val USERNAME = "username"
    }

    @FormUrlEncoded
    @POST("download/bootcamp/image.php")
    fun downloadImage(@Field(USERNAME) username: String): Call<ImageResponse>
}