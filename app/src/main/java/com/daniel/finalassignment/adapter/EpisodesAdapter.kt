package com.daniel.finalassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.finalassignment.databinding.EpisodeItemBinding
import com.daniel.finalassignment.domain.DomainEpisode

class EpisodesAdapter (
    private val episodes: MutableList<DomainEpisode> = mutableListOf(),
    private val onEpisodeClicked: (String?) -> Unit
): RecyclerView.Adapter<EpisodesViewHolder>() {

    fun upload(newEpisode: List<DomainEpisode>) {
        episodes.removeAll(episodes)
        episodes.addAll(newEpisode)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        return EpisodesViewHolder(
            EpisodeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        return holder.bind(episodes[position], onEpisodeClicked)
    }

    override fun getItemCount(): Int = episodes.size

}

class EpisodesViewHolder(
    private val binding: EpisodeItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(episode: DomainEpisode, onEpisodeClicked: (String?) -> Unit) {
        binding.titleId.text = episode.id.toString()
        binding.title.text = episode.title
        binding.season.text = episode.season
        binding.date.text = episode.airDate
        binding.characters.text = episode.characters.toString().replace("[","").replace("]","")

        itemView.setOnClickListener{
            onEpisodeClicked(episode.id.toString())
        }
    }
}




