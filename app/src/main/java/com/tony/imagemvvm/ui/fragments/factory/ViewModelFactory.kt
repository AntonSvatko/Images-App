package com.tony.imagemvvm.ui.fragments.factory

import android.content.Context
import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.tony.imagemvvm.network.data.PhotoClient
import com.tony.imagemvvm.network.data.api.PhotoServiceClient
import com.tony.imagemvvm.ui.fragments.details.DetailsViewModel
import com.tony.imagemvvm.ui.fragments.popular.PopularViewModel
import retrofit2.Retrofit

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val picasso: Picasso, private val photoClient: PhotoClient) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass.name) {
            DetailsViewModel::class.java.name -> DetailsViewModel(
                picasso,
                photoClient
            ) as T
            PopularViewModel::class.java.name -> PopularViewModel(photoClient) as T
            else -> throw IllegalArgumentException("View model dont exist")
        }


    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context) = instance ?: ViewModelFactory(
            Picasso.Builder(context).build(),
            PhotoClient(PhotoServiceClient.getClient(context))
        ).also { instance = it }

    }

}