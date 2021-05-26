package com.intermedia.challenge.ui.characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intermedia.challenge.data.models.Appearance
import com.intermedia.challenge.data.models.Character
import com.intermedia.challenge.data.models.NetResult
import com.intermedia.challenge.data.repositories.CharactersRepository
import kotlinx.coroutines.launch

class CharactersViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters
    private var _appearences = MutableLiveData<List<Appearance>>()
    val appearences : LiveData<List<Appearance>> get() = _appearences
    private var errors : Boolean = false
    private var newOffset = 0
    private var selectedCharacter = MutableLiveData<Character>()

    init {
        loadCharacters(0)
    }

     fun errorExists() : Boolean {
        return errors
    }

    fun setAppearences(appearences: List<Appearance>){
        _appearences.postValue(appearences)
    }

    fun setSelectedCharacter(character: Character) {
        selectedCharacter.value = character
    }

    fun getSelectedCharacter() : MutableLiveData<Character> {
        return selectedCharacter
    }

    private fun loadCharacters(offset: Int) {
        viewModelScope.launch {
            when (val response = charactersRepository.getCharacters(offset)) {
                is NetResult.Success -> {
                    errors = false
                    newOffset += 15
                    _characters.postValue(response.data.charactersList.characters)
                }
                is NetResult.Error -> {
                    errors = true
                }
            }
        }
    }

    fun loadMoreCharacters() {
        loadCharacters(newOffset)
    }
}