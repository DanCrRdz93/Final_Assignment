package com.daniel.finalassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.finalassignment.R
import com.daniel.finalassignment.databinding.CharacterItemBinding
import com.daniel.finalassignment.domain.DomainCharacter
import com.squareup.picasso.Picasso

class CharactersAdapter (
    private val onCharacterClicked: CharacterClickListener,
    private val characters: MutableList<DomainCharacter> = mutableListOf()
): RecyclerView.Adapter<CharactersViewHolder>() {

    fun uploadList(newCharacter: List<DomainCharacter>) {
        characters.removeAll(characters)
        characters.addAll(newCharacter)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        return holder.bind(characters[position], onCharacterClicked)
    }

    override fun getItemCount(): Int = characters.size
}

class CharactersViewHolder(
    private val binding: CharacterItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(character: DomainCharacter, onCharacterClickListener: CharacterClickListener) {
        binding.CharacterId.text = character.id.toString()
        binding.name.text = character.name
        binding.nickname.text = character.nickname

        Picasso.get()
            .load(character.img)
            .placeholder(R.drawable.ic_baseline_image_search_24)
            .error(R.drawable.ic_baseline_broken_image_24)
            .fit()
            .into(binding.imageCharacter)

        itemView.setOnClickListener{
            onCharacterClickListener.onCharacterClicked(character)
        }
    }
}

interface CharacterClickListener{
    fun onCharacterClicked(character: DomainCharacter)
}