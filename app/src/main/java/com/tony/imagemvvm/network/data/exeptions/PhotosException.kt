package com.tony.imagemvvm.network.data.exeptions

class PhotosException(errorCode: String? = null) :
    Exception("Failed to load photos, errorCode = ${errorCode ?: "UNKNOWN"}!")
