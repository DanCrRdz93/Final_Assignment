package com.daniel.finalassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.finalassignment.R
import com.daniel.finalassignment.adapter.CharacterClickListener
import com.daniel.finalassignment.adapter.CharactersAdapter
import com.daniel.finalassignment.databinding.FragmentCharactersBinding
import com.daniel.finalassignment.domain.DomainCharacter
import com.daniel.finalassignment.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "CharactersFragment"

@AndroidEntryPoint
class CharactersFragment : BaseFragment() {

     private val binding by lazy {
        FragmentCharactersBinding.inflate(layoutInflater)
    }

    private val charactersAdapter by lazy {
        CharactersAdapter(
            object: CharacterClickListener {
                override fun onCharacterClicked(character: DomainCharacter) {
                    val characterData = CharactersData(character.id,character.name,character.nickname,character.occupation,character.status,
                    character.actor,character.img)
                    findNavController().navigate(
                        R.id.action_CharactersFragment_to_DetailsCharacterFragment, bundleOf(
                            Pair(DetailsCharacterFragment.CHARACTER_DATA, characterData)
                        )
                    )
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.charactersRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = charactersAdapter
        }
        breakingBadViewModel.getAllCharacters()


        breakingBadViewModel.characters.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.LOADING -> {

                }
                is ResponseState.SUCCESS<*> -> {
                    (state as ResponseState.SUCCESS<List<DomainCharacter>>).response
                    charactersAdapter.uploadList(state.response)

                }
                is ResponseState.ERROR -> {
                    showError(state.error) {
//                        breakingBadViewModel.getAllCharacters()
                    }
                }
            }
        }

        if (breakingBadViewModel.recyclerState == null) {
            Log.d(TAG, "onCreateView: LOADING STATE FROM NETWORK")
            binding.charactersRecycler.layoutManager?.scrollToPosition(1)
            breakingBadViewModel.getAllCharacters()
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: SAVING STATE TO VIEWMODEL")
        breakingBadViewModel.recyclerState = binding.charactersRecycler.layoutManager?.onSaveInstanceState()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        breakingBadViewModel.recyclerState?.let {
            Log.d(TAG, "onViewStateRestored: HERE YOU ARE RESTORING THE STATE")
            binding.charactersRecycler.layoutManager?.onRestoreInstanceState(it)
        }
        Log.d(TAG, "onViewStateRestored: RESETTING STATE RECYCLER TO NULL")
        breakingBadViewModel.recyclerState = null
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: REMOVE THE OBSERVERS")
        breakingBadViewModel.characters.removeObservers(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (breakingBadViewModel.recyclerState == null) {
            Log.d(TAG, "onDestroyView: RESETTING RECYCLER STATE IN VIEWMODEL")
            breakingBadViewModel.resetState()
        }
    }
}
