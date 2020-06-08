package com.example.marvelsquadhero.utils


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.marvelsquadhero.model.Thumbnail
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.loadImage(url: String?){
    Glide.with(context)
        .load(url)
        //.apply(RequestOptions().override(250,250))
        .into(this)

}

fun Thumbnail.getThumbnail() :String {
    return "${this.path}.${this.extension}"
}

fun ImageView.loadImage(url: String?){
    Glide.with(context)
        .load(url)
        .into(this)
}
