package com.tony.imagemvvm.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class ImageViewModel : ViewModel() {
    fun launchSafely(
            onError: ((Throwable?) -> Unit)? = null,
            onCallBack: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch (CoroutineExceptionHandler { _, throwable ->
            onError?.invoke(throwable)?: throwable.printStackTrace()
        }){
            onCallBack()
        }
    }

}