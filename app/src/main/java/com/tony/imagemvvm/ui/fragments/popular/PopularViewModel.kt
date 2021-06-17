package com.tony.imagemvvm.ui.fragments.popular

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.tony.imagemvvm.network.data.PhotoClient
import com.tony.imagemvvm.network.data.result.ResultOf
import com.tony.imagemvvm.network.data.vo.Photo
import com.tony.imagemvvm.ui.base.viewmodel.ImageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PopularViewModel(
    val photoClient: PhotoClient
) : ImageViewModel() {

    val isLoading = ObservableBoolean()

    val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()

    val toastFailedLiveData = MutableLiveData<Throwable?>()


    var text = ""
    var tempText = ""
    var check = true
    var isFirst = true

    init {
        isLoading.set(true)
        launchSafely(onError = {
            toastFailedLiveData.value = it
            isLoading.set(false)
        }) {
            getPhotos(text)
        }
    }

    suspend fun getPhotos(text: String) {
        needUpdate(text)


        withContext(Dispatchers.Main) {
            val value = when (val result = photoClient.fetchPhotos(text)) {
                is ResultOf.Success -> result.data
                is ResultOf.Failure -> {
                    toastFailedLiveData.value = result.throwable
                    emptyList()
                }
            }

            photosLiveData.value = mutableListOf<Photo>()
                .apply {
                    photosLiveData.value?.let { addAll(it) }
                    if (check || isFirst) {
                        addAll(value)
                    }
                }

        }


        isLoading.set(false)
    }

    private fun needUpdate(text: String) {
        if (text != "" && text != tempText) {
            photosLiveData.postValue(emptyList())
            check = true
            isFirst = false
        } else {
            check = false
        }
        tempText = text
    }

}