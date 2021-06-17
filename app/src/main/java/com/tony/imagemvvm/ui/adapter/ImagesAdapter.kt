package com.tony.imagemvvm.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tony.imagemvvm.R
import com.tony.imagemvvm.databinding.ItemImageBinding
import com.tony.imagemvvm.network.data.vo.Photo
import com.tony.imagemvvm.network.data.vo.size.SizesParcelableData
import com.tony.imagemvvm.ui.base.fragment.BaseFragment
import com.tony.imagemvvm.ui.fragments.popular.PopularFragmentDirections
import kotlinx.android.synthetic.main.item_image.view.*

class ImagesAdapter(
    private val picasso: Picasso,
    private var items: List<Photo> = listOf()
) : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    var fragmentType: Int = -1

    fun setItems(items: List<Photo>) {
        val previousCount = itemCount

        val itemsCount = items.size - previousCount

        when {
            itemsCount > 0 -> {
                this.items = items
                notifyItemRangeInserted(previousCount, itemsCount)
            }
            itemsCount < 0 -> {
                this.items = items
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesViewHolder = ImagesViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_image,
            parent,
            false
        )
    )


    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val item = items[position]
        item.photoFragmentType = fragmentType

        val clickListener = View.OnClickListener {
            val action = when (fragmentType) {
                BaseFragment.FragmentType.POPULAR -> PopularFragmentDirections
                    .actionPopularFragmentToDetailsFragment(item)

                else -> return@OnClickListener
            }
            Log.d("asd", item.toString())
            item.lowQualityPlaceholder = (it as? ImageView)?.drawable?.toBitmap()

            it.findNavController().navigate(action)
        }

        holder.bind(items[position], clickListener)
    }

    fun setSizesForPhoto(data: SizesParcelableData){
        Log.d("asd", data.toString())
        items.find { it.id == data.photoId }?.let {
            it.sizes = data.items
        }
    }


    fun withFragmentType(fragmentType: Int): ImagesAdapter {
        this.fragmentType = fragmentType
        return this
    }


    override fun getItemCount() = items.size

    inner class ImagesViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo, onClickListener: View.OnClickListener) {
            binding.photo = photo
            binding.picasso = picasso
            binding.clickListener = onClickListener
        }
    }

}