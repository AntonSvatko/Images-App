package com.tony.imagemvvm.ui.fragments.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.tony.imagemvvm.R
import com.tony.imagemvvm.databinding.FragmentDetailsBinding
import com.tony.imagemvvm.functions.createPhotoIntent
import com.tony.imagemvvm.functions.createSizesIntent
import com.tony.imagemvvm.functions.validate
import com.tony.imagemvvm.ui.base.fragment.BaseFragment
import com.tony.imagemvvm.ui.fragments.factory.ViewModelFactory

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {
    lateinit var localManager: LocalBroadcastManager

    private val navArgs: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        localManager = LocalBroadcastManager.getInstance(requireContext())
    }

    override fun setupBinding(binding: FragmentDetailsBinding) {
        val viewModel: DetailsViewModel =
            ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(
                DetailsViewModel::class.java
            )
        val photo = navArgs.photo.validate()

        val onBackClickListener = View.OnClickListener {
            it.findNavController().popBackStack()
        }

        if (photo.sizes.isNullOrEmpty()) {
            viewModel.photoSizesLiveData.observe(this) {
                localManager.sendBroadcast(createSizesIntent(photo, it))
            }
            viewModel.getPhotoSizes(photo)
        }

        viewModel.setFavorite(photo.isSaved)

        binding.photo = photo
        binding.viewModel = viewModel
        binding.onBackClickListener = onBackClickListener
    }
}