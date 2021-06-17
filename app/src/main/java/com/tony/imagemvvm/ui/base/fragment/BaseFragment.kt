package com.tony.imagemvvm.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tony.imagemvvm.R
import com.tony.imagemvvm.setBlackStatusBar
import com.tony.imagemvvm.setDefaultStatusBar
import com.tony.imagemvvm.ui.main.MainActivity
import java.lang.IllegalArgumentException

abstract class BaseFragment<T: ViewDataBinding>(private val resId: Int) : Fragment() {


    private val mainActivity: MainActivity
        get() = requireActivity() as MainActivity

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, resId, container, false
        )

        binding.lifecycleOwner = this
        setupBinding(binding)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentType == FragmentType.DETAILS) setupDetailedFragment()
    }

    private fun setupDetailedFragment() {
        setBlackStatusBar()
        mainActivity.hideUI()
    }

    abstract fun setupBinding(binding: T)

    override fun onDestroyView() {
        super.onDestroyView()
        if (fragmentType == FragmentType.DETAILS) setupDefaultFragment()
    }

    private fun setupDefaultFragment() {
        setDefaultStatusBar()
        mainActivity.showUI()
    }

    protected val requirePostpone: Boolean
        get() = mainActivity.requirePostpone.also {
            mainActivity.requirePostpone = false
        }

    protected val fragmentType: Int
        get() = when (resId) {
            R.layout.fragment_popular -> FragmentType.POPULAR
//            R.layout.fragment_favorites -> FragmentType.FAVORITES
            R.layout.fragment_details -> FragmentType.DETAILS
            else -> throw IllegalArgumentException("Add correct resource id.")
        }

    object FragmentType {
        const val POPULAR = 0b001
        const val FAVORITES = 0b010
        const val DETAILS = 0b100
    }

}