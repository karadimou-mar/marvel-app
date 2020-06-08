package com.example.marvelsquadhero.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelsquadhero.R
import com.example.marvelsquadhero.model.character.MarvelCharacter
import com.example.marvelsquadhero.utils.getThumbnail
import com.example.marvelsquadhero.utils.loadImage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_marvel_list_character.view.*

class MarvelListCharacterAdapter(private val itemClickListener: OnItemClickListener) :
    PagedListAdapter<MarvelCharacter, MarvelListCharacterAdapter.ViewHolder>(characterDiff) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val character = getItem(position)
        holder.textViewName?.text = character?.name
        holder.imageViewThumbnail!!.loadImage(character?.thumbnail?.getThumbnail())
        holder.bind(character,itemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_marvel_list_character,
                parent,
                false
            )
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView? = itemView.textView_name
        val imageViewThumbnail: CircleImageView? = itemView.imageView_thumbnail


        fun bind(hero: MarvelCharacter?, clickListener: OnItemClickListener){
            itemView.setOnClickListener {
                clickListener.onItemClicked(hero)
            }
        }
    }

    companion object {
        val characterDiff = object : DiffUtil.ItemCallback<MarvelCharacter>() {
            override fun areItemsTheSame(
                oldItem: MarvelCharacter,
                newItem: MarvelCharacter
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MarvelCharacter,
                newItem: MarvelCharacter
            ): Boolean {
                return oldItem.name == newItem.name && oldItem.thumbnail == newItem.thumbnail
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(hero: MarvelCharacter?)
    }
}