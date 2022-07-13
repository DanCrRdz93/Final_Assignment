package com.daniel.finalassignment.network

import com.daniel.finalassignment.model.characters.CharactersItem
import com.daniel.finalassignment.model.episodes.EpisodesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BreakingBadApi {

    @GET(LIST_EPISODES)
    suspend fun getAllEpisodes(
    ): Response<List<EpisodesItem>>

    @GET(LIST_CHARACTERS)
    suspend fun getAllCharacters(
    ): Response<List<CharactersItem>>

    companion object{
        const val BASE_URL = "https://www.breakingbadapi.com/api/"
        private const val LIST_EPISODES = "episodes"
        private const val LIST_CHARACTERS = "characters"
    }

}