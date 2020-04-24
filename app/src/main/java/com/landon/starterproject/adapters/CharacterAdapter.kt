package com.landon.starterproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.landon.starterproject.R
import com.landon.starterproject.models.Character
import kotlinx.android.synthetic.main.character_list_item.view.profilePic
import kotlinx.android.synthetic.main.character_list_item.view.name

class CharacterAdapter(private val items: ArrayList<Character>, private val itemClickListener: OnCharacterClickListener) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>()  {

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.name
        val profilePic: ImageView = view.profilePic

        fun bindClickListener(character: Character, clickListener: OnCharacterClickListener) {
            itemView.setOnClickListener {
                clickListener.onItemClicked(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.CharacterViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)

        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = items[position]
        val imageUrl = character.thumbnail.path.replace("http", "https") + "." + character.thumbnail.extension

        holder.bindClickListener(character, itemClickListener)
        holder.name.text = character.name

        Glide.with(holder.itemView.context).load(imageUrl).into(holder.profilePic);
    }

    fun updateCharacters(newUsers: List<Character>) {
        items.clear()
        items.addAll(newUsers)
        notifyDataSetChanged()
    }
}

interface OnCharacterClickListener{
    fun onItemClicked(character: Character)
}