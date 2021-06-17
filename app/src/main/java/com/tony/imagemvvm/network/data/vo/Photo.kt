package com.tony.imagemvvm.network.data.vo


import android.graphics.Bitmap
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tony.imagemvvm.network.data.vo.size.Size
import com.tony.imagemvvm.ui.base.fragment.BaseFragment
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val farm: Int,
    @SerializedName("height_s")
    val heightS: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
    var sizes: List<Size>? = listOf(),
    @SerializedName("url_s")
    val urlS: String,
    @SerializedName("width_s")
    val widthS: Int,
    var isSaved: Boolean = false
) : Parcelable {
    @IgnoredOnParcel var photoFragmentType = BaseFragment.FragmentType.POPULAR
    @IgnoredOnParcel var lowQualityPlaceholder: Bitmap? = null
}