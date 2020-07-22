package com.example.cavistacodetest.uibase.edittexts

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.example.cavistacodetest.R

class CustomEditTextRegular : AppCompatEditText {
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
        this.textDirection = View.TEXT_DIRECTION_LOCALE
//        this.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        this.background = context.getDrawable(R.drawable.custom_edittext_style)
            this.setPadding(40, 20, 20, 20)
    }


}
