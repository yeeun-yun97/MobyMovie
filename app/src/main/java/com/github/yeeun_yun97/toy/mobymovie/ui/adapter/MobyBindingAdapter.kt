package com.github.yeeun_yun97.toy.mobymovie.ui.adapter

import android.content.res.ColorStateList
import android.content.res.Configuration
import android.text.Html
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColor
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.yeeun_yun97.toy.mobymovie.R

object MobyBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageByUrl(view: ImageView, url: String) {
        Glide.with(view.context).clear(view)
        view.imageTintList = null
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(url)
                .centerCrop()
                .into(view)
        } else {
            view.setImageDrawable(
                ResourcesCompat.getDrawable(
                    view.context.resources,
                    R.drawable.image_def_movie_thumb,
                    view.context.theme
                )
            )
            val colorStateList = view.context.obtainStyledAttributes(
                TypedValue().data,
                arrayOf(com.google.android.material.R.attr.colorOnBackground).toIntArray()
            ).getColorStateList(0)
            view.imageTintList = colorStateList
        }
    }

    @JvmStatic
    @BindingAdapter("htmlText")
    fun setTextWithHtmlStyle(view: TextView, content: String) {
        val text = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
        view.setText(text)
    }


}
