package com.daniel.finalassignment.network

import com.daniel.finalassignment.domain.mapToDomainCharacters
import com.daniel.finalassignment.domain.mapToDomainEpisodes
import com.daniel.finalassignment.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

interface BreakingBadRepository {
    fun getAllEpisodes(): Flow<ResponseState>
    fun getAllCharacters(): Flow<ResponseState>
}

class BreakingBadRepositoryImpl @Inject constructor(
    private val breakingBadApi: BreakingBadApi
): BreakingBadRepository{

    override fun getAllEpisodes(): Flow<ResponseState> {
        return flow {
            try {
                val response = breakingBadApi.getAllEpisodes()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResponseState.SUCCESS(it.mapToDomainEpisodes()))
                    } ?: throw Exception("RESPONSE IS NULL")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (error: Exception) {
                emit(ResponseState.ERROR(error))
            }
        }
    }

    override fun getAllCharacters(): Flow<ResponseState> {
        return flow {
            try {
                val response = breakingBadApi.getAllCharacters()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResponseState.SUCCESS(it.mapToDomainCharacters()))
                    } ?: throw Exception("RESPONSE IS NULL")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (error: Exception) {
                emit(ResponseState.ERROR(error))
            }
        }
    }

}