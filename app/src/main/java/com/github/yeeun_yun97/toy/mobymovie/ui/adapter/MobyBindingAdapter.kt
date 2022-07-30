package com.github.yeeun_yun97.toy.mobymovie.ui.adapter

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.yeeun_yun97.toy.mobymovie.R

object MobyBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageByUrl(view: ImageView, url: String) {
        Glide.with(view.context).clear(view)
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(url)
                .centerCrop()
                .into(view)
        } else {
            view.setImageDrawable(
                ResourcesCompat.getDrawable(
                    view.context.resources,
                    R.drawable.img_def_movie,
                    view.context.theme
                )
            )
        }
    }

    @JvmStatic
    @BindingAdapter("htmlText")
    fun setTextWithHtmlStyle(view: TextView, content: String) {
        val text = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
        view.setText(text)
    }


}
