package com.tony.imagemvvm.network.data.vo.size

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SizesParcelableData(
    val photoId: String,
    val items: List<Size>
) : Parcelable