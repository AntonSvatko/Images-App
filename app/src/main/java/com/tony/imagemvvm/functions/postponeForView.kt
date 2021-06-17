package com.tony.imagemvvm.functions

import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.tony.imagemvvm.network.data.vo.Photo
import java.util.concurrent.TimeUnit

fun Fragment.postponeForView(view: View) {
    postponeEnterTransition(2, TimeUnit.SECONDS)
    view.doOnPreDraw {
        startPostponedEnterTransition()
    }
}

fun Photo.validate() = apply{
    if (sizes == null) sizes = emptyList()

}