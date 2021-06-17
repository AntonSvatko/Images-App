package com.tony.imagemvvm.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tony.imagemvvm.network.data.vo.Photo
import com.tony.imagemvvm.ui.adapter.ImagesAdapter
import com.tony.imagemvvm.ui.decor.ImageDecoration

@BindingAdapter("adapter", requireAll = true)
fun adapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.addItemDecoration(ImageDecoration())
    view.adapter = adapter
}

@BindingAdapter("photosList")
fun photosList(view: RecyclerView, list: List<Photo>?) {
    list?.let {
        (view.adapter as? ImagesAdapter)?.setItems(it)
    }
}

