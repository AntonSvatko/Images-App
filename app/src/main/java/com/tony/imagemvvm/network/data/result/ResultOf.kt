package com.tony.imagemvvm.network.data.result

sealed class ResultOf<out T> {
    data class Success<out R>(val data: R) : ResultOf<R>()
    data class Failure(val throwable: Throwable) : ResultOf<Nothing>()
}