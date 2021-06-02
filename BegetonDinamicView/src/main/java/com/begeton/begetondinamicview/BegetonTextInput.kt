package com.begeton.begetondinamicview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.begeton_text_input.view.*
import top.defaults.drawabletoolbox.DrawableBuilder

class BegetonTextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    var inputHint: String = ""
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }


    var descriptionText: String = ""
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var errorText: String = ""
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var inputTextColor: Int = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var descriptionTextColor = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var defaultBoxColor: Int = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var focusedBoxColor = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var errorColor = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var defaultBoxLineWidth: Float = 0f
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var focusedBoxLineWidth: Float = 0f
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var errorBoxLineWidth: Float = 0f
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var boxCornerRadius: Float = 0f
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var errorIconRes: Int = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var iconRes:Int = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var errorEnabled: Boolean = false
        set(value) {
            field = value
            setErrorState(field)
        }

    var maxLineInputCount: Int = 1
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var highLightTextBackground: Int = 0
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }


    init {
        readAttrs(context, attrs)
        inflate(context, R.layout.begeton_text_input, this)
        defineViewLogic()
    }

    private fun readAttrs(context: Context, attrs: AttributeSet?){
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BegetonTextInput,
            0,
            0)
            .apply {
                try {
                    inputHint = getString(R.styleable.BegetonTextInput_inputHint)?: ""
                    descriptionText = getString(R.styleable.BegetonTextInput_descriptionText)?: ""
                    errorText = getString(R.styleable.BegetonTextInput_errorText)?: ""
                    inputTextColor = getColor(R.styleable.BegetonTextInput_inputTextColor, 0)
                    descriptionTextColor = getColor(R.styleable.BegetonTextInput_descriptionTextColor, 0)
                    defaultBoxColor = getColor(R.styleable.BegetonTextInput_defaultBoxColor, 0)
                    focusedBoxColor = getColor(R.styleable.BegetonTextInput_focusedBoxColor, 0)
                    errorColor = getColor(R.styleable.BegetonTextInput_errorColor, 0)
                    defaultBoxLineWidth = getDimension(R.styleable.BegetonTextInput_defaultBoxLineWidth, 1f)
                    focusedBoxLineWidth = getDimension(R.styleable.BegetonTextInput_focusedBoxLineWidth, 1f)
                    errorBoxLineWidth = getDimension(R.styleable.BegetonTextInput_errorBoxLineWidth, 1f)
                    boxCornerRadius = getDimension(R.styleable.BegetonTextInput_boxCornerRadius, 4f)
                    iconRes = getResourceId(R.styleable.BegetonTextInput_icon, 0)
                    errorIconRes = getResourceId(R.styleable.BegetonTextInput_errorIcon, 0)
                    maxLineInputCount = getInt(R.styleable.BegetonTextInput_inputMaxLineCount, Int.MAX_VALUE)
                    highLightTextBackground = getColor(R.styleable.BegetonTextInput_textIndicatorBackgroundColor, 0)
                } finally {
                    recycle()
                }
            }
    }

    fun defineViewLogic() {
        setContainerDefault()
       if(inputHint.isNotBlank()) {
           text_input_higlight_view.text = inputHint
           text_input_higlight_view.setTextColor(descriptionTextColor)
           text_input.hint = inputHint
           text_input.setHintTextColor(descriptionTextColor)
           text_input.setTextColor(inputTextColor)
           text_input.maxLines = maxLineInputCount
           text_input_higlight_view.setBackgroundColor(highLightTextBackground)

           text_input.onFocusChangeListener = OnFocusChangeListener() { v, hasFocus ->
                    if (hasFocus) {
                        setContainerFocused()
                        text_input_higlight_view.visibility = View.VISIBLE
                    } else {
                        setContainerDefault()
                        if(text_input.text.isNullOrEmpty())
                        text_input_higlight_view.visibility = View.GONE
                    }
                }
           text_input.addTextChangedListener(object: TextWatcher{
               override fun beforeTextChanged(
                   s: CharSequence?,
                   start: Int,
                   count: Int,
                   after: Int
               ) {}

               override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                   if(s.isNullOrEmpty().not())
                       text_input_higlight_view.visibility = View.VISIBLE
               }

               override fun afterTextChanged(s: Editable?) {}

           })
       }

        if(descriptionText.isNotBlank()){
            text_input_description.text = descriptionText
            text_input_description.setTextColor(descriptionTextColor)
            text_input_description.visibility = View.VISIBLE
        } else {
            text_input_description.visibility = View.GONE
        }

        if(iconRes != 0){
            text_input_icon.setImageDrawable(context.getDrawable(iconRes))
        }
    }

    fun setContainerDefault(){
        val backGroundDrawable = DrawableBuilder()
            .rectangle()
            .cornerRadius(boxCornerRadius.toInt())
            .strokeColor(defaultBoxColor)
            .strokeWidth(defaultBoxLineWidth.toInt())
            .width(text_input_container.width)
            .height(text_input_container.height)
            .build()

        text_input_container.background = backGroundDrawable
    }

    fun setContainerFocused(){
        val backGroundDrawable = DrawableBuilder()
            .rectangle()
            .cornerRadius(boxCornerRadius.toInt())
            .strokeColor(focusedBoxColor)
            .strokeWidth(focusedBoxLineWidth.toInt())
            .width(text_input_container.width)
            .height(text_input_container.height)
            .build()

        text_input_container.background = backGroundDrawable
    }

    fun setContainerError(){
        val backGroundDrawable = DrawableBuilder()
            .rectangle()
            .cornerRadius(boxCornerRadius.toInt())
            .strokeColor(errorColor)
            .strokeWidth(errorBoxLineWidth.toInt())
            .width(text_input_container.width)
            .height(text_input_container.height)
            .build()

        text_input_container.background = backGroundDrawable
    }

    fun setErrorState(field: Boolean) {
        if(field){
            setContainerError()
            if(errorText.isNotBlank()){
                text_input_description.text = errorText
                text_input_description.visibility = View.VISIBLE
                text_input_description.setTextColor(errorColor)
            } else {
                text_input_description.visibility = View.GONE
            }

            if(errorIconRes != 0){
                text_input_icon.setImageDrawable(context.getDrawable(errorIconRes))
            }
        } else {
            if(iconRes != 0){
                text_input_icon.setImageDrawable(context.getDrawable(iconRes))
            }

            if(descriptionText.isBlank())
                text_input_description.visibility = GONE
            else {
                text_input_description.text = descriptionText
                text_input_description.setTextColor(descriptionTextColor)
                text_input_description.visibility = VISIBLE
            }

            if(text_input.hasFocus()){
                setContainerFocused()
            } else {
                setContainerDefault()
            }

            if(text_input.text.isNotBlank())
                text_input_higlight_view.visibility = View.VISIBLE
            else
                text_input_higlight_view.visibility = View.GONE
        }
    }

    fun getTextField(): EditText {
        invalidate()
        return text_input
    }

    fun setHint(hint: String){
        inputHint = hint
        text_input_higlight_view.text = hint
        text_input.hint = hint
    }

    fun setUnfocusable(){
        text_input.isFocusable = false
        text_input.isFocusableInTouchMode = false
        text_input.isClickable = false
    }

    fun getIconView(): ImageView  {
        invalidate()
        text_input_icon.visibility = VISIBLE
        return text_input_icon
    }

    fun setAssistiveText(textStringRes: Int){
        setAssistiveText(context.getString(textStringRes))
    }

    fun setAssistiveText(text: String){
        invalidate()
        descriptionText = text
        text_input_description.text = text
        text_input_description.visibility = VISIBLE
    }

    fun getRoot():ConstraintLayout {
        invalidate()
        return text_input_root
    }
}