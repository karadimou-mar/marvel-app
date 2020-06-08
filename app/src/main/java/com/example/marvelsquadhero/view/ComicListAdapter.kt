package com.example.marvelsquadhero.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelsquadhero.R
import com.example.marvelsquadhero.model.comic.MarvelComic
import com.example.marvelsquadhero.utils.getThumbnail
import com.example.marvelsquadhero.utils.loadImage
import kotlinx.android.synthetic.main.item_comic.view.*

class ComicListAdapter(private val comics: ArrayList<MarvelComic>, val context: Context) :
    RecyclerView.Adapter<ComicListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return comics.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comic, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageViewComic.loadImage(comics[position].thumbnail.getThumbnail())
        holder.textViewComic.text = comics[position].title
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewComic = view.imageView_comic!!
        val textViewComic = view.textView_comic!!

    }


}
