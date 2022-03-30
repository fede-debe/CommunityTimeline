package com.example.communitymessages.utils

import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import java.util.regex.Pattern

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, imageUrl: String?) {
    imageView.load(imageUrl)
}

@BindingAdapter("htmlText")
fun htmlText(textView: TextView, html: String) {
    val newHtml = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
    val spannableString = SpannableString(newHtml)

    val matcher = Pattern.compile("\n\n").matcher(newHtml)
    while (matcher.find()) {
        spannableString.setSpan(
            AbsoluteSizeSpan(2, true),
            matcher.start() + 1,
            matcher.end(),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
        )
    }
    textView.text = spannableString
    textView.movementMethod = LinkMovementMethod.getInstance()
}

@BindingAdapter("viewVisibility")
fun viewVisibility(imageView: View, visible: Boolean) {
    imageView.visibility = if (visible) View.VISIBLE else View.GONE
}
