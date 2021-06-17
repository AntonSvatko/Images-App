package com.tony.imagemvvm.ui.fragments.details

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import com.tony.imagemvvm.network.data.PhotoClient
import com.tony.imagemvvm.network.data.result.ResultOf
import com.tony.imagemvvm.network.data.vo.Photo
import com.tony.imagemvvm.network.data.vo.size.Size
import com.tony.imagemvvm.ui.base.viewmodel.ImageViewModel

class DetailsViewModel(
    val picasso: Picasso,
    val photoClient: PhotoClient
) : ImageViewModel() {

    val isFavorite = ObservableBoolean()

    var photoSizesLiveData: MutableLiveData<List<Size>> = MutableLiveData()

    fun setFavorite(isSaved: Boolean) {
        isFavorite.set(isSaved)
    }

    fun getPhotoSizes(photo: Photo) = launchSafely {
        val result = photoClient.fetchSize(photo.id)

        photoSizesLiveData.value = when (result) {
            is ResultOf.Success -> {
                photo.sizes = result.data
                photo.sizes
            }
            is ResultOf.Failure -> emptyList()
        }

    }
}