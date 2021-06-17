package com.tony.imagemvvm.functions

import com.squareup.picasso.Callback

inline fun picassoCallback(crossinline onSuccess: () -> Unit) = object : Callback {
    override fun onSuccess() {
        onSuccess()
    }

    override fun onError(e: Exception?) {

    }
}