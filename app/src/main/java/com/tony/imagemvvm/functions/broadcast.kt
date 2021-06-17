package com.tony.imagemvvm.functions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.tony.imagemvvm.network.data.vo.Photo
import com.tony.imagemvvm.network.data.vo.size.Size
import com.tony.imagemvvm.network.data.vo.size.SizesParcelableData

const val PHOTO_ACTION = "default.action"
const val SIZES_ACTION = "sizes.action"

const val PHOTO_EXTRA = "default.photo.extra"
const val SIZES_EXTRA = "sizes.extra"

fun photoReceiver(onReceive: (Intent) -> Unit) = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let(onReceive)
    }
}

val defaultFilter = IntentFilter().apply {
    addAction(PHOTO_ACTION)
    addAction(SIZES_ACTION)
}

fun createPhotoIntent(photo: Photo): Intent {
    val intent = Intent(PHOTO_ACTION)
    intent.putExtra(PHOTO_EXTRA, photo)
    return intent
}

fun createSizesIntent(photo: Photo, sizes: List<Size>): Intent {
    val intent = Intent(SIZES_ACTION)
    intent.putExtra(SIZES_EXTRA, SizesParcelableData(photo.id, sizes))
    return intent
}