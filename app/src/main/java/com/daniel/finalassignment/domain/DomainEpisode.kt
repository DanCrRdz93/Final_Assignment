package com.daniel.finalassignment.domain

import com.daniel.finalassignment.model.episodes.EpisodesItem

data class DomainEpisode(

    val id: Int,
    val title: String,
    val season: String,
    val airDate: String,
    val characters: List<String>,

)

fun EpisodesItem.mapToDomainEpisode(): DomainEpisode {
    return DomainEpisode(
        id = this.episodeId ?: 0,
        title = this.title ?: "No tittle available",
        season = this.season ?: "No season available",
        airDate = this.airDate ?: "No date available",
        characters = this.characters
    )
}

fun List<EpisodesItem>.mapToDomainEpisodes(): List<DomainEpisode> {

    return this.map { episode ->
        DomainEpisode(
            id = episode.episodeId ?: 0,
            title = episode.title ?: "No tittle available",
            season = episode.season ?: "No season available",
            airDate = episode.airDate ?: "No date available",
            characters = episode.characters
        )
    }
}
