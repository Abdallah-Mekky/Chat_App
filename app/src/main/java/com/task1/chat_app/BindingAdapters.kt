package com.task1.chat_app

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:Error")
fun setError(textInputLayout: TextInputLayout, errorMessage: String?) {
    textInputLayout.error = errorMessage

}

@BindingAdapter("imagesrc")
fun setImage(imageView: ImageView, path: Int) {

    imageView.setImageResource(path)
}