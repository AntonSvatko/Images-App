package com.tony.imagemvvm.ui.squareview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class SquareLayout(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            widthMeasureSpec
        )
    }


}