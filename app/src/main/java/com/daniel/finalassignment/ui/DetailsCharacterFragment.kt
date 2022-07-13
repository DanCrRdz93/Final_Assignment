package com.daniel.finalassignment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daniel.finalassignment.R
import com.daniel.finalassignment.databinding.FragmentDetailsCharacterBinding
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DetailsCharacterFragment : Fragment() {

    private val binding by lazy {
        FragmentDetailsCharacterBinding.inflate(layoutInflater)
    }

    private lateinit var characterData: CharactersData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            characterData = it.getSerializable(CHARACTER_DATA) as CharactersData
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.CharacterId.text = characterData.id.toString()
        binding.name.text = characterData.name
        binding.nickname.text = characterData.nickname
        binding.occupation.text = characterData.occupation.toString().replace("[","").replace("]","")
        binding.status.text = characterData.status
        binding.actor.text = characterData.actor

        Picasso.get()
            .load(characterData.img)
            .placeholder(R.drawable.ic_baseline_image_search_24)
            .error(R.drawable.ic_baseline_broken_image_24)
            .fit()
            .into(binding.imageCharacter)


        return binding.root
    }

    companion object {
        const val CHARACTER_DATA = "CHARACTER_DATA"
    }
}


