package com.tony.imagemvvm

import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.setDefaultStatusBar(){
    setStatusBarColor(R.color.veryDarkGray)
}

fun Fragment.setBlackStatusBar(){
    setStatusBarColor(R.color.black)
}

private fun Fragment.setStatusBarColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), color)
    }
}