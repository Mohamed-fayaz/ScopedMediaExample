package com.example.mygallery

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.scopedmediaexample.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: Uri) {
    imgUrl?.let {
        Log.i("hrllo",it.toString())
        Glide.with(imgView.context)
            .load(imgUrl)
    .apply( RequestOptions().override(400, 1400))
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("imageUrlBig")
fun bindImageBig(imgView: ImageView, imgUrl: Uri) {
    imgUrl?.let {
        Log.i("hrllo",it.toString())
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply( RequestOptions().override(1000, 1600))
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}