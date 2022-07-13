package com.daniel.finalassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.finalassignment.adapter.EpisodesAdapter
import com.daniel.finalassignment.databinding.FragmentEpisodesBinding
import com.daniel.finalassignment.domain.DomainEpisode
import com.daniel.finalassignment.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "EpisodesFragment"

@AndroidEntryPoint
class EpisodesFragment : BaseFragment() {

    private val binding by lazy {
        FragmentEpisodesBinding.inflate(layoutInflater)
    }

    private val episodesAdapter by lazy {
        EpisodesAdapter {
            // TODO handle click event
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.episodesRecycler.apply {
            Log.d(TAG, "onCreateView: SETTING THE LAYOUT MANAGER TO RECYCLER")
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = episodesAdapter
        }
        breakingBadViewModel.getAllEpisodes()


        Log.d(TAG, "onCreateView: OBSERVING LIVEDATA")
        breakingBadViewModel.episodes.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.LOADING -> {
                    // TODO show loading spinner
                }
                is ResponseState.SUCCESS<*> -> {
                    Log.d(TAG, "onCreateView: SETTING EPISODES TO RECYCLER")
                    (state as ResponseState.SUCCESS<List<DomainEpisode>>).response
                    episodesAdapter.upload(state.response)
                }
                is ResponseState.ERROR -> {
                    showError(state.error) {
//                        breakingBadViewModel.getAllEpisodes()
                    }
                }
            }
        }

        if (breakingBadViewModel.recyclerState == null) {
            Log.d(TAG, "onCreateView: LOADING STATE FROM NETWORK")
            binding.episodesRecycler.layoutManager?.scrollToPosition(1)
            breakingBadViewModel.getAllEpisodes()
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: SAVING STATE TO VIEWMODEL")
        breakingBadViewModel.recyclerState = binding.episodesRecycler.layoutManager?.onSaveInstanceState()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        breakingBadViewModel.recyclerState?.let {
            Log.d(TAG, "onViewStateRestored: HERE YOU ARE RESTORING THE STATE")
            binding.episodesRecycler.layoutManager?.onRestoreInstanceState(it)
        }
        Log.d(TAG, "onViewStateRestored: RESETTING STATE RECYCLER TO NULL")
        breakingBadViewModel.recyclerState = null
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: REMOVE THE OBSERVERS")
        breakingBadViewModel.episodes.removeObservers(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (breakingBadViewModel.recyclerState == null) {
            Log.d(TAG, "onDestroyView: RESETTING RECYCLER STATE IN VIEWMODEL")
            breakingBadViewModel.resetState()
        }
    }
}

