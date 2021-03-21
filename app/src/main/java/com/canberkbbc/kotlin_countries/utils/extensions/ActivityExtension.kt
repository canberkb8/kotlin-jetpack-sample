package com.canberkbbc.kotlin_countries.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.canberkbbc.kotlin_countries.R

fun Activity.changeFragment(fragmentId: Int) {
    this.findNavController(R.id.nav_host_fragment).navigate(fragmentId)
}

fun Activity.changeFragment(navAction: NavDirections) {
    this.findNavController(R.id.nav_host_fragment).navigate(navAction)
}

fun ImageView.getImage(url:String?,progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun placeHolderProgressBar(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=40f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImage(view:ImageView,url:String?){
    view.getImage(url, placeHolderProgressBar(view.context))
}
