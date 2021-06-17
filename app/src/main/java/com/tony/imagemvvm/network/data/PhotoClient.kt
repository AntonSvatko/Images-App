package com.tony.imagemvvm.network.data

import android.util.Log
import com.tony.imagemvvm.network.data.api.PhotosService
import com.tony.imagemvvm.network.data.constants.ApiConstants
import com.tony.imagemvvm.network.data.exeptions.PhotosException
import com.tony.imagemvvm.network.data.result.ResultOf
import com.tony.imagemvvm.network.data.vo.Photo
import com.tony.imagemvvm.network.data.vo.size.Size

class PhotoClient(private val apiService: PhotosService) {
    suspend fun fetchPhotos(text: String): ResultOf<List<Photo>> {
        Log.d("test2", text)
        val data = if(text != "") apiService.fetchSearch(text = text) else apiService.fetchPhotos()
        return if (data.stat == ApiConstants.API_OK)
            ResultOf.Success(data.photos.photo) else errorResponse(data.stat)
    }

    suspend fun fetchSize(id: String): ResultOf<List<Size>> {
        val data = apiService.fetchSizes(id)
        return if (data.stat == ApiConstants.API_OK)
            ResultOf.Success(data.sizes.size) else errorResponse(data.stat)
    }

    private fun errorResponse(error: String) = ResultOf.Failure(PhotosException(error))
}