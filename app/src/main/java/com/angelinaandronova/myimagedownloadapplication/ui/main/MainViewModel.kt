package com.angelinaandronova.myimagedownloadapplication.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepo: MainRepository) : ViewModel() {

    val image: MutableLiveData<ImageDownloadResult> = MutableLiveData()

    fun downloadImage(username: String) {
        mainRepo.downloadImage(username, image)
    }

}
