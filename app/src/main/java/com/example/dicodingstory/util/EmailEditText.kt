package com.example.dicodingstory.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.dicodingstory.R

class EmailEditText : AppCompatEditText {

    private lateinit var warningImage: Drawable


    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Menambahkan hint pada editText
        hint = "Email"

        // Menambahkan text aligmnet pada editText
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init(){
        warningImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_warning_24) as Drawable
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty())
                    if(!Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()){
                        showWarningImage()
                    }else{
                        hideWarningImage()
                    }

                else
                    hideWarningImage()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

    }

    // Menampilkan image
    private fun showWarningImage() {
        setDrawables(endOfTheText = warningImage)

    }

    // Menghilangkan image
    private fun hideWarningImage() {
        setDrawables()
    }

    private fun setDrawables(startOfTheText: Drawable? = null, topOfTheText: Drawable? = null, endOfTheText: Drawable? = null, bottomOfTheText: Drawable? = null){
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
    }

}