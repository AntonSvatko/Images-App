package com.tony.imagemvvm.network.data.vo.size


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tony.imagemvvm.network.size.Label
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Size(
    val height: Int,
    val label: String,
    val media: String,
    val source: String,
    val url: String,
    val width: Int
) : Parcelable {
    val labelEnum: Label
        get() = when (label) {
            "Thumbnail" -> Label.THUMBNAIL
            "Large Square" -> Label.SQUARE
            "Medium" -> Label.MEDIUM
            else -> Label.UNDEFINED
        }
}