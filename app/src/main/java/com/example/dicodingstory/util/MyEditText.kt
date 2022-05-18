package com.example.dicodingstory.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.dicodingstory.R

class MyEditText : AppCompatEditText, View.OnTouchListener  {

    private lateinit var clearButtonImage: Drawable
    private lateinit var emailImage: Drawable
    private lateinit var passwordImage: Drawable
    private var isEmail: Boolean = false
    private var isPassword: Boolean = false

    constructor(context: Context) : super(context) {
        init(null,0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs,0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        gravity =Gravity.CENTER_VERTICAL

    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int)
    {
        val style = context.obtainStyledAttributes(attrs, R.styleable.MyEditText, defStyleAttr, 0)

        isEmail = style.getBoolean(R.styleable.MyEditText_email, false)
        isPassword = style.getBoolean(R.styleable.MyEditText_password, false)
        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24) as Drawable
        emailImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_email_24) as Drawable
        passwordImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_vpn_key_24) as Drawable

        if (isEmail) {
            setButtonDrawables(startOfTheText = emailImage)
        } else if (isPassword) {
            setButtonDrawables(startOfTheText = passwordImage)
        }

        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val input = s.toString()
                val emailError = resources.getString(R.string.invalid_email)
                val passwordError = resources.getString(R.string.invalid_password)

                if (s.toString().isNotEmpty()){
                    showClearButton()
                } else {
                    hideClearButton()
                }

                if (isPassword && input.length < 6 && input.isNotEmpty()) {
                    error = passwordError
                } else if (isEmail && !Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                    error = emailError
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })

        style.recycle()


    }

    private fun showClearButton() {
        when {
            isEmail -> {
                setButtonDrawables(
                    startOfTheText = emailImage,
                    endOfTheText = clearButtonImage
                )
            }
            isPassword -> {
                setButtonDrawables(
                    startOfTheText = passwordImage,
                    endOfTheText = clearButtonImage
                )

            }
            else -> {
                setButtonDrawables(endOfTheText = clearButtonImage)

            }
        }

    }

    private fun hideClearButton() {
        when {
            isEmail -> {
                setButtonDrawables(
                    startOfTheText = emailImage
                )
            }
            isPassword -> {
                setButtonDrawables(
                    startOfTheText = passwordImage
                )

            }
            else -> {
                setButtonDrawables()

            }
        }
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }


    override fun onTouch(p0: View?, p1: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    p1.x < clearButtonEnd -> isClearButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()
                when {
                    p1.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            if (isClearButtonClicked) {
                when (p1.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24) as Drawable
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }

}