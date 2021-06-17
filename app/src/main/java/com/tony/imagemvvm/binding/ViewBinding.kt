package com.tony.imagemvvm.binding

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.tony.imagemvvm.functions.picassoCallback
import com.tony.imagemvvm.network.data.exeptions.NoConnectivityException
import com.tony.imagemvvm.network.data.vo.Photo
import com.tony.imagemvvm.network.data.vo.size.Size
import com.tony.imagemvvm.network.size.Label


@BindingAdapter("isProgress")
fun bindProcess(progressBar: ProgressBar, isProgress: Boolean) {
    progressBar.visibility = if (isProgress) View.VISIBLE else View.GONE
}

@BindingAdapter("photo", "picasso", "isDetails", "sizes", requireAll = false)
fun bindPhotoImage(
    imageView: ImageView,
    photo: Photo,
    picasso: Picasso,
    isDetails: Boolean = false,
    sizes: List<Size>? = null
) {
    if (isDetails) {
        val neededPhotoSize = (sizes ?: photo.sizes)?.find { it.labelEnum == Label.MEDIUM }
        Log.d("need", neededPhotoSize.toString())
        val placeholderDrawable =
            BitmapDrawable(imageView.resources, photo.lowQualityPlaceholder)

        picasso.load(neededPhotoSize?.source).placeholder(placeholderDrawable).into(imageView)

    } else {

        picasso.load(photo.urlS).into(imageView)
    }
}

@BindingAdapter("toast")
fun bindFailedToast(progressBar: ProgressBar, throwable: Throwable?) {
    throwable?.let {
        val context = progressBar.context
        val resources = context.resources

        val text = when (it) {
            is NoConnectivityException -> "Failed Connection"
            else -> "Failed Load"
        }

        it.printStackTrace()

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
