package com.example.marvelsquadhero.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelsquadhero.R
import com.example.marvelsquadhero.database.Converters
import com.example.marvelsquadhero.model.character.MarvelCharacter
import com.example.marvelsquadhero.utils.loadImage
import kotlinx.android.synthetic.main.item_squad_list_character.view.*

class SquadListCharacterAdapter (private val itemClickListener: MarvelListCharacterAdapter.OnItemClickListener)
    : RecyclerView.Adapter<SquadListCharacterAdapter.ViewHolder>(){

    private val conv = Converters()
    private val squadCharacters = ArrayList<MarvelCharacter>()

    fun setData(data:ArrayList<MarvelCharacter>){
        squadCharacters.clear()
        squadCharacters.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName?.text = squadCharacters[position].name
        holder.imageViewThumbnail.loadImage(conv.thumbnailToString(squadCharacters[position].thumbnail))
        holder.bind(squadCharacters[position],itemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_squad_list_character, parent, false ))
    }

    override fun getItemCount(): Int {
        return squadCharacters.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val textViewName = view.textView_squad_name!!
        val imageViewThumbnail = view.imageView_squad_thumbnail!!
        fun bind(hero: MarvelCharacter?, clickListener: MarvelListCharacterAdapter.OnItemClickListener){
            itemView.setOnClickListener {
                clickListener.onItemClicked(hero)
            }
        }
    }
}