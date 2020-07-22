package com.etpl.mcmsuser.uibase.buttons

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.example.cavistacodetest.R

class CustomButtonMedium : AppCompatButton {
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
        background = context.getDrawable(R.drawable.custom_button_background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextColor(context.getColor(R.color.white))
        }
    }
}
