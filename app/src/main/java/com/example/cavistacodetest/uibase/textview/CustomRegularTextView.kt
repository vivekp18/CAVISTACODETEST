package com.etpl.mcmsuser.uibase.textviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomRegularTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    internal fun init() {
        val tf = Typeface.createFromAsset(context.assets, "fonts/Roboto-Regular.ttf")
        typeface = tf

    }

}
