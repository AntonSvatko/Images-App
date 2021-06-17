package com.tony.imagemvvm.network.data.vo


data class ResponseData(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)