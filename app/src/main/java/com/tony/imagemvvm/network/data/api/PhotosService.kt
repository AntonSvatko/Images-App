package com.tony.imagemvvm.network.data.api

import com.tony.imagemvvm.network.data.vo.Response
import com.tony.imagemvvm.network.data.vo.size.ResponseSize
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosService {

    @GET("/services/rest/")
    suspend fun fetchPhotos(
        @Query("method") method: String = PHOTOS_LIST_METHOD,
        @Query("extras") extras: String = PHOTO_EXTRAS
    ): Response

    @GET("/services/rest/")
    suspend fun fetchSearch(
        @Query("method") method: String = PHOTOS_SEARCH_METHOD,
        @Query("text") text: String,
        @Query("extras") extras: String = PHOTO_EXTRAS
    ): Response

    @GET("/services/rest/")
    suspend fun fetchSizes(
        @Query("photo_id") id: String,
        @Query("method") method: String = PHOTO_SIZES_METHOD
    ): ResponseSize


    companion object {
        private const val PHOTOS_LIST_METHOD = "flickr.interestingness.getList"
        private const val PHOTOS_SEARCH_METHOD = "flickr.photos.search"
        private const val PHOTO_SIZES_METHOD = "flickr.photos.getSizes"
        private const val PHOTO_EXTRAS = "url_s"
    }
}