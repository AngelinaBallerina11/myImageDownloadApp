package com.angelinaandronova.myimagedownloadapplication.ui.main

import android.arch.lifecycle.MutableLiveData
import android.support.v4.content.ContextCompat
import com.angelinaandronova.myimagedownloadapplication.network.model.ImageResponse
import com.angelinaandronova.myimagedownloadapplication.network.service.ImageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.HTTP

class MainRepository(private val retrofit: Retrofit) {

    private fun getImageService(): ImageService = retrofit.create(ImageService::class.java)

    fun downloadImage(username: String, liveData: MutableLiveData<ImageDownloadResult>) {
        getImageService().downloadImage(username).enqueue(object : Callback<ImageResponse> {
            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                liveData.value = DownloadFailed(t)
            }

            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        liveData.value = DownloadSuccessful(it.image)
                    }
                } else {
                    liveData.value = DownloadFailed(
                        when (response.code()) {
                            401 -> Unauthorized()
                            403 -> Forbidden()
                            404 -> NotFound()
                            in 500..599 -> InternalServerError()
                            else -> General()
                        }
                    )
                }
            }
        })
    }
}

sealed class ImageDownloadHttpCodes : Throwable()
class Unauthorized : ImageDownloadHttpCodes()
class Forbidden : ImageDownloadHttpCodes()
class NotFound : ImageDownloadHttpCodes()
class InternalServerError: ImageDownloadHttpCodes()
class General : ImageDownloadHttpCodes()

sealed class ImageDownloadResult
data class DownloadSuccessful(val base64EncodedString: String) : ImageDownloadResult()
data class DownloadFailed(val throwable: Throwable) : ImageDownloadResult()
