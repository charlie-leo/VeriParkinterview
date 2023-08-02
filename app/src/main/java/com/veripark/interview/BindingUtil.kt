package com.veripark.interview

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object BindingUtil {

    /**
     * Method use to show and hide the error for the TextInputLayout in xml.
     * @param value Binding variable error message.
     */
    @BindingAdapter("android:errorText")
    @JvmStatic fun TextInputLayout.errorText(value: String){
        if (value.isNotEmpty() && value.isNotBlank()){
            this.isErrorEnabled = true
            this.error = value
        } else {
            this.isErrorEnabled = false
            this.error = ""
        }
    }
}