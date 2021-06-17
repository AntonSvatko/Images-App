package com.tony.imagemvvm.ui.fragments.popular

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.squareup.picasso.Picasso
import com.tony.imagemvvm.R
import com.tony.imagemvvm.databinding.FragmentPopularBinding
import com.tony.imagemvvm.functions.*
import com.tony.imagemvvm.network.data.vo.size.SizesParcelableData
import com.tony.imagemvvm.ui.adapter.ImagesAdapter
import com.tony.imagemvvm.ui.base.fragment.BaseFragment
import com.tony.imagemvvm.ui.factory.ViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class PopularFragment : BaseFragment<FragmentPopularBinding>(R.layout.fragment_popular) {
    lateinit var imagesAdapter: ImagesAdapter

    lateinit var manager: LocalBroadcastManager

    private val receiver = photoReceiver(::handleReceiver)

    lateinit var viewModel: PopularViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        manager = LocalBroadcastManager.getInstance(requireContext())
        imagesAdapter = ImagesAdapter(Picasso.Builder(requireContext()).build())

        manager.registerReceiver(receiver, defaultFilter)
    }


    private fun search(editText: EditText) {
        GlobalScope.launch() {
            editText.onTextChanged()
                .debounce(1, TimeUnit.SECONDS)
                .consumeEach {
                    viewModel.getPhotos(it)
                }
        }
    }


    private fun handleReceiver(intent: Intent) {
        when (intent.action) {
            SIZES_ACTION -> intent.getParcelableExtra<SizesParcelableData>(SIZES_EXTRA)?.let {
                imagesAdapter.setSizesForPhoto(it)
            }
        }
    }

    override fun setupBinding(binding: FragmentPopularBinding) {
        viewModel =
            ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(
                PopularViewModel::class.java
            )
        binding.viewModel = viewModel


        binding.adapter = imagesAdapter.withFragmentType(fragmentType)

//        binding.searchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
//            actionId == EditorInfo.IME_ACTION_DONE
//        })

        search(binding.searchEditText)


        if (requirePostpone)
            postponeForView(binding.popularRecyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.unregisterReceiver(receiver)
    }

}