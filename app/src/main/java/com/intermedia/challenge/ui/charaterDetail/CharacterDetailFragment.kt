package com.intermedia.challenge.ui.charaterDetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.intermedia.challenge.R
import com.intermedia.challenge.data.models.Appearance
import com.intermedia.challenge.data.models.Appearances
import com.intermedia.challenge.data.models.Character
import com.intermedia.challenge.databinding.FragmentCharacterDetailBinding
import com.intermedia.challenge.databinding.FragmentCharactersBinding
import com.intermedia.challenge.ui.characters.CharactersAdapter
import com.intermedia.challenge.ui.characters.CharactersViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {
    private lateinit var binding: FragmentCharacterDetailBinding
    private val viewModel: CharactersViewModel by sharedViewModel()
    private var comics_appearences : List<Appearance> = listOf()
    private var appearences_string : MutableList<String> = mutableListOf()
    private val rv_adapter = CharactersDetailAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailBinding.bind(view)
        getDataFromViewModel()
        goBackToCharacters()
    }

    private fun goBackToCharacters() {
        binding.buttonClose.setOnClickListener {
            findNavController().navigate(R.id.action_characterDetailFragment_to_navigation_characters)
        }
    }

    private fun getDataFromViewModel() {
        viewModel.getSelectedCharacter().observe(viewLifecycleOwner, Observer {
            comics_appearences = it.comics.appearances
            viewModel.setAppearences(comics_appearences)

            viewModel.appearences.observe(viewLifecycleOwner, { characters ->
                rv_adapter.addAll(characters, true)
            })

            binding.rvCharacterComicsAppearences.adapter = rv_adapter
            binding.tCharacterName.text = it.name
            if (it.description.isNullOrEmpty()){
                binding.tCharacterInfo.text = "There is no description available."
            }else{
                binding.tCharacterInfo.text = it.description
            }

            val image = "${it.thumbnail.extension}.${it.thumbnail.path}"
            Glide.with(requireContext()).load(image)
                .centerCrop().into(binding.imageThumbnail)

        })
    }
}