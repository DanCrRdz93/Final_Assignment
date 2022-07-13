package com.daniel.finalassignment.viewmodel

import android.os.Parcelable
import androidx.lifecycle.*
import com.daniel.finalassignment.network.BreakingBadRepository
import com.daniel.finalassignment.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingBadViewModel @Inject constructor(
    private val breakingBadRepository: BreakingBadRepository,
    private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {



    var recyclerState: Parcelable? = null

    /** this is mutable that will change inside the view model
     */
    private val _episodes: MutableLiveData<ResponseState> = MutableLiveData(ResponseState.LOADING)

    // This guy livedata is data class wrapper that follows the observable pattern and is lifecycle aware
    // emits data when lifecycle is active and does not emit data when inactive

    // THis no mutable livedata is a readable variable that can nt change from the UI
    val episodes: LiveData<ResponseState> get() = _episodes

    private val _characters: MutableLiveData<ResponseState> = MutableLiveData(ResponseState.LOADING)
    val characters: LiveData<ResponseState> get() = _characters

    fun getAllEpisodes(){
        getData(breakingBadRepository.getAllEpisodes(), _episodes)
    }

    fun getAllCharacters(){
        getData(breakingBadRepository.getAllCharacters(), _characters)
    }

    private fun getData(flow: Flow<ResponseState>, data: MutableLiveData<ResponseState>){
        viewModelScope.launch {
            flow.flowOn(ioDispatcher).collect{
                data.postValue(it)
            }
        }
    }

    fun resetState() {
        _episodes.value = ResponseState.LOADING
        _characters.value = ResponseState.LOADING
    }
}