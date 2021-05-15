package com.intermedia.challenge.ui.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.intermedia.challenge.R
import com.intermedia.challenge.data.models.Appearances
import com.intermedia.challenge.data.models.Character
import com.intermedia.challenge.data.models.Thumbnail
import com.intermedia.challenge.databinding.FragmentCharactersBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharactersViewModel by sharedViewModel()
    private val adapter = CharactersAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCharactersList()
        setupPagination()
    }

    private fun setupPagination() {
        binding.listCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMoreCharacters()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun setupCharactersList() {
        adapter.onClickListener = { character ->

            val selectedChar = Character(
                comics = Appearances(character.comics.available, character.comics.collectionURI, character.comics.appearances, character.comics.returned),
                thumbnail = Thumbnail(character.thumbnail.path, character.thumbnail.extension)
            )
            viewModel.setSelectedCharacter(Character(comics = selectedChar.comics, name = character.name, description = character.description, thumbnail = selectedChar.thumbnail))
            findNavController().navigate(R.id.action_navigation_characters_to_characterDetailFragment)
        }

        binding.listCharacters.adapter = adapter
        if (viewModel.errorExists()) {
            Toast.makeText(requireContext(), "Hubo un error cargando los personajes", Toast.LENGTH_LONG).show()
            return
        }
        viewModel.characters.observe(viewLifecycleOwner, { characters ->
           adapter.addAll(characters,true)
        })
    }


}